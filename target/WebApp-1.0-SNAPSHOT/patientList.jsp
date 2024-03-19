<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Patients:</h2>
  <form action="patientList.html" method="get">
    <select name="sort">
      <%
        List<String> columnNames = (List<String>) request.getAttribute("columnNames"); //sort options
        for (String column : columnNames) {
      %>
      <option value="<%=column%>">Sort by <%=column%></option>
      <% } %>
    </select>
    <select name="order">
      <option value="asc">Ascending</option>
      <option value="desc">Descending</option>
    </select>
    <input type="submit" value="Sort">
  </form>

  <form action="patientList.html" method="GET">
    <input type="submit" value="Reset">
  </form>
  <%
    String sort = request.getParameter("sort");
    String order = request.getParameter("order");
    if (sort != null && order != null) {
      String displayOrder = "asc".equals(order) ? "ASCENDING" : "DESCENDING"; //sort order options
  %>
  <h4>Sorted by <%=displayOrder%> <%=sort%></h4>
  <% } %>
  <ul>
    <%
      List<String> patientThings = (List<String>) request.getAttribute("patientThings");
      List<List<String>> patientIDs = (List<List<String>>) request.getAttribute("patientIDs");

      int noneSize = 0;
      for (int i = 0; i < patientThings.size(); i++) {
        String patientThing = patientThings.get(i);
        List<String> ids = patientIDs.get(i);

        if (Objects.equals(patientThing, "")) { //skip empty patientThing
          noneSize = ids.size();
          continue;
        }

        if (ids.size() == 1) { //display single patient
          String href = "patientDetails.html?id=" + ids.get(0);
    %>
    <li><%=patientThing%><a href="<%=href%>"> (ID: <%=ids.get(0)%>)</a></li><br>
    <%
    } else { //display list of patients
    %>
    <br><li><%=patientThing%>: <%=ids.size()%></li><br>
    <%
      for (String patientID : ids) {
        String href = "patientDetails.html?id=" + patientID;
    %>
    <li style="margin-left: 20px;"><a href="<%=href%>"> (ID: <%=patientID%>)</a></li><br>
    <%
          }
    %>
    <br>
    <%
        }
      }
    %>
    <br>
    <p>None: <%=noneSize%></p>
  </ul>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>