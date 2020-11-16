<%@ page import="packages.businessLayer.MessageBoard" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Chatr00m - Home</title>
    <script src="https://kit.fontawesome.com/f41ca6203f.js" crossorigin="anonymous"></script>
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

<div class="navbar">
    <a href="#home" class="nav-link"><i class="fas fa-home"></i><span class="link-text">Home</span></a>
    <a href="#news" onclick="alertBox();return false;"  class="nav-link"><i class="far fa-newspaper"></i><span class="link-text">News</span></a>
    <a href="#contacts" onclick="alertBox();return false;"  class="nav-link"><i class="fas fa-id-card"></i><span class="link-text">Contact</span></a>
    <a href="searchPage.jsp"  class="nav-link"><i class="fas fa-search"></i><span class="link-text">Search</span></a>
    <a href="README.jsp" class="nav-link"><i class="fab fa-readme"></i><span class="link-text">About</span></a>

    <form id="form1" action="LogoutServlet" method="post">
        <a href="javascript:;" onclick="document.getElementById('form1').submit();" class="nav-link"><i class="fas fa-sign-out-alt"></i><span class="link-text">Log Out</span></a>
        <input type="hidden" name="mess" />
    </form>
</div>

<script src="node_modules/sweetalert2/dist/sweetalert2.all.min.js"></script>
<script>
    function alertBox(){
        alert('Oops...the feature is not implemented at the moment.')
    }
</script>

<%--<br><br>--%>
<%--<form action="LogoutServlet" method="post">--%>
<%--    <input type="submit" value="Logout" >--%>
<%--</form>--%>

<hr>
<main>

    <h1>Chat area</h1>
    <%! MessageBoard msgboard = new MessageBoard(); %>
    <%! int i = 10; String reverse = ""; ServletContext context = null;%>
    <% if (request.getAttribute("numberOfPosts") != null) {i = (int) request.getAttribute("numberOfPosts");} %>
    <% if (request.getAttribute("recentPosts") != null) {reverse = request.getAttribute("recentPosts").toString();}else{reverse = "false";} %>
    <% if (request.getAttribute("context") != null) {context = (ServletContext) request.getAttribute("context");}else{context = null;} %>
    <% if (context != null) { %>
        <%=msgboard.display(i, reverse, context)%>
    <%}%>


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

        <label for="viewRecently">Select number of posts to display: </label>
        <select id="viewRecently" name="numberOfPosts">
            <option></option>
            <option>1</option>
            <option>5</option>
            <option>10</option>
        </select>
        <input type="submit" name="viewRecently" value="View Recent Posts"/>

    </form>
        <br><br>
        <form action="DownloadServlet" method="post" enctype="multipart/form-data">
        <button type="button">Download!</button>
            <input type="submit" name="download1" value="download attachment">
        </form>
    <a href="file_list.jsp">View List</a>
        <button type="button">Clear Chat!</button>
        <button type="button">???!</button>




    <div style="text-align: center;">
        <h2>
            <form action="PostServlet" method="post">

<%--                <a href="/PostServlet">List All Books</a>--%>
                <input type="submit" value="list all Posts">
            </form>
        </h2>
    </div>

    <p>Logged in</p>
    <p><%=message%></p>
<%--    <p>Session ID = <%=sessionID %></p>--%>

</main>
<%--<c:if test="${not empty loggedInUser}">--%>
<%--    <p>You're still logged in.</p>--%>
<%--</c:if>--%>
<%--<c:if test="${empty loggedInUser}">--%>
<%--    <p>You're not logged in!</p>--%>
<%--</c:if>--%>
<%--</section>--%>

</body>
</html>
