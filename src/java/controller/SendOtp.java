/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.userDao;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mol.User;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "SendOtp", urlPatterns = {"/sendOtp"})
public class SendOtp extends HttpServlet {

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
            out.println("<title>Servlet SendOtp</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SendOtp at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        String name = request.getParameter("user");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        userDao dao = new userDao();
        User u = dao.checkUser2(name, email, phone);

        if (u == null) {
            request.setAttribute("mess", "Account not found (Please try again!)");
            request.getRequestDispatcher("Forgotpass.jsp").forward(request, response);
        } else {
            String otp = generateRandomCode();
            request.getSession().setAttribute("otp", otp);
            request.setAttribute("otp", otp);
            request.setAttribute("mess", "Your confirmation code is: " + otp);
            request.getRequestDispatcher("Forgotpass.jsp").forward(request, response);
        }
    }

// Hàm tạo mã xác nhận ngẫu nhiên 6 chữ số
    private String generateRandomCode() {
        int code = (int) (Math.random() * 900000) + 100000; // Tạo số ngẫu nhiên trong khoảng 100000 đến 999999
        return String.valueOf(code);
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
