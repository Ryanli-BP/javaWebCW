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

@WebServlet("/addPatient.html")
public class AddPatientServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Model model = ModelFactory.getModel();
        List<String> columnNames = model.getColumnNames();

        request.setAttribute("columnNames", columnNames);
        RequestDispatcher dispatcher = request.getRequestDispatcher("addPatient.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Model model = ModelFactory.getModel();
        List<String> newPatientDetails = new ArrayList<>();
        List<String> columnNames = model.getColumnNames();

        for (String columnName : columnNames) {
            // Get the corresponding parameter value from the request
            String parameterValue = request.getParameter(columnName);
            System.out.println(parameterValue);

            newPatientDetails.add(parameterValue);
        }

        model.addPatient(newPatientDetails);

        response.sendRedirect("success.html");
    }
}
