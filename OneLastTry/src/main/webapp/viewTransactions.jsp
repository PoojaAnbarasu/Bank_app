<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException" %>
<%@ page import="java.util.ArrayList, java.util.List, java.util.Date, servlet.Transaction" %>
<%@ page import="java.io.StringWriter, java.io.PrintWriter" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Transactions</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .container {
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            max-width: 800px;
            width: 100%;
        }
        h1 {
            color: #333;
            margin-bottom: 20px;
        }
        form {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            font-size: 14px;
            color: #555;
        }
        input[type="text"], input[type="submit"] {
            width: calc(100% - 22px);
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
        }
        input[type="submit"] {
            background-color: #32CD32;
            color: white;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .button {
            display: inline-block;
            padding: 10px 20px;
            font-size: 16px;
            background-color: #1fab98;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            text-decoration: none;
            margin-top: 20px;
        }
        .button:hover {
            background-color: #9fe3da;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>View Transactions</h1>
        <form action="" method="get">
            <label for="accountNumber">Account Number:</label>
            <input type="text" id="accountNumber" name="accountNumber" required>
            <input type="submit" value="View Transactions">
        </form>
        <table>
            <thead>
                <tr>
                    <th>Transaction ID</th>
                    <th>Type</th>
                    <th>Amount</th>
                    <th>Date</th>
                </tr>
            </thead>
            <tbody>
                <%
                    String accountNumber = request.getParameter("accountNumber");

                    if (accountNumber == null || accountNumber.isEmpty()) {
                        out.println("<tr><td colspan='4'>Account number is missing.</td></tr>");
                    } else {
                        List<Transaction> transactions = new ArrayList<>();

                        Connection conn = null;
                        PreparedStatement pstmt = null;
                        ResultSet rs = null;

                        try {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/logindb", "root", "12345");
                            System.out.println("Connection established.");

                            String query = "SELECT * FROM transactions WHERE account_no = ? ORDER BY transaction_date DESC LIMIT 10"; 
                            pstmt = conn.prepareStatement(query);
                            pstmt.setString(1, accountNumber);
                            rs = pstmt.executeQuery();
                            System.out.println("Query executed: " + query);

                            while (rs.next()) {
                                Transaction transaction = new Transaction();
                                transaction.setId(rs.getInt("id"));
                                transaction.setType(rs.getString("type"));
                                transaction.setAmount(rs.getDouble("amount"));
                                transaction.setTransactionDate(new Date(rs.getTimestamp("transaction_date").getTime()));
                                transaction.setAccountNumber(rs.getString("account_no")); 
                                transactions.add(transaction);
                            }

                            if (!transactions.isEmpty()) {
                                for (Transaction transaction : transactions) {
                        %>
                        <tr>
                            <td><%= transaction.getId() %></td>
                            <td><%= transaction.getType() %></td>
                            <td><%= transaction.getAmount() %></td>
                            <td><%= transaction.getTransactionDate() %></td>
                        </tr>
                        <%
                                }
                            } else {
                                out.println("<tr><td colspan='4'>No transactions available for account number: " + accountNumber + ".</td></tr>");
                            }
                        } catch (ClassNotFoundException e) {
                            StringWriter sw = new StringWriter();
                            PrintWriter pw = new PrintWriter(sw);
                            e.printStackTrace(pw);
                            out.println("<tr><td colspan='4'>Error: JDBC Driver not found.<br>" + sw.toString() + "</td></tr>");
                            System.out.println("Error: JDBC Driver not found.");
                            e.printStackTrace();
                        } catch (SQLException e) {
                            StringWriter sw = new StringWriter();
                            PrintWriter pw = new PrintWriter(sw);
                            e.printStackTrace(pw);
                            out.println("<tr><td colspan='4'>Error: Database access error.<br>" + sw.toString() + "</td></tr>");
                            System.out.println("Error: Database access error.");
                            e.printStackTrace();
                        } finally {
                            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
                            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
                            if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
                        }
                    }
                %>
            </tbody>
        </table>
        <a href="#" class="button" onclick="window.print()">Print</a>
    </div>
</body>
</html>
