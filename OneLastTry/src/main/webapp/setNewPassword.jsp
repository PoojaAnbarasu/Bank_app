<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Set New Password</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h2>Set New Password</h2>
        <form action="SetNewPasswordServlet" method="post">
            New Password: <input type="password" name="newPassword" required><br>
            <input type="submit" value="Set Password">
        </form>
    </div>
</body>
</html>
