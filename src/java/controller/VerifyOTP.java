package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "VerifyOTP", urlPatterns = {"/verify_otp"})
public class VerifyOTP extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String otpSent = (String) request.getSession().getAttribute("otp");  // Use session to store OTP
        String otpInput = request.getParameter("otp");

        if (otpSent == null || otpSent.isEmpty()) {
            request.setAttribute("mess", "Invalid request!");
            request.getRequestDispatcher("Forgotpass.jsp").forward(request, response);
            return;
        }

        if (otpInput == null || otpInput.isEmpty()) {
            request.setAttribute("mess", "Please enter OTP to verify!");
            request.getRequestDispatcher("Forgotpass.jsp").forward(request, response);
        } else if (!otpSent.equals(otpInput)) {
            request.setAttribute("mess", "Incorrect OTP entered!");
            request.getRequestDispatcher("Forgotpass.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("NewPasswordForgot.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);  
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Verify OTP Servlet";
    }
}
