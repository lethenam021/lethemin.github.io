package controller;

import dal.DAO;
import dal.OrderDao;
import dal.userDao;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mol.Product;
import mol.User;

@WebServlet(name = "Order", urlPatterns = {"/orders"})
public class Order extends HttpServlet {

    private static final double TAX_VAT_RATE = 0.1;
    private static final double SHIP_RATE = 0.05;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("acc");

        if (user == null) {
            request.setAttribute("errorMessage", "Bạn cần đăng nhập để thực hiện chức năng này.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        int userId = user.getId();
        userDao userDao = new userDao();
        DAO productDao = new DAO();
        OrderDao orderDao = new OrderDao();

        User u = userDao.getUserByID(1089);
        if (u == null) {
            request.setAttribute("errorMessage", "Không tìm thấy thông tin người dùng.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        List<Product> cart = (List<Product>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            request.setAttribute("errorMessage", "Giỏ hàng đang trống.");
            request.getRequestDispatcher("Cart.jsp").forward(request, response);
            return;
        }

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String payment = request.getParameter("payment");

        double total = calculateCartTotal(cart);

        mol.Order newOrder = orderDao.createOrder(userId, name, email, phone, address, total, payment);
        if (newOrder == null) {
            request.setAttribute("errorMessage", "Không thể tạo đơn hàng. Vui lòng thử lại sau.");
            request.getRequestDispatcher("Cart.jsp").forward(request, response);
            return;
        }
        boolean x = true;
        for (Product product : cart) {
            productDao.addSoldProduct(product.getId(), product.getName(), product.getPrice(), product.getAmount(), product.getSellId());
            x = true;
            if (!true) {
                session.setAttribute("errorMessage", "Không thể thêm sản phẩm vào danh sách đã bán: " + product.getName());
                response.sendRedirect("Cart.jsp");
                return;
            }
        }
        session.removeAttribute("cart");
        session.setAttribute("successMessage", "Đơn hàng đã được tạo thành công với ID: " + newOrder.getId());
        session.setAttribute("orderId", newOrder.getId());
        // session.setAttribute("orderList", 
        request.getRequestDispatcher("Thank.jsp").forward(request, response);
    }

    private double calculateCartTotal(List<Product> cart) {
        double total = 0;
        double grandtotal = 0;
        for (Product product : cart) {
            total += product.getPrice() * product.getAmount();
        }

        return total + (total * TAX_VAT_RATE) + (total * SHIP_RATE);
    }

    @Override
    public String getServletInfo() {
        return "Servlet xử lý đơn hàng";
    }
}
