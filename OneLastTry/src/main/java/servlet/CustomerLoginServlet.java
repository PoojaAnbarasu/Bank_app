package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CustomerLoginServlet")
public class CustomerLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Logindb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber").trim();
        String password = request.getParameter("password").trim();

        System.out.println("Account Number: " + accountNumber);
        System.out.println("Password: " + password);

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Load JDBC driver
            Class.forName(JDBC_DRIVER);
            // Connect to the database
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Query to find the customer with the provided account number and password
            String sql = "SELECT * FROM customers WHERE account_no = ? AND password = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, accountNumber);
            stmt.setString(2, password);

            rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Customer found in the database.");
                // If customer found, create a session and store customer details
                HttpSession session = request.getSession();
                session.setAttribute("accountNumber", accountNumber); // Ensure this name is consistent
                session.setAttribute("customer", rs.getString("full_name"));
                session.setAttribute("balance", rs.getDouble("initial_balance"));

                // Redirect to customer dashboard
                response.sendRedirect("customerDashboard.jsp");
            } else {
                System.out.println("Customer not found in the database.");
                // If no matching customer found, set error message and redirect back to login page
                request.setAttribute("errorMessage", "Invalid account number or password. Please try again.");
                request.getRequestDispatcher("customerLogin.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            // Log the SQL exception for debugging purposes
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        } catch (ClassNotFoundException e) {
            // Log the ClassNotFound exception for debugging purposes
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        } finally {
            // Close resources to avoid memory leaks
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
