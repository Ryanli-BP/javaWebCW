<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Ryanl
  Date: 3/16/2024
  Time: 12:23 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<% List<String> detailNames = (List<String>) request.getAttribute("columnNames"); %>
<form method="POST" action="addPatient.html">
    <%
        for(String detailName : detailNames) {
    %>
    <label for="<%=detailName%>"><%=detailName%></label>
    <input type="text" id="<%=detailName%>" name="<%=detailName%>"/>
    <hr>
    <% } %>
    <input type="submit" value="add"/>
</form>
</body>
</html>