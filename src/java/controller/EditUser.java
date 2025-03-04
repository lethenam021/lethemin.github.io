package controller;

import dal.userDao;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//

/**
 * Servlet for editing user information.
 */
@WebServlet(name = "EditUser", urlPatterns = {"/edit2"})
public class EditUser extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Edit User</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Edit User</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idParam = request.getParameter("id");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            String sellParam = request.getParameter("sell");
            String adminParam = request.getParameter("admin");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String gender = request.getParameter("sex");
            String birthdate = request.getParameter("date");
            String avatar = request.getParameter("avatar");

            // Validate input parameters
            if (idParam == null || password == null || name == null || sellParam == null || adminParam == null) {
                throw new IllegalArgumentException("One or more parameters are missing.");
            }

            // Trim and parse input parameters
            int id = Integer.parseInt(idParam.trim());
            int sell = Integer.parseInt(sellParam.trim());
            int admin = Integer.parseInt(adminParam.trim());

            // Update user information in the database
            userDao dao = new userDao();
            dao.updateUser(id, name.trim(), password.trim(), sell, admin, email, phone, address, gender, birthdate, avatar);
           //response.sendRedirect("profile"); // Redirect on success

            response.sendRedirect("manageru"); // Redirect on success
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid number format. Please enter valid integers.");
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An error occurred while updating the user.");
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for editing user information.";
    }
}
