/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modals.User;

/**
 *
 * @author ritzhaki
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class LoginServlet extends BaseServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("error", "");
        if (request.getSession().getServletContext().getAttribute("USER_NAME") == null) {
            request.getSession().getServletContext().setAttribute("USER_NAME", "");
        }
        if (request.getParameter("logout") != null) {
            request.getSession().setAttribute("USER", new User());
        } else {
            request.getServletContext().setAttribute("actions", new ArrayList<String>());
        }
        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String USER_NAME = request.getParameter("USER_NAME");
        String PASSWORD = request.getParameter("PASSWORD");
        request.setAttribute("error", "");
        User u = super.getUser(USER_NAME, PASSWORD);

        if (u != null) {
            request.getSession().getServletContext().setAttribute("USER_NAME", USER_NAME);
            request.getSession().setAttribute("USER", u);
        } else {
            request.setAttribute("error", "user wasn't found!");
        }
        super.doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    String getJSPPage() {
        return "login.jsp";
    }

}
