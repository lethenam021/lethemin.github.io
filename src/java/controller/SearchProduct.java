package controller;

import dal.DAO;
import dal.userDao;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mol.Product;
import mol.User;

@WebServlet(name = "SearchProduct", urlPatterns = {"/search"})
public class SearchProduct extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String txtSearch = request.getParameter("txt");
        DAO dao = new DAO();
        userDao uDAO = new userDao();
        
        User u = uDAO.getUserByName(txtSearch);
        List<Product> products;
        
        if (u != null) {
            int userId = u.getId();
            products = dao.getProductbySellId(userId);
        } else {
            products = dao.searchByName(txtSearch);
        }
        
        request.setAttribute("lists", products);
        request.getRequestDispatcher("AllProducts.jsp").forward(request, response);
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
        return "Handles product search based on seller or product name";
    }
}
