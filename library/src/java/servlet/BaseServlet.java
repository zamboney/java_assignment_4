package servlet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

public abstract class BaseServlet extends HttpServlet {

    public static final String connectionPath = "jdbc:derby://localhost:1527/libraryDB";

    abstract String getJSPPage();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("jsp", this.getJSPPage());
        if (request.getSession().getServletContext().getAttribute("permissions") == null) {
            request.getSession().getServletContext().setAttribute("permissions", this.getPermissions());
        }
        request.getServletContext().getRequestDispatcher("/wrapper.jsp").forward(request, response);
    }

    public User getUserByUserNameAndPassword(String username, String password) {
        List<Map<String, Object>> users = new ArrayList<>();
        try {
            users = this.query(this.getDBScripts("getUsersByUserNameAndPassword", username, password));
        } catch (IOException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BaseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (users.isEmpty()) {
            return null;
        }
        return SQLUsersToUsers(users).get(0);

    }

    public User getUserById(int userId) {
        List<Map<String, Object>> users = new ArrayList<>();
        try {
            users = this.query(this.getDBScripts("getUserById", userId));
        } catch (IOException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BaseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (users.isEmpty()) {
            return null;
        }
        return SQLUsersToUsers(users).get(0);

    }

    private List<User> SQLUsersToUsers(List<Map<String, Object>> usersSql) {
        List<User> users = new ArrayList<>();
        usersSql.forEach((item) -> {
            User u = new User();
            u.setId(Integer.parseInt(item.get("ID").toString()));
            u.setFname(item.get("FNAME").toString());
            u.setLname(item.get("LNAME").toString());
            u.setPenalty(Double.parseDouble(item.get("PENALTY").toString()));
            u.setPermission(item.get("PERMISSION_NAME").toString());
            u.setPermission_id(Integer.parseInt(item.get("PERMISSION_ID").toString()));
            u.setUserName(item.get("USER_NAME").toString());
            users.add(u);
        });

        return users;
    }

    public List<User> getUsers() {
        List<Map<String, Object>> users = new ArrayList<>();
        try {
            users = this.query(this.getDBScripts("getUsers"));
        } catch (IOException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BaseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (users.isEmpty()) {
            return null;
        }
        return this.SQLUsersToUsers(users);

    }

    List<Rent> getRentsByUserId(int userId) {
        List<Map<String, Object>> sqlRents = new ArrayList<>();
        try {
            sqlRents = this.query(this.getDBScripts("RentsByUserId", userId));
        } catch (IOException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BaseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Rent> rents = new ArrayList<>();
        sqlRents.forEach((item) -> {
            Rent r = new Rent();
            r.setBookId(Integer.parseInt(item.get("BOOK_ID").toString()));
            r.setId(Integer.parseInt(item.get("RENT_ID").toString()));
            r.setUserId(userId);
            r.setBookName(item.get("BOOK_NAME").toString());
            r.setConditionId(Integer.parseInt(item.get("CONDITION_ID").toString()));
            r.setDayToReturn(Integer.parseInt(item.get("DATE_TO_RETURN").toString()));
            ((java.util.Date) item.get("START_DATE")).getTime();
            r.setStartDate((java.util.Date) item.get("START_DATE"));
            long penalty = 0;
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(new java.util.Date());
            cal.add(Calendar.DATE, -r.getDayToReturn());
            if (cal.getTime().getTime() > r.getStartDate().getTime()) {
                penalty = (new java.util.Date().getTime() - r.getStartDate().getTime()) / (1000 * 3600 * 24);
            }
            r.setPenalty(penalty);
            rents.add(r);
        });

        return rents;
    }

    public List<Permission> getPermissions() {
        List<Map<String, Object>> permissions = null;
        try {
            permissions = this.query("select * from app.permissions");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BaseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Permission> parsePermissions = new ArrayList<>();
        permissions.forEach((item) -> {
            Permission p = new Permission();
            p.setId(Integer.parseInt(item.get("ID").toString()));
            p.setName(item.get("NAME").toString());
            parsePermissions.add(p);
        });
        return parsePermissions;
    }

    public List<Genre> getGenres() {
        List<Map<String, Object>> genres = null;
        try {
            genres = this.query("select * from app.genres");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BaseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Genre> parseGenres = new ArrayList<>();
        genres.forEach((item) -> {
            Genre g = new Genre();
            g.setId(Integer.parseInt(item.get("ID").toString()));
            g.setName(item.get("NAME").toString());
            parseGenres.add(g);
        });
        return parseGenres;
    }

    public List<Condition> getConditions() {
        List<Map<String, Object>> permissions = null;
        try {
            permissions = this.query("select * from app.conditions");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BaseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Condition> parsePermissions = new ArrayList<>();
        permissions.forEach((item) -> {
            Condition p = new Condition();
            p.setId(Integer.parseInt(item.get("ID").toString()));
            p.setName(item.get("NAME").toString());
            parsePermissions.add(p);
        });
        return parsePermissions;
    }

    private List<Book> SQLBooksToBooks(List<Map<String, Object>> books) {
        List<Book> parseBooks = new ArrayList<>();
        books.forEach((item) -> {
            Book b = new Book();
            b.setAuthor(item.get("AUTHOR").toString());
            b.setCopies(Integer.parseInt(item.get("COPIES").toString()));
            b.setCondition(item.get("CONDITION").toString());
            b.setCondition_id(Integer.parseInt(item.get("CONDITION_ID").toString()));
            b.setGenreName(item.get("GENRE_NAME").toString());
            b.setName(item.get("NAME").toString());
            parseBooks.add(b);

        });
        return parseBooks;
    }

    public List<Book> getBooksBySearchWord(String search) {
        List<Map<String, Object>> books = null;
        try {
            books = this.query(this.getDBScripts("getBooksBySearchWord", search));
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            Logger.getLogger(BaseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.SQLBooksToBooks(books);
    }

    public List<Book> getBooksByGenreId(int genre_id) {
        List<Map<String, Object>> books = null;
        try {
            books = this.query(this.getDBScripts("getBooksByGenreId", genre_id));
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            Logger.getLogger(BaseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.SQLBooksToBooks(books);
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

    int getUserId(HttpServletRequest request) {
        return ((User) request.getSession().getAttribute("user")).getId();
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

}
