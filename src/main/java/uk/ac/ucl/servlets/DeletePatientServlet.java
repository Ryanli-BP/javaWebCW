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
import java.util.List;

@WebServlet("/deletePatient.html")
public class DeletePatientServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String patientID = request.getParameter("patientID");
        Model model = ModelFactory.getModel();
        List<String> patientDetails = model.getPatientDetails(patientID);
        request.setAttribute("patientDetails", patientDetails);

        RequestDispatcher dispatcher = request.getRequestDispatcher("deletePatient.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String patientID = request.getParameter("patientID");
        Model model = ModelFactory.getModel();
        model.deletePatient(patientID);
        response.sendRedirect("success.html");
    }
}