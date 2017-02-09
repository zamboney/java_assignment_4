/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ritzhaki
 */
public abstract class BaseServlet extends HttpServlet {
    
    public static final String connectionPath = "jdbc:derby://localhost:1527/libraryDB";
    
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
        if (request.getServletContext().getAttribute("premissions") == null) {
            try {
                request.getServletContext().setAttribute("premissions", this.query("SELECT APP.PERMISSIONS.ID, APP.PERMISSIONS.\"NAME\" FROM APP.PERMISSIONS"));
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(BaseServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        request.getServletContext().getRequestDispatcher("/wrapper.jsp").forward(request, response);
    }
    
    List<Map<String, Object>> query(String query) throws ClassNotFoundException, SQLException, SQLException {
        List<Map<String, Object>> table = new ArrayList<>();
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        Connection cn = null;
        try {
            cn = DriverManager.getConnection(connectionPath);
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
                            row.put(rsmd.getColumnName(i), new java.util.Date(rs.getDate(i).getTime()));
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
    
    boolean update(String query) throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        Connection cn = null;
        try {
            cn = DriverManager.getConnection(connectionPath);
            Statement st = cn.createStatement();
            return st.executeUpdate(query) > -1;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        } finally {
            if (cn != null) {
                cn.close();
            }
        }
    }
    
    String getUserId(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("userID");
    }
    private List<Map<String, Object>> Conditions = null;
    
    List<Map<String, Object>> GetConditions(HttpServletRequest request) {
        List<Map<String, Object>> conditions = (List<Map<String, Object>>) request.getSession().getAttribute("conditions");
        if (conditions == null) {
            try {
                conditions = this.query("SELECT * FROM APP.CONDITIONS");
                request.getSession().setAttribute("conditions", conditions);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(BaseServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return conditions;
        
    }
    
    public String getDBScripts(String SQLScriptFile, Object... params) throws IOException {
        String str = "";
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(this.getClass().getClassLoader().getResource("/SQL/" + SQLScriptFile + ".sql").getPath()));
            while ((str = in.readLine()) != null) {
                sb.append("\n ").append(str);
            }
            in.close();
        } catch (IOException e) {
            System.err.println("Failed to Execute" + SQLScriptFile + ". The error is" + e.getMessage());
        }
        return String.format(sb.toString(), params);
    }
    
    void updateUser(HttpServletRequest request) {
        try {
            request.getSession().setAttribute("user", this.query(String.format(""
                    + "SELECT USER_NAME as \"User Name\" ,FNAME || ' ' || LNAME as \"Full Name\" , APP.USERS.ID,"
                    + "APP.USERS.PENALTY as Penalty , "
                    + "APP.USERS.PREMISSION_ID as PREMISSION_ID,"
                    + "APP.PERMISSIONS.\"NAME\" as Permission ,"
                    + "APP.USERS.PREMISSION_ID as PREMISSION_ID "
                    + "FROM APP.USERS, APP.PERMISSIONS "
                    + "where APP.USERS.IS_DELETED = FALSE and APP.USERS.PREMISSION_ID = APP.PERMISSIONS.ID And APP.USERS.ID = %s", this.getUserId(request))).get(0));
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BaseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
