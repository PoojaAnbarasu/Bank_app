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
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account_no") == null) {
            System.out.println("Session is null or account_no is not set");
            response.sendRedirect("customerLogin.jsp");
            return;
        }

        String accountNo = (String) session.getAttribute("account_no");
        System.out.println("Account No: " + accountNo);

        double amount = Double.parseDouble(request.getParameter("amount"));
        System.out.println("Deposit Amount: " + amount);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Logindb", "root", "12345");

            // Update the customer's balance
            String updateBalanceSQL = "UPDATE customers SET initial_balance = initial_balance + ? WHERE account_no = ?";
            PreparedStatement pstmt = conn.prepareStatement(updateBalanceSQL);
            pstmt.setDouble(1, amount);
            pstmt.setString(2, accountNo);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                // Insert the deposit transaction
                String insertTransactionSQL = "INSERT INTO transactions (account_no, amount, type) VALUES (?, ?, 'Deposit')";
                PreparedStatement pstmt2 = conn.prepareStatement(insertTransactionSQL);
                pstmt2.setString(1, accountNo);
                pstmt2.setDouble(2, amount);

                pstmt2.executeUpdate();

                // Retrieve the updated balance
                String getUpdatedBalanceSQL = "SELECT initial_balance FROM customers WHERE account_no = ?";
                PreparedStatement pstmt3 = conn.prepareStatement(getUpdatedBalanceSQL);
                pstmt3.setString(1, accountNo);
                ResultSet rs = pstmt3.executeQuery();

                if (rs.next()) {
                    double updatedBalance = rs.getDouble("initial_balance");
                    session.setAttribute("balance", updatedBalance);
                }

                response.sendRedirect("customerDashboard.jsp");
            } else {
                response.sendRedirect("error.jsp");
            }

            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
