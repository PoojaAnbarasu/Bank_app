<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
    <title>Register Customer</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h2>Register Customer</h2>
        <form action="RegisterCustomerServlet" method="post">
            Full Name: <input type="text" name="full_name" required><br>
            Address: <input type="text" name="address" required><br>
            Mobile No: <input type="text" name="mobile_no" required><br>
            Email ID: <input type="email" name="email" required><br>
            Type of Account: 
            <select name="account_type">
                <option value="Saving">Saving</option>
                <option value="Current">Current</option>
            </select><br>
            Initial Balance: <input type="number" name="initial_balance" min="1000" required><br>
            Date of Birth: <input type="date" name="date_of_birth" required><br>
            ID Proof: <input type="text" name="id_proof" required><br>
            <input type="submit" value="Register">
        </form>
    </div>
</body>
</html>
