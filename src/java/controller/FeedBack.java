package controller;

import dal.DAO;
import dal.OrderDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import mol.Order;

@WebServlet(name = "FeedBack", urlPatterns = {"/feedback"})
public class FeedBack extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pidstr = request.getParameter("pid");
        String name = request.getParameter("userName");
        String content = request.getParameter("feedbackContent");
        String ratestr = request.getParameter("rating");

        try {
            if (pidstr != null && ratestr != null && content != null && name != null) {
                int pid = Integer.parseInt(pidstr);
                int rate = Integer.parseInt(ratestr);
                DAO dao = new DAO();
                dao.addFeedBack(pid, name, content, rate);
                request.getRequestDispatcher("Thank.jsp").forward(request, response);
               // response.sendRedirect("detail2?ids=" + pid);

            } else {
                request.setAttribute("errorMessage", "Không thể tạo đơn hàng để lưu phản hồi.");
                request.getRequestDispatcher("Order.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi nếu có
            request.setAttribute("errorMessage", "Có lỗi xảy ra, vui lòng thử lại.");
            request.getRequestDispatcher("Order.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "FeedBack Servlet for handling user feedback submissions";
    }
}
