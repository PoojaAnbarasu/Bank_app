<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>Modify Customer</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h2>Modify Customer</h2>
        <form action="ModifyCustomerServlet" method="post">
            Customer ID: <input type="text" name="id" required><br>
            New Full Name: <input type="text" name="newName" required><br>
            New Address: <input type="text" name="newAddress" required><br>
            New Mobile No: <input type="text" name="newMobileNo" required><br>
            New Email ID: <input type="email" name="newEmail" required><br>
            New Type of Account: 
            <select name="newAccountType">
                <option value="Saving">Saving</option>
                <option value="Current">Current</option>
            </select><br>
            New Date of Birth: <input type="date" name="newDateOfBirth" required><br>
            New ID Proof: <input type="text" name="newIdProof" required><br>
            <input type="submit" value="Modify">
        </form>
    </div>
</body>
</html>


    