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
<main>

    <h1 class="h1-main">Chat area</h1>
    <p>Logged in</p>
    <p><%=message%></p>
    <%! MessageBoard msgboard = new MessageBoard(); %>
    <%! int i = 10; String reverse = ""; ServletContext context = null; int postLimit = 0;%>
    <% if (request.getAttribute("numberOfPosts") != null) {i = (int) request.getAttribute("numberOfPosts");} %>
    <% if (request.getAttribute("recentPosts") != null) {reverse = request.getAttribute("recentPosts").toString();}else{reverse = "false";} %>
    <% if (request.getAttribute("context") != null) {context = (ServletContext) request.getAttribute("context");}else{context = null;} %>
    <% postLimit = 0;%>
    <c:forEach var="post" items="${listPost}">
        <% if (postLimit < i) {%>
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
        <%postLimit ++;%>
        <%}%>
    </c:forEach>
    </table>


    <form action="MessageBoardServlet" method="post" enctype="multipart/form-data">
        <div class="post-box">
            <h1 class="post-box-h1">Post:</h1>
            <div class="post-box-text">
                <label for="message">Enter Text Message</label>
                <input id="message" type="text" name="message">
            </div>

            <div class="post-box-tag">
                <label for="tags">Enter #Tags (Separate by space)</label>
                <input class="post-box-tag-input1" id="tags" type="text" name="tags">

                <input class="post-box-tag-input2" type="file" name="photo" value="Add attachment">
            </div>
            <div></div>
            <div class="post-box-submit">
                <input class="btn2" type="submit" name="create" value="Create">
            </div>
        </div>

        <br><br>

        <div class="post-box">
            <label for="deletePost">Select Post's ID to delete:</label>
            <select id="deletePost" name="deletePost">
                <c:forEach var="post" items="${listPost}">
                    <option>${post.postID}</option>
                </c:forEach>
            </select>
            <input type="submit" name="delete" value="Delete Post"/>

            <br><br>

            <label for="updatePost">Select Post's ID to update:</label>
            <select id="updatePost" name="updatePost">
                <c:forEach var="post" items="${listPost}">
                    <option>${post.postID}</option>
                </c:forEach>
            </select>
            <div>
                <label for="updatedMessage">Enter Desired Message Here</label>
                <input id="updatedMessage" type="text" name="updatedMessage">
                <br>
                <label for="updatedTags">Enter Desired tags here</label>
                <br>
                <input id="updatedTags" type="text" name="updatedTags">
                <input type="file" name="updatedAttachment" value="Add attachment">
                <input class="btn3" type="submit" name="update" value="Update Post"/>
            </div>
        </div>
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
        <input type="submit" name="download1" value="download attachment">
    </form>
    <a href="file_list.jsp">View List</a>


    <div style="text-align: center;">
        <h2>
            <form action="PostServlet" method="post">

                <%--                <a href="/PostServlet">List All Books</a>--%>
                <input type="submit" value="list all Posts">
            </form>
        </h2>
    </div>


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