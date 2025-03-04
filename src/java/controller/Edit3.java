package controller;

import dal.userDao;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mol.User;

@WebServlet(name = "Edit3", urlPatterns = {"/edit3"})
public class Edit3 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        try {
            // Lấy thông tin từ request
            String idParam = request.getParameter("id");
            String password = request.getParameter("password");
            String sellParam = request.getParameter("sell");
            String adminParam = request.getParameter("admin");
            String birthdate = request.getParameter("date");
            String gender = request.getParameter("sex");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String avatar = request.getParameter("avatar");

            // Kiểm tra các thông tin quan trọng
            if (idParam == null || password == null || name == null || sellParam == null || adminParam == null || birthdate == null || gender == null || email == null || phone == null || address == null) {
                throw new IllegalArgumentException("Một số tham số bị thiếu.");
            }

            // Parse các tham số
            int id = Integer.parseInt(idParam.trim());
            int sell = Integer.parseInt(sellParam.trim());
            int admin = Integer.parseInt(adminParam.trim());

            // Cập nhật thông tin người dùng
            userDao dao = new userDao();
             User u=dao.updateUser(id, name.trim(), password.trim(), sell, admin, email, phone, address, gender, birthdate, avatar);
            session.setAttribute("info", u);
            request.getRequestDispatcher("Cart.jsp").forward(request, response);

            // Chuyển hướng về giỏ hàng sau khi cập nhật thành công
            //response.sendRedirect("cart");

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Định dạng số không hợp lệ. Vui lòng nhập số hợp lệ.");
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Đã xảy ra lỗi khi cập nhật thông tin người dùng.");
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet cập nhật thông tin người dùng.";
    }
}
