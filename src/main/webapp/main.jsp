<%@ page import="packages.businessLayer.MessageBoard" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="https://kit.fontawesome.com/f41ca6203f.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="style.css" media="screen"/>

</head>
<body>

<div class="navbar">
    <ul class="navbar-nav">
        <li class="nav-item">
            <a href="README.jsp" class="nav-link">
                <i class="fab fa-readme"></i>
                <span class="link-text">About</span>
            </a>
        </li>
        <li class="nav-item">

            <a href="login.jsp" class="nav-link">
                <i class="fas fa-sign-in-alt"></i>
                <span class="link-text">Login</span>
            </a>
        </li>
    </ul>
    <%--    <a class="active" href="#home">Home</a>--%>
<%--        <a href="README.jsp">About</a>--%>
    <%--    <a href="#search">Search</a>--%>
<%--        <a href="login.jsp">Login</a>--%>
</div>
<main>
    <h1>Chat area</h1>
    <%! MessageBoard msgboard = new MessageBoard(); %>
    <%! int i = 10; String reverse = "";%>
    <% if (request.getAttribute("numberOfPosts") != null) {i = (int) request.getAttribute("numberOfPosts");} %>
    <% if (request.getAttribute("recentPosts") != null) {reverse = request.getAttribute("recentPosts").toString();}else{reverse = "false";} %>
    <%= msgboard.display(i, reverse)%>
    <%--  <form action="MessageBoardServlet" method="POST">


          <div class="post-message">
              <div class="post-message-side">

              </div>
              <div class="post-message-header">
                  <input class="post-message-header1" type="text" name="message">
                  <input class="post-message-header2" type="file" name="attachment" value="Add attachment">
              </div>
              <div>
                  <input class="post-message-body" type="submit" name="create-post" value="Create">
              </div>
          </div>
      </form>--%>
    <br>
    <hr>

    <%--   <form action="UploadServlet" method="post" enctype="multipart/form-data">
           <input type="file" name="filename" id="filename"
                  accept=".txt,
   application/pdf,
   application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,
   application/vnd.ms-excel,
   application/vnd.openxmlformats-officedocument.wordprocessingml.document,
   application/msword"/>
           <input type="submit" value="Save">
       </form>--%>
    <%
        if (request.getAttribute("uploaded_message") != null) {
    %>
    <h3><%=request.getAttribute("uploaded_message")%></h3>
    <%
        }
    %>
</main>
</body>
</html>