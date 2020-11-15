<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="style.css" media="screen"/>
</head>
<body>
<div class="topnav">
    <a class="active" href="#home">Home</a>
    <a href="#news">News</a>
    <a href="#contact">Contact</a>
    <a href="#about">About</a>
    <a href="#search">Search</a>
    <a href="login.jsp">Login</a>
</div>


<hr>
<section>
    <h1>Chat area</h1>
    <form action="MessageBoardServlet" method="POST">


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

        <button type="button">Delete Post!</button>
        <button type="button">Update Post!</button>
        <button type="button">View recent Post!</button>
        <button type="button">Download!</button>
        <button type="button">Clear Chat!</button>
        <button type="button">???!</button>
    </form>

    <form action="MessageBoardServlet" method="POST">
        <table>
            <tr>
                <thead></thead>
            </tr>
            <tr>

            </tr>
        </table>
    </form>


    <br>
    <hr>

    <form action="UploadServlet" method="post" enctype="multipart/form-data">
        <input type="file" name="filename" id="filename"
               accept=".txt,
application/pdf,
application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,
application/vnd.ms-excel,
application/vnd.openxmlformats-officedocument.wordprocessingml.document,
application/msword"/>
        <input type="submit" value="Save">
    </form>
    <%
        if (request.getAttribute("uploaded_message") != null) {
    %>
    <h3><%=request.getAttribute("uploaded_message")%></h3>
    <%
        }
    %>
</section>
</body>
</html>