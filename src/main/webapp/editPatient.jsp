<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Patient Details</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<form method="GET" action="editPatient.html">
    <input type="text" name="patientID" placeholder="Enter PatientID here"/>
    <input type="submit" value="Search">
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