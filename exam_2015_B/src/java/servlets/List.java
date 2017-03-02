/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modals.Student;

/**
 *
 * @author ritzhaki
 */
@WebServlet(name = "List", urlPatterns = {"/List"})
public class List extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String license_number = request.getParameter("license_number");
        java.util.List<Student> students = super.getStudents();
        request.setAttribute("students", students);
        java.util.List<Student>  students1 = students.stream().filter((Student s)-> s.getId().equals(license_number)).collect(Collectors.toList());
        if(!students1.isEmpty())
            request.setAttribute("students", students1);

        
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
        return "list.jsp";
    }

}
