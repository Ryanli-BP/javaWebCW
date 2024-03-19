<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Patient Details</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<h1>Patient Details</h1>
<ul>
    <% List<String> details = (List<String>) request.getAttribute("patientDetails");
        for(String detail : details) { %>
    <li><%= detail %></li>
    <hr>
    <% } %>
</ul>
</body>
</html>