package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/editPatient.html")
public class EditPatientServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Retrieve the patient ID from the request
        String patientID = request.getParameter("patientID");

        // Use the model to find the patient by ID
        Model model = ModelFactory.getModel();
        List<String> patientDetails = model.getPatientDetails(patientID);

        // Set the patient details as a request attribute and forward the request to editPatient.jsp
        request.setAttribute("patientDetails", patientDetails);
        RequestDispatcher dispatcher = request.getRequestDispatcher("editPatient.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Retrieve the patient ID from the request
        String patientID = request.getParameter("patientID");

        // Use the model to find the patient by ID
        Model model = ModelFactory.getModel();
        List<String> newPatientDetails = new ArrayList<>();

        // Check if the form in editPatient.jsp has been submitted, BIRTHDAY is chosen arbitrarily
        if (request.getParameter("BIRTHDATE") != null) {

            List<String> columnNames = model.getColumnNames();
            for (String columnName : columnNames) {
                // Get the corresponding parameter value from the request
                String parameterValue = request.getParameter(columnName);

                newPatientDetails.add(parameterValue);
            }

            model.updatePatient(newPatientDetails, patientID);

            response.sendRedirect("success.html");
        }
    }
}