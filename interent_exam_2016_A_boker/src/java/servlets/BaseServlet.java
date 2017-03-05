package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modals.Contact;
import modals.User;

abstract class BaseServlet extends HttpServlet {

    public static final String connectionPath = "jdbc:derby://localhost:1527/exam_2016_a_boker";

    abstract String getJSPPage();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getServletContext().getAttribute("actions") == null) {
            request.getServletContext().setAttribute("actions", new ArrayList<String>());
        }
        if (request.getSession().getAttribute("USER") == null) {
            request.getSession().setAttribute("USER", new User());
        }
        request.setAttribute("currentView", this.getJSPPage());
        request.getServletContext().getRequestDispatcher("/wrapper.jsp").forward(request, response);
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

    List<Map<String, Object>> query(String query) throws SQLException {
        List<Map<String, Object>> table = new ArrayList<>();
        try {

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

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BaseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    User getUser(String USER_NAME, String PASSWORD) {
        User u = null;
        try {
            List<Map<String, Object>> query = this.query("select * from users where USER_NAME = '" + USER_NAME + "' AND PASSWORD = '" + PASSWORD + "'");
            if (!query.isEmpty()) {
                u = new User();
                Map<String, Object> item = query.get(0);
                u.setFNAME(item.get("FNAME").toString());
                u.setLNAME(item.get("LNAME").toString());
                u.setUSER_NAME(USER_NAME);
                u.setID(Integer.parseInt(item.get("ID").toString()));
            }

        } catch (SQLException ex) {
            Logger.getLogger(BaseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;

    }

    List<Contact> getContens(int USER_ID, String search) {
        List<Contact> contants = new ArrayList<>();
        if(search == null){
            search = "";
        }
        try {
            List<Map<String, Object>> query = this.query("select * from CONTETNS where USER_ID = " + USER_ID + " AND (FNAME like '%" + search + "%' or LNAME like '%" + search + "%' or PHONE like '%" + search + "%')");
            query.forEach((item) -> {
                Contact c = new Contact();
                c.setFNAME(item.get("FNAME").toString());
                c.setLNAME(item.get("LNAME").toString());
                c.setPHONE(item.get("PHONE").toString());
                c.setUSER_ID(USER_ID);
                c.setID(Integer.parseInt(item.get("ID").toString()));
                contants.add(c);

            });
        } catch (SQLException ex) {
            Logger.getLogger(BaseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contants;
    }

}
