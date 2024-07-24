package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection to the database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Logindb", "root", "12345");

            // Prepare the SQL statement
            ps = con.prepareStatement("SELECT username FROM admin WHERE username=? AND password=?");
            ps.setString(1, username);
            ps.setString(2, password);

            // Execute the query
            rs = ps.executeQuery();

            // Check if the user exists
            if (rs.next()) {
                // User exists, redirect to dashboard
                response.sendRedirect("dashboard.jsp");
            } else {
                // User does not exist, show error message
                out.println("<html><body>");
                out.println("<h3>Invalid username or password</h3>");
                out.println("<a href='login.jsp'>Try again</a>");
                out.println("</body></html>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<html><body>");
            out.println("<h3>Error occurred: " + e.getMessage() + "</h3>");
            out.println("<a href='login.jsp'>Try again</a>");
            out.println("</body></html>");
        } finally {
            // Close resources
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (con != null) con.close(); } catch (Exception e) { e.printStackTrace(); }
        }

        out.close();
    }
}
