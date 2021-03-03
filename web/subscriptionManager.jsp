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
        List<Forum> allForum = (List<Forum>)request.getAttribute("listForum");
        List<Integer> subscriptIds = (List<Integer>)request.getAttribute("listSubscriptions");
        String type = (String)request.getAttribute("type");
        if (type != null && type.equalsIgnoreCase("all")) {
            for (Forum forum : allForum) {
                if (subscriptIds.contains(forum.getId())) {
    %>
                    <li><%=forum.toString() %> <a href="SubscriptionManager?type=unsubscribe&id_forum=<%=forum.getId()%>">Se désabonner</a></li>
    <%
                } else {
    %>
                    <li><%=forum.toString() %> <a href="SubscriptionManager?type=subscribe&id_forum=<%=forum.getId()%>">abonnement</a></li>
    <%
                }
            }
        } else {
            for (Forum forum : allForum) {
    %>
                <li><a href="ForumManager?type=detail&id=<%=forum.getId()%>"><%=forum.toString() %></a></li>
    <%       }
        }
    %>
</ul>
</body>
</html>
