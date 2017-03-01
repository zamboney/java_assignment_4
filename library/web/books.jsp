<%-- 
    Document   : books
    Created on : Feb 6, 2017, 3:14:53 PM
    Author     : ritzhaki
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="conditions" scope="request" class="List<modals.Condition>"/>
<jsp:useBean id="books" scope="request" class="List<modals.Book>"/>
<jsp:useBean id="genres" scope="request" class="List<modals.Genre>"/>
<%
    Object str = request.getSession().getAttribute("premission");
    int premissionLVL = 0;
    if (str != null) {
        premissionLVL = Integer.parseInt(request.getSession().getAttribute("premission").toString());
    }
%>
<h2>Books</h2>
<%if (premissionLVL == 3) {%>
<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
    Add Book
</button>
<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#Genre">
    Add Genre
</button>
<!-- Modal -->
<div class="modal fade" id="Genre" tabindex="-1" role="dialog" aria-labelledby="Genre">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Add Genre</h4>
            </div>
            <form action="/Books" method="POST">
                <div class="modal-body">
                    <input type="hidden" name="create" value="genre"/>
                    <div class="form-group">
                        <label for="Genre">Genre</label>
                        <input required type="text" class="form-control" name="name" id="Genre"/>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Create</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Add Book</h4>
            </div>
            <form action="/Books" method="POST">
                <div class="modal-body">
                    <input type="hidden" name="create" value="book"/>
                    <div class="form-group">
                        <label for="NAME">Name</label>
                        <input required type="text" class="form-control" name="NAME" id="NAME"/>
                    </div>
                    <div class="form-group">
                        <label for="AUTHOR">Author</label>
                        <input required type="text" class="form-control" name="AUTHOR" id="AUTHOR"/>
                    </div>
                    <div class="form-group">
                        <label for="QUANTITY">Quantity</label>
                        <input min=1 required type="number" class="form-control" value="1" name="QUANTITY" id="QUANTITY"/>
                    </div>
                    <div class="form-group">
                        <label for="CONDITION_ID">Condition</label>
                        <select required name="CONDITION_ID" class="form-control" id="CONDITION_ID">
                            <c:forEach items="${conditions}" var="condition">
                                <option value="${condition.getId()}">${condition.getName()}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="GENRE_ID">Genre</label>
                        <select required name="GENRE_ID" class="form-control" id="GENRE_ID">
                            <c:forEach items="${genres}" var="genre">
                                <option value="${genre.getId()}">${genre.getName()}</option>
                            </c:forEach>
                        </select>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Create</button>
                </div>
            </form>
        </div>
    </div>

</div>
<%}%>
<div class="row">
    <div class="col-md-6">
        <h3>Search For Book</h3>
        <div id="custom-search-input">
            <form action="/Books">
                <div class="input-group col-md-12">
                    <input type="text" name="search" class="form-control input" />
                    <span class="input-group-btn">
                        <button type="submit" class="btn btn-info btn" type="button">
                            <i class="glyphicon glyphicon-search"></i>
                        </button>
                    </span>
                </div>
            </form>
        </div>
    </div>
</div>
<hr/>


<c:if test="${books.size() > 0}">

    <a href="/Books" id="back-step" class="btn btn-primary btn"><span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span></a>
    <br>
    <h2><%if (request.getParameter("search") == null) {%>Books<%} else {%>SEARCH: <%=request.getParameter("search")%><%}%></h2>

    <div class="row">
        <c:forEach items="${books}" var="book">
            <div class="col-sm-3 text-center">
                <div class="card" style="border:1px solid rgba(0,0,0,.125);padding: 20px;">
                    <div class="card-block">
                        <h3 class="card-title">${book.getName()}</h3>
                        <div class="text-left" >

                            <h5>AUTHOR: ${book.getAuthor()} </h5>   
                            <h5>CONDITION:  ${book.getCondition()}</h5>
                            <h5>GENRE: ${book.getGenreName()} </h5>        
                            <h5>COPIES: ${book.getCopies()} </h5>  
                        </div>
                        <%if (premissionLVL > 1) {%>
                        <form action="/Books" method="post">
                            <input type="hidden" name="create" value="rent"/>
                            <input type="hidden" name="book_name" value="${book.getName()}"/>
                            <input type="hidden" name="condition_id" value="${book.getCondition_id()}"/>
                            <button type="submit" class="btn btn-primary">Rent</button>
                        </form>
                        <%}%>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</c:if>
<c:if test="${books.size() == 0}">
    <h2>Genres</h2>
    <% if(request.getParameter("genre_id") != null){%>
    <h3 style="color:red">There is no books on this genre or the books in this genre is NOT IN USE conation </h3>
    <%}%>
    <div class="row">
        <c:forEach items="${genres}" var="genre">
            <div class="col-sm-6 text-center">
                <div class="card" style="border:1px solid rgba(0,0,0,.125);padding: 20px;">
                    <div class="card-block">

                        <h3 class="card-title"><jsp:getProperty name="genre" property="name"/></h3>
                        <a href="/Books?genre_id=${genre.getId()}" class="btn btn-primary">Peak</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</c:if>



