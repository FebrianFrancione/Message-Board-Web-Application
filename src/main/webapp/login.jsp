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
    <title>Login Page</title>
    <h1>Please login to continue</h1>
</head>
<body>

<form action="loginServlet" method="post">

    Username: <input type="text" name="username">
    <br>
    Password: <input type="password" name="pwd">
    <br><br>
    <input type="submit" value="Login">
</form>
</body>
</html>