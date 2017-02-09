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

/**
 *
 * @author ritzhaki
 */
@WebServlet(name = "ReturnServlet", urlPatterns = {"/Return"})
public class ReturnServlet extends BaseServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        try {
            List<Map<String, Object>> rents = super.query(super.getDBScripts("RentsByUserId", super.getUserId(request)));
            for (Map<String, Object> rent : rents) {
                long penalty = 0;

                int dateToReurn = Integer.parseInt(rent.get("DATE_TO_RETURN").toString());
                Date rentDate = (Date) rent.get("START_DATE");
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(new Date());
                cal.add(Calendar.DATE, -dateToReurn);
                if (cal.getTime().getTime() > rentDate.getTime()) {
                    penalty = (new Date().getTime() - rentDate.getTime()) / (1000 * 3600 * 24);
                }
                rent.put("PENALTY", penalty);
            }
            request.setAttribute("rents", rents);

            super.GetConditions(request);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ReturnServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        switch (request.getParameter("type")) {
            case "return": {
                try {
                    String rentId = request.getParameter("id");
                    Map<String, Object> user = (Map<String, Object>)request.getSession().getAttribute("user");
                    
                    super.update("UPDATE APP.USERS SET PENALTY=" + (Integer.parseInt(request.getParameter("PENALTY")) + Math.round(Float.parseFloat(user.get("PENALTY").toString())) ) + " WHERE APP.USERS.ID=" + super.getUserId(request));
                    boolean update = super.update("UPDATE APP.RENTS SET END_DATE=CURRENT_DATE WHERE APP.RENTS.ID=" + rentId);
                    super.updateUser(request);
                    super.update("UPDATE APP.BOOKS SET CONDITION_ID=" + request.getParameter("condition_id") + " WHERE APP.BOOKS.ID=" + request.getParameter("bookId"));

                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(ReturnServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
        }

        response.sendRedirect("/Return");
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
        return "return.jsp";
    }

}
