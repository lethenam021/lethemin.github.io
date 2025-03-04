package controller;

import dal.DAO;
import mol.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "EditControl", urlPatterns = {"/edit"})
public class EditProduct extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        try {
            // Retrieve form parameters
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            float price = Float.parseFloat(request.getParameter("price"));
            String image = request.getParameter("image");
            int storage = Integer.parseInt(request.getParameter("storage"));
            String color = request.getParameter("color");
            String quantity = request.getParameter("stock");
            String description = request.getParameter("description");

            // Retrieve the seller's ID from the session
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("acc");
            int sellerId = user.getId();

            // Update product through DAO
            DAO dao = new DAO();
            dao.updateProduct(id, name, price, image, storage, color, quantity, description, sellerId);

                response.sendRedirect("manager"); // Redirect on success

        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid input data: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for editing product details.";
    }
}
