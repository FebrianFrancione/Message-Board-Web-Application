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
    <a href="login.jsp">Login</a>
    <input type="text" placeholder="Search..">
</div>


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
</section>
</body>
</html>
