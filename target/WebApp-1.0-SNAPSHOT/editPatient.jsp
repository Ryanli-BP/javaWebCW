<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %><%--
  Created by IntelliJ IDEA.
  User: Ryanl
  Date: 3/16/2024
  Time: 12:23 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Patient Details</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<form action="editPatient.html" method="GET">
    <label for="patientID">Patient ID:</label><br>
    <input type="text" id="patientID" name="patientID"><br>
    <input type="submit" value="Submit">
</form>
<% if(request.getAttribute("patientDetails") != null) { %>
<form method="POST" action="editPatient.html">
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
    <label for="<%=detailName%>"><%=detailName%></label>
    <input type="text" id="<%=detailName%>" name="<%=detailName%>" value="<%=detailValue%>"/>
    <hr>
    <% } %>
    <input type="hidden" name="patientID" value="<%= patientId %>"/>
    <input type="submit" value="Save"/>
</form>
<% } else if (request.getParameter("patientID") != null) { %>
<p>No patient found with the provided ID.</p>
<% } %>
</body>
</html>