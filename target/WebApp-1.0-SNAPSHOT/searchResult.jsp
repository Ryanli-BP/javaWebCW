<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
  <style>
    .highlight {
      background-color: yellow;
    }
  </style>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <% String searchString = (String) request.getAttribute("searchstring"); %>
  <h1>Search Result</h1>
  <h3>Keyword: <%=searchString%></h3>
  <%
    List<String> patients = (List<String>) request.getAttribute("result");
    List<String> patientIDs = (List<String>) request.getAttribute("patientIDs");
    if (patients.size() !=0)
    {
  %>
  <ul>
    <%
      for (int i = 0; i < patients.size(); i++)
      {
        String patient = patients.get(i);
        String patientId = patientIDs.get(i);
        String highlightedPatient = patient.replaceAll("(?i)" + searchString, "<span class='highlight'>" + searchString + "</span>");
    %>
    <li><a href="patientDetails.html?id=<%=patientId%>"><%=highlightedPatient%></a></li>
    <% }
    }
    else {%>
    <p>Nothing found</p>
    <%}%>
  </ul>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>