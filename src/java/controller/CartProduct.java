package controller;

import dal.DAO;
import dal.OrderDao;
import dal.userDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import mol.Order;
import mol.Product;
import mol.User;

@WebServlet(name = "CartProduct", urlPatterns = {"/cart"})
public class CartProduct extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Product> cart = (List<Product>) session.getAttribute("cart");
        User user = (User) session.getAttribute("acc");

        if (user == null) {
            request.setAttribute("errorMessage", "Bạn cần đăng nhập để thực hiện chức năng này.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return;
        }

        int userId = user.getId();
        userDao daos = new userDao();
        User u = daos.getUserByID(userId);

        if (u != null) {
            session.setAttribute("info", u);
        } else {
            request.setAttribute("errorMessage", "Không tìm thấy người dùng!");
        }

        if (cart == null) {
            cart = new ArrayList<>();
        }

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            request.setAttribute("error", "Invalid product ID provided.");
            request.getRequestDispatcher("Cart.jsp").forward(request, response);
            return;
        }

        int id = Integer.parseInt(idStr);
        String quantityStr = request.getParameter("quantity");
        int quantity = quantityStr != null && !quantityStr.isEmpty() ? Integer.parseInt(quantityStr) : 1;

        DAO dao = new DAO();
        Product product = dao.getProductsByID(id);

        if (product != null) {
            boolean found = false;
            for (Product p : cart) {
                if (p.getId() == id) {
                    p.setAmount(p.getAmount() + quantity);
                    found = true;
                    break;
                }
            }
            if (!found) {
                product.setAmount(quantity);
                cart.add(product);
            }
        }

        session.setAttribute("cart", cart);
        calculateCartTotals(request, cart);
        request.getRequestDispatcher("Cart.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Product> cart = (List<Product>) session.getAttribute("cart");
        String action = request.getParameter("action");
        String idStr = request.getParameter("id");

        if (cart == null || idStr == null || idStr.isEmpty()) {
            request.setAttribute("error", "No products in cart.");
            request.getRequestDispatcher("Cart.jsp").forward(request, response);
            return;
        }

        int id = Integer.parseInt(idStr);
        if ("remove".equals(action)) {
            cart.removeIf(product -> product.getId() == id);
        } else if ("add".equals(action)) {
            for (Product product : cart) {
                if (product.getId() == id) {
                    product.setAmount(product.getAmount() + 1);
                    break;
                }
            }
        } else if ("sub".equals(action)) {
            for (Product product : cart) {
                if (product.getId() == id) {
                    if (product.getAmount() > 1) {
                        product.setAmount(product.getAmount() - 1);
                    }
                    break;
                }
            }
        }

        session.setAttribute("cart", cart);
        calculateCartTotals(request, cart);
        request.getRequestDispatcher("Cart.jsp").forward(request, response);

    }

    private void calculateCartTotals(HttpServletRequest request, List<Product> cart) {
        HttpSession session= request.getSession();
        double total = 0;
        for (Product product : cart) {
            total += product.getPrice() * product.getAmount();
        }
        double vat = total * 0.1;
        double ship = total * 0.05;
        double sum = total + vat + ship;

        DecimalFormat df = new DecimalFormat("#.0");
        String formatVat = df.format(vat);
        String formatShip = df.format(ship);
        String formatSum = df.format(sum);

        session.setAttribute("total", total);
        session.setAttribute("ship", formatShip);
        session.setAttribute("vat", formatVat);
        session.setAttribute("sum", formatSum);

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        DAO dao = new DAO();
        OrderDao odao = new OrderDao();
        List<Order> orders = odao.getAllOrders();

        String idStr = request.getParameter("id");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            Product product = dao.getProductsByID(id);

            if (product != null) {
                List<Product> productList = dao.getProductbySellId(product.getSellId());
                request.setAttribute("productList", productList);

                // Tính tổng lợi nhuận của người bán
                double totalProfit = 0.0;
                for (Product prod : productList) {
                    totalProfit += prod.getPrice() * prod.getAmount();
                }

                // Định dạng lợi nhuận thành dạng có dấu thập phân
                DecimalFormat df = new DecimalFormat("#.00");
                String formattedProfit = df.format(totalProfit);
                request.setAttribute("totalProfit", formattedProfit);
            }
        }

        request.getRequestDispatcher("ManagerFinancial.jsp").forward(request, response);
    }
}
