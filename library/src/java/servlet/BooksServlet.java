/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modals.Book;

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

        request.setAttribute("conditions", super.getConditions());
        request.setAttribute("genres", super.getGenres());
        if (request.getParameter("genre_id") != null) {
            int genre_id = Integer.parseInt(request.getParameter("genre_id"));
            request.setAttribute("books", super.getBooksByGenreId(genre_id));
        } else if (request.getParameter("search") != null) {
            String search = request.getParameter("search");
            super.getBooksBySearchWord(search);
            request.setAttribute("books", super.getBooksBySearchWord(search));
        } else {
            request.setAttribute("books", new ArrayList<Object>());
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
        switch (request.getParameter("create")) {
            case "rent":
                String book_name = request.getParameter("book_name"),
                 condition_id = request.getParameter("condition_id");
                 {
                    try {
                        String bookId = this.query(String.format("SELECT APP.BOOKS.ID FROM BOOKS WHERE NAME = '%s' AND CONDITION_ID = %s AND BOOKS.ID NOT IN(SELECT BOOK_ID FROM APP.RENTS AS RENTS Where BOOKS.ID = RENTS.BOOK_ID AND RENTS.END_DATE IS NULL)", book_name, condition_id)).get(0).get("ID").toString();
                        int UserId = super.getUserId(request);
                        super.update("INSERT INTO APP.RENTS (BOOK_ID, USER_ID,START_DATE) VALUES (" + bookId + ", " + UserId + ",CURRENT_DATE)");
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(BooksServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case "genre": {
                try {
                    super.update("INSERT INTO APP.GENRES (NAME) VALUES ('" + request.getParameter("name") + "')");
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(BooksServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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
