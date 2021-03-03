<%@ page import="Model.Forum" %>
<%@ page import="Model.Message" %>
<%@ page import="java.util.List" %>
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
    <meta http-equiv = "refresh" content = "60">
    <title>JSP Page</title>
</head>
<body>
    <%
        Forum forum = (Forum)request.getAttribute("forum");
        List<Message> listMessage = (List<Message>)request.getAttribute("listMessage");
    %>
    <h1><%=forum.getTitle()%></h1>
    <p>
        <%=forum.getDescription()%>
    </p>
    <ul>
        <% for (Message message : listMessage) { %>
                <li><%=message.getContent()%> by <%=message.getEditor().getLogin()%></li>
        <% } %>
    </ul>

    <form action="ForumManager" method="post">
        <input type="hidden" value="<%=forum.getId()%>" name="id" />
        <label> Content </label>
        <textarea id="content" name="Message Content" required></textarea>
        <br>
        <input type="submit" value="Submit">
    </form>
</body>
</html>
