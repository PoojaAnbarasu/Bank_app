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

@WebServlet("/ViewCustomerServlet")
public class ViewCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Logindb", "root", "12345");

            PreparedStatement ps = con.prepareStatement("SELECT id, full_name, address, mobile_no, email, account_type, date_of_birth, id_proof, account_no FROM customers");

            ResultSet rs = ps.executeQuery();

            out.println("<html><body>");
            out.println("<h2>Customer Details</h2>");
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Full Name</th><th>Address</th><th>Mobile No</th><th>Email</th><th>Account Type</th><th>Date of Birth</th><th>ID Proof</th><th>Account No</th></tr>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("full_name") + "</td>");
                out.println("<td>" + rs.getString("address") + "</td>");
                out.println("<td>" + rs.getString("mobile_no") + "</td>");
                out.println("<td>" + rs.getString("email") + "</td>");
                out.println("<td>" + rs.getString("account_type") + "</td>");
                out.println("<td>" + rs.getDate("date_of_birth") + "</td>");
                out.println("<td>" + rs.getString("id_proof") + "</td>");
                out.println("<td>" + rs.getString("account_no") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body></html>");

            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        out.close();
    }
}
