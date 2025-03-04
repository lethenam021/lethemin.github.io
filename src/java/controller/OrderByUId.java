package controller;

import dal.DAO;
import dal.OrderDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mol.Order;
import mol.Product;
import mol.User;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderProducts", urlPatterns = {"/buy"})
public class OrderByUId extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OrderDao orderDao = new OrderDao();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("acc");
        if (user != null) {
            int userId = user.getId();
            session.setAttribute("Order", orderDao.getOrderByUid(userId));
            request.getRequestDispatcher("TotalOrder.jsp").forward(request, response);

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
        request.getRequestDispatcher("TotalOrder.jsp").forward(request, response);

    }

}
