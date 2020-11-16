

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>List of Posts</title>
</head>
<body>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of Posts</h2></caption>
        <tr>
            <th>postID</th>
            <th>user</th>
            <th>text</th>
            <th>Date</th>
            <th>tags</th>
            <th>lastUpdated</th>
        </tr>
        <c:forEach var="post" items="${listPost}">
            <tr>
                <td><c:out value="${post.postID}" /></td>
                <td><c:out value="${post.username}" /></td>
                <td><c:out value="${post.text}" /></td>
<%--                //displays nothing--%>
<%--                <td><c:out value="${post.attachment}" /></td>--%>
                <td><c:out value="${post.date}" /></td>
                <td><c:out value="${post.tags}" /></td>
<%--                //displays nothing--%>
                <td><c:out value="${post.lastUpdated}" /></td>
            </tr>
<%--            <c:out value="${post.date}" />--%>
        </c:forEach>
    </table>
</div>
</body>
</html>