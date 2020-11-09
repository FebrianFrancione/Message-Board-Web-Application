<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="../style.css" media="screen"/>
</head>
<body>
<%
    String message = null;
    String sessionID = null;
    Cookie[] cookies = request.getCookies();
    if(cookies != null){
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("message")) message = cookie.getValue();
            if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
        }
    }
%>

<div class="topnav">
    <a class="active" href="#home">Home</a>
    <a href="#news">News</a>
    <a href="#contact">Contact</a>
    <a href="#about">About</a>
    <form action="LogoutServlet" method="post">
    <a href=""><input type="submit" value="Logout" ></a></form>
    <input type="text" placeholder="Search..">
</div>



<%--<br><br>--%>
<%--<form action="LogoutServlet" method="post">--%>
<%--    <input type="submit" value="Logout" >--%>
<%--</form>--%>

<hr>
<section>

    <h1>Chat area</h1>
    <form action="MessageBoardServlet" method="POST">
        <input type="submit" name="create-post" value="Create">
        <button type="button">Delete Post!</button>
        <button type="button">Update Post!</button>
        <button type="button">View recent Post!</button>
        <button type="button">Download!</button>
        <button type="button">Clear Chat!</button>
        <button type="button">???!</button>
    </form>


    <p>Logged in</p>
    <p><%=message%></p>
    <p>Session ID = <%=sessionID %></p>

</section>
<%--<c:if test="${not empty loggedInUser}">--%>
<%--    <p>You're still logged in.</p>--%>
<%--</c:if>--%>
<%--<c:if test="${empty loggedInUser}">--%>
<%--    <p>You're not logged in!</p>--%>
<%--</c:if>--%>
<%--</section>--%>

</body>
</html>
