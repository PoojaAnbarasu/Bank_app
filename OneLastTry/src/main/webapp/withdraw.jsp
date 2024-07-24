<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Withdraw</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h2>Withdraw</h2>
        <form action="WithdrawServlet" method="post">
            Amount: <input type="number" name="amount" required><br>
            <input type="submit" value="Withdraw">
        </form>
    </div>
</body>
</html>
