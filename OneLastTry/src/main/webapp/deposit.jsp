<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Deposit</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h2>Deposit Amount</h2>
        <form action="DepositServlet" method="post">
            <label for="amount">Amount:</label>
            <input type="number" id="amount" name="amount" required>
            <input type="submit" value="Deposit">
        </form>
    </div>
</body>
</html>
