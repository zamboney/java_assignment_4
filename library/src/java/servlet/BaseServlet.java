/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ritzhaki
 */
public abstract class BaseServlet extends HttpServlet {

    abstract String getJSPPage();

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
        request.setAttribute("jsp", this.getJSPPage());
        request.getServletContext().getRequestDispatcher("/wrapper.jsp").forward(request, response);
    }

    List<Map<String, Object>> query(String query) throws ClassNotFoundException, SQLException, SQLException {
        List<Map<String, Object>> table = new ArrayList<>();
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        Connection cn = null;
        try {
            cn = DriverManager.getConnection("jdbc:derby://localhost:1527/libraryDB");
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    switch (rsmd.getColumnType(i)) {
                        default:
                            row.put(rsmd.getColumnName(i), rs.getString(i));
                            break;
                        case java.sql.Types.DATE:
                            row.put(rsmd.getColumnName(i), rs.getDate(i));
                    }
                }
                table.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            if (cn != null) {
                cn.close();
            }
        }
        return table;

    }
}
