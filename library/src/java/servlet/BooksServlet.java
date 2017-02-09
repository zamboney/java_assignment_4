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
@WebServlet(name = "BooksServlet", urlPatterns = {"/Books"})
public class BooksServlet extends BaseServlet {

    /*
    
    
    
    
    */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        try {
            request.setAttribute("conditions", super.query("SELECT * FROM APP.CONDITIONS"));
            request.setAttribute("genres", super.query("SELECT * FROM APP.GENRES"));
            if (request.getParameter("genre_id") != null) {
                int genre_id = Integer.parseInt(request.getParameter("genre_id"));
                request.setAttribute("books", super.query(
                        "SELECT COUNT(APP.BOOKS.ID) as COPIES, APP.BOOKS.\"NAME\" as  \"NAME\" ,APP.BOOKS.AUTHOR as  AUTHOR ,CONDITIONS.\"NAME\" as \"CONDITION\",CONDITIONS.\"ID\" as \"CONDITION_ID\"\n"
                        + "FROM  APP.BOOKS, APP.CONDITIONS\n"
                        + "WHERE APP.BOOKS.CONDITION_ID = APP.CONDITIONS.ID \n"
                        + "AND APP.BOOKS.GENRE_ID = " + genre_id + "\n"
                        + "AND BOOKS.ID NOT IN(SELECT BOOK_ID FROM APP.RENTS AS RENTS Where BOOKS.ID = RENTS.BOOK_ID AND RENTS.END_DATE IS NULL)"
                        + "AND CONDITIONS.\"NAME\"  != 'NOT IN USE'\n"
                        + "GROUP BY APP.BOOKS.\"NAME\" , AUTHOR, CONDITIONS.\"NAME\", CONDITIONS.\"ID\""));
            } else if (request.getParameter("search") != null) {
                String search = request.getParameter("search");
                request.setAttribute("books", super.query(
                        "SELECT COUNT(APP.BOOKS.ID) as COPIES, APP.BOOKS.\"NAME\" as  \"NAME\" ,APP.BOOKS.AUTHOR as  AUTHOR, CONDITIONS.\"NAME\" as \"CONDITION\",CONDITIONS.\"ID\" as \"CONDITION_ID\", APP.GENRES.\"NAME\" AS GENRE_NAME\n"
                        + "FROM APP.GENRES, APP.BOOKS, APP.CONDITIONS\n"
                        + "WHERE APP.BOOKS.CONDITION_ID = APP.CONDITIONS.ID\n"
                        + "AND APP.GENRES.ID = APP.BOOKS.GENRE_ID\n"
                        + "AND LOWER(APP.BOOKS.NAME) LIKE '%" + search.toLowerCase() + "%'\n"
                        + "AND BOOKS.ID NOT IN(SELECT BOOK_ID FROM APP.RENTS AS RENTS Where BOOKS.ID = RENTS.BOOK_ID AND RENTS.END_DATE IS NULL)"
                        + "AND CONDITIONS.\"NAME\"  != 'NOT IN USE'\n"
                        + "GROUP BY APP.BOOKS.\"NAME\" , AUTHOR, CONDITIONS.\"NAME\", CONDITIONS.\"ID\",APP.GENRES.\"NAME\""));
            }
            super.doGet(request, response);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BooksServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        switch (request.getParameter("create")) {
            case "rent":
                String book_name = request.getParameter("book_name"),
                 condition_id = request.getParameter("condition_id");
                 {
                    try {
                        String bookId = this.query(String.format("SELECT APP.BOOKS.ID FROM BOOKS WHERE NAME = '%s' AND CONDITION_ID = %s AND BOOKS.ID NOT IN(SELECT BOOK_ID FROM APP.RENTS AS RENTS Where BOOKS.ID = RENTS.BOOK_ID AND RENTS.END_DATE IS NULL)", book_name, condition_id)).get(0).get("ID").toString();
                        String UserId = super.getUserId(request);
                        super.update("INSERT INTO APP.RENTS (BOOK_ID, USER_ID,START_DATE) VALUES (" + bookId + ", " + UserId + ",CURRENT_DATE)");
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(BooksServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case "genre":
                break;
            case "book":
                String NAME = request.getParameter("NAME"),
                 AUTHOR = request.getParameter("AUTHOR"),
                 CONDITION_ID = request.getParameter("CONDITION_ID"),
                 GENRE_ID = request.getParameter("GENRE_ID");
                int QUANTITY = Integer.parseInt(request.getParameter("QUANTITY"));
                try {
                    for (int i = 0; i < QUANTITY; i++) {
                        this.update(String.format("INSERT INTO APP.BOOKS (NAME,AUTHOR,CONDITION_ID,GENRE_ID) VALUES ('%s','%s',%s,%s)",
                                NAME,
                                AUTHOR,
                                CONDITION_ID,
                                GENRE_ID));
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(BooksServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
        }
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
        return "books.jsp";
    }

}
