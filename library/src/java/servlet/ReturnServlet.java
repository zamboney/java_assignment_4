/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modals.Rent;
import modals.User;

/**
 *
 * @author ritzhaki
 */
@WebServlet(name = "ReturnServlet", urlPatterns = {"/Return"})
public class ReturnServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Rent> rents = super.getRentsByUserId(super.getUserId(request));
        request.setAttribute("rents", rents);
        request.setAttribute("conditions", super.getConditions());
        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        switch (request.getParameter("type")) {
            case "return": {
                try {
                    String rentId = request.getParameter("id");
                    User user = (User)request.getSession().getAttribute("user");
                    
                    super.update("UPDATE APP.USERS SET PENALTY=" + (Double.parseDouble(request.getParameter("PENALTY")) + user.getPenalty() ) + " WHERE APP.USERS.ID=" + super.getUserId(request));
                    boolean update = super.update("UPDATE APP.RENTS SET END_DATE=CURRENT_DATE WHERE APP.RENTS.ID=" + rentId);
                    super.update("UPDATE APP.BOOKS SET CONDITION_ID=" + request.getParameter("condition_id") + " WHERE APP.BOOKS.ID=" + request.getParameter("bookId"));
                    request.getSession().setAttribute("user",super.getUserById(user.getId()));

                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(ReturnServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
        }

        response.sendRedirect("/Return");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    String getJSPPage() {
        return "return.jsp";
    }

}
