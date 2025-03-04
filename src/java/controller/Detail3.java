package controller;

import dal.DAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import mol.Sold;

@WebServlet(name = "Detail3", urlPatterns = {"/detail3"})
public class Detail3 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy tham số 'time' từ yêu cầu
            String dtime = request.getParameter("time");

            if (dtime != null && !dtime.isEmpty()) {
                // Chuyển đổi định dạng thời gian
                SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSSSSSSSS");
                SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = originalFormat.parse(dtime);
                String formattedDate = targetFormat.format(date);

                // Sử dụng thời gian đã được định dạng
                DAO dao = new DAO();
                List<Sold> sold = dao.getSoldProductsByOrder(formattedDate);

                request.setAttribute("sold", sold);
                request.getRequestDispatcher("orderDetail2.jsp").forward(request, response);
            } else {
                response.getWriter().println("Invalid 'time' parameter.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("An error occurred while processing the request.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);  // Gọi phương thức POST để xử lý cả GET và POST
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Bạn có thể để trống nếu không sử dụng
    }
}
