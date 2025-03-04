package controller;

import dal.DAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import mol.AverageStar;
import mol.Countnumbersold;
import mol.Product;
import mol.FeedBack;

@WebServlet(name = "DetailProduct", urlPatterns = {"/detail"})
public class DetailProduct extends HttpServlet {

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);

                DAO dao = new DAO();
                Product product = dao.getProductsByID(id);
                Countnumbersold p = dao.getCountNumberOfsold(id);
                AverageStar px = dao.getAverageFeedback(id);
                if (product != null) {
                    String pro = product.getName();
                    String str = pro.substring(0, 9);
                    List<Product> relatedProducts = dao.getRelateProduct(str);
                    List<FeedBack> f = dao.getFeedbackByProductId(id);

                    //relatedProducts.removeIf(p -> p.getId() == id);
                    request.setAttribute("p", p);
                    request.setAttribute("px", px);
                    request.setAttribute("detail", product);
                    request.setAttribute("detail2", relatedProducts);
                    request.setAttribute("f", f);
                }
                request.getRequestDispatcher("Detail.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Product ID");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is missing");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for displaying product details and related products.";
    }
}
