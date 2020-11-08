<%--
  Created by IntelliJ IDEA.
  User: febri
  Date: 08/11/2020
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p><b>First Name:</b>
    <%= request.getParameter("username")%>
</p>
        Welcome!
        <%= request.getParameter("userID")%>
</body>
</html>
