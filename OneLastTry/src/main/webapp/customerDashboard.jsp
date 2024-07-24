<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.*, javax.servlet.*" %>
<%
    HttpSession httpSession = request.getSession(false);
    if (httpSession == null || httpSession.getAttribute("customer") == null) {
        response.sendRedirect("customerLogin.jsp");
        return;
    }
    String customer = (String) httpSession.getAttribute("customer");
    String accountNo = (String) httpSession.getAttribute("accountNumber"); // Consistent attribute name
    Double balance = (Double) httpSession.getAttribute("balance");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Dashboard</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <div class="container center">
        <h2>Customer Dashboard</h2>
        <h3>Welcome, <%= customer %></h3>
        <p>Account Number: <%= accountNo %></p>
        <p>Balance: <%= balance %></p>
        <a href="viewTransactions.jsp" class="button">View Transactions</a><br>
        <a href="deposit.jsp" class="button">Deposit</a><br>
        <a href="withdraw.jsp" class="button">Withdraw</a><br>
        <a href="closeAccount.jsp" class="button">Close Account</a><br>
        <a href="setNewPassword.jsp" class="button">Set New Password</a><br>
        <a href="logout.jsp" class="button">Logout</a><br>
    </div>
</body>
</html>
