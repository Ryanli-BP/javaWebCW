<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<% List<String> columnNames = (List<String>) request.getAttribute("columnNames"); %>
<form method="POST" action="addPatient.html">
    <%
        for(String columnName : columnNames) {
    %>
    <label for="<%=columnName%>"><%=columnName%></label>
    <input type="text" id="<%=columnName%>" name="<%=columnName%>"/>
    <hr>
    <% } %>
    <input type="submit" value="add"/>
</form>
</body>
</html>