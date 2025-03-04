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

@WebServlet(name = "AddnewUser", urlPatterns = {"/addUser"})
public class AddnewUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        userDao dao = new userDao();

        String name = request.getParameter("username");
        String password = request.getParameter("password");
        String isSellstr = request.getParameter("isSell");
        String isAdminstr = request.getParameter("isAdmin");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String gender = request.getParameter("sex");
        String birthdate = request.getParameter("date");
        String avatar=request.getParameter("avatar");

        // Validate inputs
        if (name == null || password == null || isSellstr == null || isAdminstr == null) {
            response.sendRedirect("manageru?error=Please fill all fields.");
            return;
        }

        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasUppercase = password.matches(".*[A-Z].*");
        boolean hasLowercase = password.matches(".*[a-z].*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");

        int isSell = Integer.parseInt(isSellstr);
        int isAdmin = Integer.parseInt(isAdminstr);

        // Check password strength
        if (!(hasNumber && hasUppercase && hasLowercase && hasSpecialChar && password.length() > 8)) {
            response.sendRedirect("manageru?error=Password not strong enough! Please Sign Up Again.");
            return;
        }

        User existingUser = dao.checkUser(name);
        if (existingUser != null) {
            response.sendRedirect("manageru?error=( User already exists! ) Please Sign Up Again.");
            return;
        }

        // Add user if no existing user is found
        dao.AddUser(name, password, isSell, isAdmin, email, phone, address, gender, birthdate,avatar);
        response.sendRedirect("manageru");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
