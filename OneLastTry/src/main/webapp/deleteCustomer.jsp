<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>Delete Customer</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h2>Delete Customer</h2>
        <form action="DeleteCustomerServlet" method="post">
            Customer ID: <input type="text" name="id" required><br>
            <input type="submit" value="Delete">
        </form>
    </div>
</body>
</html>

    