package controller;

import dal.userDao;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import mol.User;

@WebServlet(name = "SignUp", urlPatterns = {"/signup"})

public class SignUp extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("SignUp.jsp").forward(request, response); // Use a SignUp page for form
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        String repassword = request.getParameter("repass");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String birthdate = request.getParameter("date");
        String gender = request.getParameter("sex");
        String avatar=request.getParameter("avatar");

// Check if the name contains uppercase and lowercase letters
//        boolean hasUppercase = username != null && username.matches(".*[A-Z].*");
//        boolean hasLowercase = username != null && username.matches(".*[a-z].*");
//
//        if (username == null || !hasUppercase || !hasLowercase) {
//            request.setAttribute("mess", "Invalid name format. Name must contain both uppercase and lowercase letters.");
//            request.getRequestDispatcher("Login.jsp").forward(request, response);
//        }

        // Validate password matching
        if (!password.equals(repassword)) {
            request.setAttribute("mess", "( Passwords do not match! ) Please try again.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return;
        }

        // Validate password strength
        if (!isPasswordStrong(password)) {
            request.setAttribute("mess", "( Weak password! ) At least 8 characters, including uppercase, lowercase, number, and special character.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return;
        }

        userDao dao = new userDao();
        User existingUser = dao.checkUser(username);

        if (existingUser != null) {
            request.setAttribute("mess", "( User already exists! ) Please try again.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return;
        }

        // Register new user
        User newUser = dao.signUp(username, password, email, phone, address, gender, birthdate,avatar);
        if (newUser != null) {
            HttpSession session = request.getSession();
            session.setAttribute("acc", newUser);
            response.sendRedirect("home");
        } else {
            request.setAttribute("mess", "Sign-up failed. Please try again.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
    }

    private boolean isPasswordStrong(String password) {
        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasUppercase = password.matches(".*[A-Z].*");
        boolean hasLowercase = password.matches(".*[a-z].*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
        return hasNumber && hasUppercase && hasLowercase && hasSpecialChar && password.length() >= 8;
    }

    @Override
    public String getServletInfo() {
        return "SignUp servlet handles user registration with validation.";
    }
}
//<c:if test="${sessionScope.acc.isAdmin == 1}">
//                    <li class="nav-item">
//                        <a class="nav-link text-light" href="manageru">Manage Account</a>
//                    </li>
//                </c:if>
//                <c:if test="${sessionScope.acc.isSell == 1}">
//                    <li class="nav-item">
//                        <a class="nav-link text-light" href="manager">Manage Product</a>
//                    </li>
//                </c:if>
//                <c:if test="${sessionScope.acc.isSell == 1}">
//                    <li class="nav-item">
//                        <a class="nav-link text-light" href="finance">Manage Finance</a>
//                    </li>
//                </c:if> 
//                <c:if test="${sessionScope.acc != null}">
//                    <li class="nav-item">
//                        <a class="nav-link text-light" href="logout">Logout</a>
//                    </li>
//                </c:if>
//          <!-- Order List -->
//            <h2>Order List</h2>
//            <table>
//                <thead>
//                    <tr>
//                        <th>Name</th>
//                        <th>Email</th>
//                        <th>Phone</th>
//                        <th>Address</th>
//                        <th>Date</th>
//                        <th>Total</th>
//                        <th>Payment</th>
//                    </tr>
//                </thead>
//                <tbody>
//                    <c:if test="${empty orderList}">
//                        <tr>
//                            <td colspan="7" style="text-align: center;">No orders found.</td>
//                        </tr>
//                    </c:if>
//                    <c:forEach var="o" items="${orderList}">
//                        <tr>
//                            <td><c:out value="${o.name}" /></td>
//                            <td><c:out value="${o.email}" /></td>
//                            <td><c:out value="${o.phone}" /></td>
//                            <td><c:out value="${o.address}" /></td>
//                            <td><c:out value="${o.date}" /></td>
//                            <td><c:out value="${o.totalMoney}" /></td>
//                            <td><c:out value="${o.payment}" /></td>
//                        </tr>
//                    </c:forEach>
//                </tbody>
//            </table>
//
//            <c:if test="${not empty orderList}">
//                <div class="feedback-button">
//                    <a href="FeedBack.jsp">Leave Feedback</a>
//                </div>
//            </c:if>
