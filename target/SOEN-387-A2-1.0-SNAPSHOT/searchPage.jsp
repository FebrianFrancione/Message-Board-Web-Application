<%@ page import="packages.businessLayer.MessageBoard" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Chatr00m - Search</title>
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
    <a href="loggedIn.jsp" class="nav-link"><i class="fas fa-home"></i><span class="link-text">Home</span></a>
    <a href="#news" onclick="alertBox();return false;"  class="nav-link"><i class="far fa-newspaper"></i><span class="link-text">News</span></a>
    <a href="#contacts" onclick="alertBox();return false;"  class="nav-link"><i class="fas fa-id-card"></i><span class="link-text">Contact</span></a>
    <a href="search"  class="nav-link"><i class="fas fa-search"></i><span class="link-text">Search</span></a>
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

    <h1>Search area</h1>
    <%! MessageBoard msgboard = new MessageBoard(); %>
    <%! int i = 10;
        String reverse = "";
        String option = "";
        String username = "";
        String dateRangeFrom = "";
        String dateRangeTo = "";
        String tag = "";
        String dateSort = "";
        ServletContext context = null;
    %>
    <% if (request.getAttribute("recentPosts") != null) {reverse = request.getAttribute("recentPosts").toString();}else{reverse = "false";} %>
    <% if (request.getAttribute("option") != null) {option = request.getAttribute("option").toString();}else{option = null;} %>
    <% if (request.getAttribute("username") != null) {username = request.getAttribute("username").toString();}else{username = null;} %>
    <% if (request.getAttribute("dateRangeFrom") != null) {dateRangeFrom = request.getAttribute("dateRangeFrom").toString();}else{dateRangeFrom = null;} %>
    <% if (request.getAttribute("dateRangeTo") != null) {dateRangeTo = request.getAttribute("dateRangeTo").toString();}else{dateRangeTo = null;} %>
    <% if (request.getAttribute("tag") != null) {tag = request.getAttribute("tag").toString();}else{tag = null;} %>
    <% if (request.getAttribute("dateSort") != null) {dateSort = request.getAttribute("dateSort").toString();}else{dateSort = null;} %>
    <% if (request.getAttribute("context") != null) {context = (ServletContext) request.getAttribute("context");}else{context = null;} %>



    <form action="SearchServlet" method="post">
        <div>
            <label for="searchUser">Search for Post by User:</label>
            <input type="text" placeholder="Search.." name="searchUsername">
        </div>
        <div>
            <br>
            <span for="updatePost">Search for Post by Date Range:</span>
            <label for="searchByDate">From:</label>
            <input placeholder="yyyy-MM-dd HH:mm:ss" name="fromDate" >
            <label for="searchByDate">To:</label>
            <input placeholder="yyyy-MM-dd HH:mm:ss" name="toDate" >
        </div>
        <div>
            <br>
            <label for="searchByTags">Search for Post by Tag:</label>
            <input type="text" placeholder="Search.." name="searchByTags">
            <br>
        </div>
        <div>
            <br>
            <br>
            <span for="updatePost">Search filtering option:</span>
            <input type="radio" id="searchUser" name="searchOption" value="searchUser">
            <label for="searchUser">Search by User</label>
            <input type="radio" id="searchByDate" name="searchOption" value="searchByDate">
            <label for="searchByDate">Search by Date Range</label>
            <input type="radio" id="searchByTags" name="searchOption" value="searchByTags">
            <label for="searchByTags">Search by Tags</label>
<%--            <input type="radio" id="searchAll" name="searchOption" value="searchAll">--%>
<%--            <label for="searchAll">Search for all at the same time</label>--%>
                <br>
                <br>
            <span for="updatePost">Sorting filtering option:</span>
            <input type="radio" id="dateOld" name="dateSorting" value="false">
            <label for="dateOld">Old to New</label>
            <input type="radio" id="dateNew" name="dateSorting" value="true">
            <label for="dateNew">New to Old</label>
                <br>
                <br>
                <input type="submit">
                <input type="reset" value="Clear the textfields">
            </div>
    </form>
    <br>

<%--    <% if (context != null) { %>--%>
<%--            <%=msgboard.search(option, username, dateRangeFrom, dateRangeTo, tag, 100, dateSort, context)%>--%>
<%--    <%}%>--%>
    <c:forEach var="post" items="${listPost}">
        <div class="post" style="margin: 0 20px; padding: 10px;" id="${post.userID}">
            <div class="post-ids" style="display: flex; flex-direction: row; justify-content: space-between;">
                <div>Username: ${post.username} </div>
                <div>Post id: ${post.postID} </div>
            </div>
            <div class="post-body" style="font-size: 20px; margin-top: 20px;"> ${post.text} </div>
            <div class="post-tags" style="margin-top: 20px; color: grey;"> ${post.tags} </div>
            <div class="post-date" style="margin-top: 10px; font-size: 12px;"> ${post.date} <br> Last Updated: ${post.lastUpdated} </div>
            <div style="margin-top: 15px;"><img style="width:200px; height: 250px;" src="data:image/jpeg;base64,${post.imageString}" alt="no attachment"></div>
        </div>
    </c:forEach>
<%--    <p>Logged in</p>--%>

<%--    <p>Session ID = <%=sessionID %></p>--%>

</main>

</body>
</html>
