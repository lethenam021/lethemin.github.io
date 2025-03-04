package controller;

import dal.DAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import mol.Sold;
import mol.User;

@WebServlet(name = "ManagerFinance", urlPatterns = {"/finance"})
public class ManagerFinance extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("acc");
        
        if (user != null) {
            DAO dao = new DAO();
            int userId = user.getId();

            // Retrieve list of sold products for the user
            List<Sold> soldProducts = dao.getSoldProductsBySellIds(userId);
            request.setAttribute("list", soldProducts);

            // Calculate total profit using a for loop
            double totalProfit = 0.0;
            for (Sold prod : soldProducts) {
                totalProfit += prod.getPrice() * prod.getAmount();
            }
            DecimalFormat df = new DecimalFormat("#.00");
            request.setAttribute("totalProfit", df.format(totalProfit));
        }

        // Forward to the finance management JSP page
        request.getRequestDispatcher("ManagerFinancial.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for managing store finances.";
    }
}
