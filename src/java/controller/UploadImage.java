/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author ADMIN
 */
@WebServlet("/upload-avatar")
@MultipartConfig
public class UploadImage extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy file từ request
        Part filePart = request.getPart("fileAvatar");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        
        // Lưu ảnh vào thư mục trên server
        String uploadDir = getServletContext().getRealPath("/uploads");
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdir(); // Tạo thư mục nếu chưa có
        }
        
        // Lưu file
        File file = new File(uploadDir, fileName);
        try (InputStream fileContent = filePart.getInputStream()) {
            Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        
        // Cập nhật đường dẫn ảnh vào database (giả sử bạn có phương thức updateAvatarUrl)
        String avatarUrl = "/uploads/" + fileName;
        updateAvatarUrlInDatabase(request.getSession().getAttribute("user_id"), avatarUrl);
        
        // Trả về trang hoặc redirect về trang profile
        response.sendRedirect("profile.jsp"); // Redirect lại trang profile
    }

    private void updateAvatarUrlInDatabase(Object userId, String avatarUrl) {
        // Cập nhật URL ảnh vào database (Cần thực hiện qua DAO hoặc ORM)
        // Ví dụ:
        // UserDao userDao = new UserDao();
        // userDao.updateAvatar(userId, avatarUrl);
    }
}
