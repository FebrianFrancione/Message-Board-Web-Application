<%@ page import="packages.businessLayer.MessageBoard" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="style.css" media="screen"/>
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
    <%! MessageBoard msgboard = new MessageBoard(); %>
    <%= msgboard.display()%>


    <form action="MessageBoardServlet" method="post" enctype="multipart/form-data">
        <label for="message">Enter Text Message</label>
        <input id="message" type="text" name="message">
        <label for="tags">Enter #Tags (Separate by space)</label>
        <input id="tags" type="text" name="tags">
        <input type="file" name="photo" value="Add attachment">
        <input type="submit" name="create" value="Create">

        <br><br>

        <label for="deletePost">Select Post's ID to delete:</label>
        <select id="deletePost" name="deletePost">
            <%! ArrayList<Integer> IDs = msgboard.retrievePostIDs();%>
            <%
                for (int id: IDs) {
            %> <option><%=id%></option> <%
                }
            %>
        </select>
        <input type="submit" name="delete" value="Delete Post"/>

        <br><br>

        <label for="updatePost">Select Post's ID to update:</label>
        <select id="updatePost" name="updatePost">
            <%! ArrayList<Integer> postIDs = msgboard.retrievePostIDs();%>
            <%
                for (int id: postIDs) {
            %> <option><%=id%></option> <%
            }
        %>
        </select>
        <label for="updatedMessage">Enter Desired Message Here</label>
        <input id="updatedMessage" type="text" name="updatedMessage">
        <label for="updatedTags">Enter Desired tags here</label>
        <input id="updatedTags" type="text" name="updatedTags">
        <input type="file" name="updatedAttachment" value="Add attachment">
        <input type="submit" name="update" value="Update Post"/>

        <br><br>

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
