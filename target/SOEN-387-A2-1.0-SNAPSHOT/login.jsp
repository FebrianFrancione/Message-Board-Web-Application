<%--
Created by IntelliJ IDEA.
User: febri
Date: 08/11/2020
Time: 16:38
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="US-ASCII">
    <title>Chatr00m - Login</title>
    <script src="https://kit.fontawesome.com/f41ca6203f.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="style.css" media="screen"/>

</head>
<body>

<form action="loginServlet" method="post">
    <div class="login-box">
        <h1 class="login-box-h1">Please Login to Continue</h1>
        <div class="textbox">
            <i class="fas fa-user"></i>
            Username: <input type="text" name="username">
        </div>
        <div class="textbox">
            <i class="fas fa-lock"></i>
            Password: <input type="password" name="password">
        </div>
        <input class="btn" type="submit" value="Login">
    </div>
</form>
</body>
</html>