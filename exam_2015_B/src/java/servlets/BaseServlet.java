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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modals.*;

abstract class BaseServlet extends HttpServlet {

    public static final String connectionPath = "jdbc:derby://localhost:1527/exam_2015_B";

    abstract String getJSPPage();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("currentView", this.getJSPPage());
        if (request.getSession().getAttribute("student") == null) {
            request.getSession().setAttribute("student", new Student());
        }
        Student s = (Student) request.getSession().getAttribute("student");

        if (request.getParameter("license_number") != null) {
            String license_number = request.getParameter("license_number");
            if (license_number == null ? s.getId() != null : !license_number.equals(s.getId())) {
                Student sTemp = this.getStudent(license_number);
                if (sTemp != null) {
                    request.getSession().setAttribute("student", sTemp);
                }
            }
        }
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

    Student getStudent(String license_number) {

        try {
            List<Map<String, Object>> table = this.query("SELECT * FROM APP.STUDENTS WHERE ID = '" + license_number + "'");
            if (table.size() > 0) {
                Student s = new Student();
                s.setId(table.get(0).get("ID").toString());
                s.setfName(table.get(0).get("FNAME").toString());
                s.setlName(table.get(0).get("LNAME").toString());
                return s;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    List<Score> getGrades(String license_number, String simester_number) {

        List<Score> scores = new ArrayList<>();
        try {
            List<Map<String, Object>> table = this.query("SELECT * FROM APP.SCORES WHERE STUDENT_ID = '" + license_number + "' AND SIMESTER = " + simester_number);
            table.forEach((item) -> {

                Score s = new Score();
                s.setCOURSE_NAME(item.get("COURSE_NAME").toString());
                s.setCOURSE_NUMBER(item.get("COURSE_NUMBER").toString());
                s.setSTUDENT_ID(item.get("STUDENT_ID").toString());
                s.setGRADE(Integer.parseInt(item.get("GRADE").toString()));
                s.setID(item.get("ID").toString());
                s.setPOINTS(Integer.parseInt(item.get("POINTS").toString()));
                s.setSIMESTER(Integer.parseInt(item.get("SIMESTER").toString()));
                scores.add(s);
            });
        } catch (SQLException ex) {
            Logger.getLogger(BaseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scores;
    }

    List<Score> getGrades(String license_number) {
        List<Score> scores = new ArrayList<>();
        try {
            List<Map<String, Object>> table = this.query("SELECT * FROM APP.SCORES WHERE STUDENT_ID = '" + license_number + "'");
            table.forEach((item) -> {

                Score s = new Score();
                s.setCOURSE_NAME(item.get("COURSE_NAME").toString());
                s.setCOURSE_NUMBER(item.get("COURSE_NUMBER").toString());
                s.setSTUDENT_ID(item.get("STUDENT_ID").toString());
                s.setGRADE(Integer.parseInt(item.get("GRADE").toString()));
                s.setID(item.get("ID").toString());
                s.setPOINTS(Integer.parseInt(item.get("POINTS").toString()));
                s.setSIMESTER(Integer.parseInt(item.get("SIMESTER").toString()));
                scores.add(s);
            });
        } catch (SQLException ex) {
            Logger.getLogger(BaseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scores;
    }

    List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        try {
            List<Map<String,Object>> table = this.query("SELECT * FROM APP.STUDENTS");
            table.forEach((item)->{
               Student s = new Student();
               s.setId(item.get("ID").toString());
               s.setfName(item.get("FNAME").toString());
               s.setlName(item.get("LNAME").toString());
               List<String> simesters = new ArrayList<>();
                try {
                    this.query("SELECT count(APP.SCORES.ID), APP.SCORES.SIMESTER as SIMESTER FROM APP.SCORES WHERE APP.SCORES.STUDENT_ID = '301120655' GROUP BY APP.SCORES.SIMESTER").forEach((simester)->{
                        simesters.add(simester.get("SIMESTER").toString());
                    });
                } catch (SQLException ex) {
                    Logger.getLogger(BaseServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
               s.setSimesters(simesters);
               students.add(s);
            });
        } catch (SQLException ex) {
            Logger.getLogger(BaseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return students;
    }

    List<Student> getStudentsById(String license_number) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
