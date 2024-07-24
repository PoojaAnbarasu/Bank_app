package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/WithdrawServlet")
public class WithdrawServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Debugging statement to check session status
        System.out.println("Session ID: " + (session != null ? session.getId() : "No session"));

        if (session == null || session.getAttribute("accountNumber") == null) {
            System.out.println("Session is null or accountNumber is missing.");
            response.sendRedirect("customerLogin.jsp");
            return;
        }

        String accountNo = (String) session.getAttribute("accountNumber");
        Double currentBalance = (Double) session.getAttribute("balance");

        // Debugging statements to check retrieved session attributes
        System.out.println("Account Number: " + accountNo);
        System.out.println("Current Balance: " + currentBalance);

        double amount;
        try {
            amount = Double.parseDouble(request.getParameter("amount"));
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid amount.");
            request.getRequestDispatcher("withdraw.jsp").forward(request, response);
            return;
        }

        if (currentBalance == null || currentBalance < amount) {
            request.setAttribute("errorMessage", "Insufficient balance.");
            request.getRequestDispatcher("withdraw.jsp").forward(request, response);
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Logindb", "root", "12345");

            String updateBalanceSQL = "UPDATE customers SET initial_balance = initial_balance - ? WHERE account_no = ?";
            stmt = conn.prepareStatement(updateBalanceSQL);
            stmt.setDouble(1, amount);
            stmt.setString(2, accountNo);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                String insertTransactionSQL = "INSERT INTO transactions (account_no, type, amount, transaction_date) VALUES (?, 'Withdraw', ?, NOW())";
                PreparedStatement stmt2 = conn.prepareStatement(insertTransactionSQL);
                stmt2.setString(1, accountNo);
                stmt2.setDouble(2, amount);

                int transactionInserted = stmt2.executeUpdate();
                stmt2.close();

                if (transactionInserted > 0) {
                    session.setAttribute("balance", currentBalance - amount);
                    response.sendRedirect("customerDashboard.jsp");
                } else {
                    System.out.println("Transaction insertion failed.");
                    response.sendRedirect("error.jsp");
                }
            } else {
                System.out.println("Balance update failed.");
                response.sendRedirect("error.jsp");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
