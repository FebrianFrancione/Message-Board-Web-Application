<%--
  Created by IntelliJ IDEA.
  User: febri
  Date: 10/11/2020
  Time: 12:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="packages.database.DBConnection"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Title</title>
    <style>
        tr,td,th{
            padding:20px;
            text-align: center;
        }
    </style>
</head>
<body>
<br>
<div style="text-align: center;">
    <%!
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
    %>
    <table border="2">
        <tr>
            <th>ID</th><th>File Name</th><th>Date</th>
        </tr>
        <%
            con = DBConnection.getConnection();
            String sql = "select * from files";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
        %>
        <tr>
            <td><%= rs.getInt(1)%></td>
            <td><%= rs.getString(4)%></td>
            <td><%= rs.getTimestamp(3)%></td>

            <td> <form action="DownloadServlet" method="post">
                <input type="hidden" name="fileID" value="<%= rs.getInt(1)%>">
                <a href=""><input type="submit" value="download!" ></a></form>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</div>

</body>
</html>
