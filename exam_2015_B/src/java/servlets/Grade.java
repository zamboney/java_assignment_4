package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.IntStream;

/**
 *
 * @author ritzhaki
 */
@WebServlet(name = "Grade", urlPatterns = {"/Grade"})
public class Grade extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        java.util.List<modals.Score> scores = new ArrayList<>();
        request.setAttribute("error", "");        
        request.setAttribute("avg_all", "");
        request.setAttribute("avg", "");

        request.setAttribute("scores", scores);
        String license_number = request.getParameter("license_number");
        String simester_number = request.getParameter("simester_number");
        if (!license_number.equals("") && !simester_number.equals("")) {
           scores = super.getGrades(license_number, simester_number);
            if (scores.isEmpty()) {
                request.setAttribute("error", "no scores found for license_number: "  + license_number + " simester_number: " + simester_number);
            } else {
                request.setAttribute("scores", scores);
                super.getGrades(license_number);
                int points = scores.stream().mapToInt((s)->s.getPOINTS()).sum();
                int sum = scores.stream().mapToInt((s)->s.getGRADE() * s.getPOINTS()).sum();
                
                
                
                
                request.setAttribute("avg", Integer.toString(sum/points));
                scores = super.getGrades(license_number);
                points = 0;
                sum = 0;
                points = scores.stream().mapToInt((s)->s.getPOINTS()).sum();
                sum = scores.stream().mapToInt((s)->s.getGRADE() * s.getPOINTS()).sum();
                
                
                
                request.setAttribute("avg_all", Integer.toString(sum/points));
            }
        } else {
            request.setAttribute("error", "license_number or simester_number not supply!");
        }
        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    String getJSPPage() {
        return "grade.jsp";
    }

    private static class Score {

        public Score() {
        }
    }

}
