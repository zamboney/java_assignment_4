/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ritzhaki
 */
@WebServlet(name = "UsersServlet", urlPatterns = {"/Users"})
public class UsersServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("delete-id") != null) {
            try {
                super.update("UPDATE APP.USERS SET IS_DELETED = TRUE WHERE ID = " + request.getParameter("delete-id"));
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(UsersServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            request.setAttribute("users", super.query("SELECT APP.USERS.ID as ID,\n"
                    + "       FNAME,\n"
                    + "       LNAME,\n"
                    + "       USER_NAME,\n"
                    + "       PENALTY,\n"
                    + "       APP.PERMISSIONS.\"NAME\" as PERMISSION\n"
                    + "FROM APP.USERS,APP.PERMISSIONS where APP.USERS.PREMISSION_ID = APP.PERMISSIONS.ID AND APP.USERS.IS_DELETED = FALSE"));
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UsersServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        super.doGet(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("Create") != null) {
            String UserName = request.getParameter("UserName"),
                    Password = request.getParameter("Password"),
                    FirstName = request.getParameter("FirstName"),
                    LastName = request.getParameter("LastName"),
                    Permission = request.getParameter("Permission");
            try {
                this.update(String.format("INSERT INTO APP.USERS (USER_NAME,PASSWORD,FNAME,LNAME,PREMISSION_ID,PENALTY) VALUES ('%s','%s','%s','%s',%s,DEFAULT)", UserName, Password, FirstName, LastName, Permission));
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(UsersServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        response.sendRedirect("/Users");
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
        return "users.jsp";
    }

}
