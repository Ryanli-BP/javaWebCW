package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet("/downloadCSV")
public class downloadCSVServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();
        String appDirectory = System.getProperty("user.dir");
        String tempFilePath = appDirectory + "/temp.csv";
        model.saveDataAsCSV(tempFilePath);

        String filename = request.getParameter("filename");
        if (filename == null || filename.trim().isEmpty()) {
            filename = "data.csv";  // Default filename
        } else {
            filename += ".csv";  // Append .csv extension
        }

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        Files.copy(Paths.get(tempFilePath), response.getOutputStream());
    }
}