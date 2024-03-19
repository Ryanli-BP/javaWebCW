<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Ryanl
  Date: 3/16/2024
  Time: 12:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete Patient</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<form method="GET" action="deletePatient.html">
    <input type="text" name="patientID" placeholder="Enter PatientID here"/>
    <input type="submit" value="Search"/>
</form>
<% if(request.getAttribute("patientDetails") != null) { %>
<ul>
    <% List<String> details = (List<String>) request.getAttribute("patientDetails");
        String patientId = null; // This will be used to store the patient ID, as the old one is lost
        for(String detail : details) {
            String[] splitDetail = detail.split(":");
            String detailName = splitDetail[0].trim();
            String detailValue = splitDetail[1].trim();
            if ("ID".equals(detailName)) {
                patientId = detailValue;
            }
    %>
    <li><%= detail %></li>
    <hr>
    <% } %>
</ul>
<% if (patientId != null) { %>
<form method="POST" action="deletePatient.html">
    <input type="hidden" name="patientID" value="<%= patientId %>"/>
    <input type="submit" value="Delete"/>
</form>
<% } %>
<% } else if (request.getParameter("patientID") != null) { %>
<p>No patient found with the provided ID.</p>
<% } %>
</body>
</html>