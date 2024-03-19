package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

// The servlet invoked to display a list of patients. Note that this data is just example data,
// you replace it with your data.
// The url http://localhost:8080/patientList.html is mapped to calling doGet on the servlet object.
// The servlet object is created automatically, you just provide the class.
@WebServlet("/patientList.html")
public class ViewPatientListServlet extends HttpServlet
{
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    // Get the data from the model
    Model model = ModelFactory.getModel();

    String sort = request.getParameter("sort");     //needed for the sort operation
    String order = request.getParameter("order");
    List<String> columnNames = Arrays.asList("AGE", "BIRTHDATE", "DEATHDATE", "SSN", "DRIVERS", "PASSPORT", "FIRST", "LAST", "MAIDEN", "RACE", "MARITAL", "ETHNICITY", "BIRTHPLACE", "GENDER", "ADDRESS", "CITY", "STATE", "ZIP");

    List<String> patientThings;
    List<List<String>> patientIDs;

    if (Objects.equals(sort, "AGE")) {
      if (order == null) {
        order = "asc";
      }
      Map<String, List<String>> sortedPatients = model.getPatientsSortedByAges(order);
      patientThings = new ArrayList<>(sortedPatients.keySet()); //to convert the values of the map to a list
      patientIDs = new ArrayList<>(sortedPatients.values());
    }
    else if (sort != null) {
      Map<String, List<String>> sortedPatients = model.getPatientsSortedByColumn(sort, order);
      patientThings = new ArrayList<>(sortedPatients.keySet()); //to convert the values of the map to a list
      patientIDs = new ArrayList<>(sortedPatients.values());
    }
    else { //default list display
      patientThings = model.getPatientNames();
      patientIDs = model.getPatientIDs().stream().map(Arrays::asList).collect(Collectors.toList());
    }

    request.setAttribute("patientThings", patientThings);
    request.setAttribute("patientIDs", patientIDs);
    request.setAttribute("columnNames", columnNames);

    // Invoke the JSP.
    ServletContext context = getServletContext();
    RequestDispatcher dispatch = context.getRequestDispatcher("/patientList.jsp");
    dispatch.forward(request, response);
  }
}
