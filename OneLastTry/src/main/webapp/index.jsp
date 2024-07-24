<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>Banking Application</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            margin-top: 100px;
        }
        .container {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .button {
            display: inline-block;
            padding: 15px 25px;
            font-size: 16px;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
            outline: none;
            color: #fff;
            background-color: #1fab98;
            border: none;
            border-radius: 15px;
            
            margin: 10px;
        }
        .button:hover {background-color: #4ac7b6}
        .button:active {
            background-color: #1fab98;
            box-shadow: 0 5px #666;
            transform: translateY(4px);
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Welcome to Banking App</h1>
        <a href="login.jsp" class="button">Admin Login</a>
        <a href="customerLogin.jsp" class="button">Customer Login</a>
    </div>
</body>
</html>
    