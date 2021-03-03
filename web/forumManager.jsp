<%@ page import="java.util.List" %>
<%@ page import="Model.Forum" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/5/25
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
</head>
<body>
    <ul>
        <%
            List<Forum> listForum = (List<Forum>)request.getAttribute("listForum");
            for(Forum forum: listForum) {
        %>
        <li><%=forum.toString() %> <a href="ForumManager?type=del&id=<%=forum.getId()%>">Delete</a> <a href="ForumManager?type=detail&id=<%=forum.getId()%>">Detail</a></li>
        <%
            }
        %>
    </ul>
    <form action="ForumManager" method="post">
        <label> Title </label>
        <input type="text" id="title" name="Forum title" required/>
        <br>
        <label> Description </label>
        <textarea id="description" name="Forum Description" required></textarea>
        <br>
        <input type="submit" value="Submit">
    </form>
</body>
</html>
