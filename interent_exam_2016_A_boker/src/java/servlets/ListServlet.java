/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modals.Contact;
import modals.User;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ritzhaki
 */
@WebServlet(name = "List", urlPatterns = {"/List"})
public class ListServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("USER") == null) {
            request.setAttribute("contetns", new ArrayList<Contact>());
        } else {
            
            request.setAttribute("contetns", super.getContens(((User) request.getSession().getAttribute("USER")).getID(),request.getParameter("search")));
        }

        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> actions = null;
        if (request.getServletContext().getAttribute("actions") == null) {
            actions = new ArrayList<>();
        } else {
            actions = (List<String>) request.getServletContext().getAttribute("actions");
        }
        switch (request.getParameter("action")) {
            case "UPDATE": {

                int ID = Integer.parseInt(request.getParameter("ID"));
                String FNAME = request.getParameter("FNAME"),
                        LNAME = request.getParameter("LNAME"),
                        PHONE = request.getParameter("PHONE");
                try {
                    boolean update = super.update("UPDATE APP.CONTETNS SET \"FNAME\" = '" + FNAME + "', \"LNAME\" = '" + LNAME + "', \"PHONE\" = '" + PHONE + "' WHERE ID = " + ID);
                    if (update) {
                        actions.add(String.format("UPDATE ID:%s, FNAME:%s, LNAME:%s, PHONE:%s", Integer.toString(ID), FNAME, LNAME, PHONE));
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(ListServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            case "DELETE":
                int ID = Integer.parseInt(request.getParameter("ID"));
                 {
                    try {
                        boolean update = super.update("DELETE FROM APP.CONTETNS WHERE ID = " + ID);
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(ListServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                actions.add(String.format("DELETE ID:%s", Integer.toString(ID)));

                break;
            case "CREATE":
                int USER_ID = ((User) request.getSession().getAttribute("USER")).getID();
                String FNAME = request.getParameter("FNAME"),
                 LNAME = request.getParameter("LNAME"),
                 PHONE = request.getParameter("PHONE");
                actions.add(String.format("CREATE USER_ID:%s, FNAME:%s, LNAME:%s, PHONE:%s", Integer.toString(USER_ID), FNAME, LNAME, PHONE));
                 {
                    try {
                        boolean update = super.update("INSERT INTO APP.CONTETNS (FNAME, LNAME, PHONE, USER_ID) VALUES ('" + FNAME + "', '" + LNAME + "', '" + PHONE + "', " + USER_ID + ")");
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(ListServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                break;

        }
        request.getServletContext().setAttribute("actions", actions);
        this.doGet(request, response);
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
        return "list.jsp";
    }

}
