/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import mol.Product;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "Product", urlPatterns = {"/product"})
public class AllProducts extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Product</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Product at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("model");
        String storagestr = request.getParameter("storage");
        String pricestr1 = request.getParameter("minPrice");
        String pricestr2 = request.getParameter("maxPrice");
        String color = request.getParameter("color");
        if ( storagestr != null && !storagestr.isEmpty() &&!pricestr1.isEmpty() && !pricestr2.isEmpty() && pricestr1 != null && pricestr2 != null) {
            int storage = Integer.parseInt(storagestr);
            double minprice = Double.parseDouble(pricestr1);
            double maxprice = Double.parseDouble(pricestr2);
            DAO dao = new DAO();
            List<Product> Filter = dao.filterProducts(name, minprice, maxprice, color, storage);
            request.setAttribute("Filter", Filter);
            request.getRequestDispatcher("AllProducts.jsp").forward(request, response);
        }
        doPost(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String sortType = request.getParameter("sort");
    String indexPage = request.getParameter("index");

    if (indexPage == null) {
        indexPage = "1";
    }
    int index = Integer.parseInt(indexPage);

    DAO dao = new DAO();
    int totalProducts = dao.getAllProducts().size(); // Tổng số sản phẩm
    int endPage = (int) Math.ceil(totalProducts / 8.0); // Tổng số trang

    List<Product> productList;
    if (sortType == null || sortType.isEmpty()) {
        // Nếu không có sắp xếp, chỉ phân trang
        productList = dao.PagingProducts(index);
    } else {
        // Sắp xếp và phân trang
        switch (sortType) {
            case "price_asc":
                productList = dao.SortProductbyPriceASC(index);
                break;
            case "price_desc":
                productList = dao.SortProductbyPriceDESC(index);
                break;
            case "BestSeller_desc":
                productList = dao.SortProductbyBestSellerDESC(index);
                break;
            case "Feedback_desc":
                productList = dao.SortProductbyAverageFeedbackDESC(index);
                break;
            default:
                productList = dao.PagingProducts(index); // Nếu không rõ sắp xếp, mặc định phân trang
                break;
        }
    }

    // Đưa dữ liệu vào request để hiển thị
    request.setAttribute("endP", endPage);
    request.setAttribute("currentPage", index);
    request.setAttribute("lists", productList);
    request.setAttribute("sortType", sortType);

    // Forward đến trang JSP
    request.getRequestDispatcher("AllProducts.jsp").forward(request, response);
}

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
