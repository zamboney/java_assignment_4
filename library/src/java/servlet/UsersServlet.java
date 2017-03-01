/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
        request.setAttribute("users", super.getUsers());
        super.doGet(request, response);
    }

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
