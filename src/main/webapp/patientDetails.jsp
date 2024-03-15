<%--
  Created by IntelliJ IDEA.
  User: Ryanl
  Date: 3/13/2024
  Time: 6:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="uk.ac.ucl.model.Model" %>
<%@ page import="java.util.List" %>
<%
    String patientId = request.getParameter("id");
    Model model = new Model("path/to/your/data.csv");
    List<String> patientDetails = model.getPatientDetails(patientId);
%>
<html>
<head>
    <title>Patient Details</title>
</head>
<body>
<h1>Patient Details</h1>
<ul>
    <% for (String detail : patientDetails) { %>
    <li><%= detail %></li>
    <% } %>
</ul>
</body>
</html>