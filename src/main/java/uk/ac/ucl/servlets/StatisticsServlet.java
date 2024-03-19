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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/statistics.html")
public class StatisticsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Model model = ModelFactory.getModel();
        List<Integer> ages = new ArrayList<> (model.getAllAges());

        request.setAttribute("ages", ages);

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/statistics.jsp");
        dispatch.forward(request, response);
    }
}
