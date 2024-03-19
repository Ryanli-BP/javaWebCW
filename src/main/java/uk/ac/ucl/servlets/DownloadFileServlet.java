package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/downloadFile")
public class DownloadFileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();
        String filename = request.getParameter("filename");
        String filetype = request.getParameter("filetype");

        try {
            model.saveDataToFile(filename, filetype);
            response.sendRedirect("success.html");
        } catch (IllegalArgumentException e) {
            throw new ServletException(e.getMessage());
        }
    }
}