/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modals.User;
/**
 *
 * @author ritzhaki
 */
@WebServlet(name = "UserServlet", urlPatterns = {"/User"})
public class UserServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("logout") != null) {
            request.getSession().setAttribute("user", null);
            request.getSession().setAttribute("login", null);
            request.getSession().setAttribute("premission", 0);
        }
        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("lg_username"),
                password = request.getParameter("lg_password");
        User user = super.getUserByUserNameAndPassword(username,password);
        if (user != null) {
            request.setAttribute("error", null);
            request.getSession().setAttribute("login", true);
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("premission", user.getPermission_id());
        } else {
            request.setAttribute("error", true);
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
        return "user.jsp";
    }

}
