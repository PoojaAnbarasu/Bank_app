package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterCustomerServlet")
public class RegisterCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        System.out.println("Starting customer registration process...");

        String fullName = request.getParameter("full_name");
        String address = request.getParameter("address");
        String mobileNo = request.getParameter("mobile_no");
        String email = request.getParameter("email");
        String accountType = request.getParameter("account_type");
        String initialBalance = request.getParameter("initial_balance");
        String dateOfBirth = request.getParameter("date_of_birth");
        String idProof = request.getParameter("id_proof");

        // Generate account number and temporary password
        String accountNo = "ACC" + System.currentTimeMillis();
        String tempPassword = "temp" + System.currentTimeMillis();

        try {
            // Debugging: Print input parameters
            System.out.println("Received parameters:");
            System.out.println("Full Name: " + fullName);
            System.out.println("Address: " + address);
            System.out.println("Mobile No: " + mobileNo);
            System.out.println("Email: " + email);
            System.out.println("Account Type: " + accountType);
            System.out.println("Initial Balance: " + initialBalance);
            System.out.println("Date of Birth: " + dateOfBirth);
            System.out.println("ID Proof: " + idProof);
            System.out.println("Generated Account No: " + accountNo);
            System.out.println("Generated Temp Password: " + tempPassword);

            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver registered.");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Logindb", "root", "12345");
            System.out.println("Database connection established.");

            String query = "INSERT INTO customers (full_name, address, mobile_no, email, account_type, initial_balance, date_of_birth, id_proof, account_no, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, fullName);
            ps.setString(2, address);
            ps.setString(3, mobileNo);
            ps.setString(4, email);
            ps.setString(5, accountType);
            ps.setBigDecimal(6, new BigDecimal(initialBalance));
            ps.setDate(7, Date.valueOf(dateOfBirth));
            ps.setString(8, idProof);
            ps.setString(9, accountNo);
            ps.setString(10, tempPassword);

            // Debugging: Print the prepared statement before execution
            System.out.println("Executing query: " + ps.toString());

            int i = ps.executeUpdate();
            if (i > 0) {
                out.println("Customer registered successfully.<br>");
                out.println("Account No: " + accountNo + "<br>");
                out.println("Temporary Password: " + tempPassword + "<br>");
                // Debugging: Print success message
                System.out.println("Customer registered successfully.");
            } else {
                out.println("Failed to register customer.");
                // Debugging: Print failure message
                System.out.println("Failed to register customer.");
            }

            ps.close();
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // Debugging: Print the exception
            System.out.println("ClassNotFoundException: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            // Debugging: Print the exception
            System.out.println("SQLException: " + e.getMessage());
        }

        out.close();
        System.out.println("Finished customer registration process.");
    }
}
