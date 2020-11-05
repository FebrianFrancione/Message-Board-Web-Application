<%--
  Created by IntelliJ IDEA.
  User: febri
  Date: 04/11/2020
  Time: 22:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User register</title>
</head>
<body>

<div align="center">
    <h1> register form </h1>
    <form action = "<%= request.getContextPath()%>/MessageBoardServlet" method="post">
        <table style = "width: 80%">
            <tr>
                <td>
                    USername
                </td>
                <td><input type="text" name ="username" /></td>
            </tr>
            <tr>
                <td>
                    password
                </td>
                <td><input type="text" name ="password" /></td>
            </tr>
            <tr>
                <td>
                    email
                </td>
                <td><input type="text" name ="email" /></td>
            </tr>
        </table>
        <input type = "submit" value="Submit" />
    </form>
</div>
</body>
</html>
