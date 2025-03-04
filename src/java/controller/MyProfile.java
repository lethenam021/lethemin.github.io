package controller;

import dal.DAO;
import dal.OrderDao;
import dal.userDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import mol.Order;
import mol.User;

@WebServlet(name = "MyProfile", urlPatterns = {"/profile"})
public class MyProfile extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("acc");

        if (user == null) {
            request.setAttribute("errorMessage", "Bạn cần đăng nhập để thực hiện chức năng này.");
            request.getRequestDispatcher("MyProfile.jsp").forward(request, response);
            return;
        }

        int userId = user.getId();
        userDao dao = new userDao();
        User u = dao.getUserByID(userId);

        if (u != null) {
            request.setAttribute("info", u);
        } else {
            request.setAttribute("errorMessage", "Không tìm thấy người dùng!");
        }

        request.getRequestDispatcher("MyProfile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // No POST action specified for this servlet
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for handling profile display.";
    }
}
