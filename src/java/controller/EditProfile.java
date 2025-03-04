
package controller;
import dal.userDao;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

// Đánh dấu servlet sử dụng MultipartConfig
@WebServlet(name = "EditProfile", urlPatterns = {"/editx"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class EditProfile extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy các thông tin text từ form
            String idParam = request.getParameter("id");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            String sellParam = request.getParameter("sell");
            String adminParam = request.getParameter("admin");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String gender = request.getParameter("sex");
            String birthdate = request.getParameter("date");

            // Lưu file avatar
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String avatarFileName = null;
            for (Part part : request.getParts()) {
                if (part.getName().equals("avatar") && part.getSize() > 0) {
                    avatarFileName = new File(part.getSubmittedFileName()).getName();
                    part.write(uploadPath + File.separator + avatarFileName);
                }
            }

            // Validate và xử lý dữ liệu
            int id = Integer.parseInt(idParam.trim());
            int sell = Integer.parseInt(sellParam.trim());
            int admin = Integer.parseInt(adminParam.trim());

            // Ghi thông tin vào database
            userDao dao = new userDao();
            dao.updateProfile(id, name.trim(), password.trim(), sell, admin, email, phone, address, gender, birthdate, UPLOAD_DIR + "/" + avatarFileName);

            response.sendRedirect("profile"); // Redirect khi thành công
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for editing user information.";
    }
}
