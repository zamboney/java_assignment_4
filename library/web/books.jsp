<%-- 
    Document   : books
    Created on : Feb 6, 2017, 3:14:53 PM
    Author     : ritzhaki
--%>

<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Map<String, Object>> genres = (List<Map<String, Object>>) request.getAttribute("genres");
    List<Map<String, Object>> conditions = (List<Map<String, Object>>) request.getAttribute("conditions");
%>
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
            <form action="/Users" method="POST">
                <div class="modal-body">
                    <input type="hidden" name="Create-Genre"/>
                    <div class="form-group">
                        <label for="Genre">Genre</label>
                        <input required type="text" class="form-control" name="Genre" id="Genre"/>
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
                            <%for (Map<String, Object> condition
                                        : conditions) {%>
                            <option value="<%=condition.get("ID")%>"><%=condition.get("NAME")%></option>
                            <%}%>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="GENRE_ID">Genre</label>
                        <select required name="GENRE_ID" class="form-control" id="GENRE_ID">
                            <%for (Map<String, Object> genre
                                        : genres) {%>
                            <option value="<%=genre.get("ID")%>"><%=genre.get("NAME")%></option>
                            <%}%>
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
<%  List<Map<String, Object>> books;
    if ((books = (List<Map<String, Object>>) request.getAttribute("books")) != null) {%>

<a href="/Books" id="back-step" class="btn btn-primary btn"><span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span></a>
<br>
<h2><%
    if (request.getParameter("search") == null) {
        for (Map<String, Object> genre : genres) {
            if (genre.get("ID").equals(request.getParameter("genre_id"))) {%><%=genre.get("NAME")%><%}%>

    <%}%>\Books<%} else {%>SEARCH: <%=request.getParameter("search")%><%}%></h2>

<div class="row">
    <%for (Map<String, Object> book
                : books) {%>
    <div class="col-sm-3 text-center">
        <div class="card" style="border:1px solid rgba(0,0,0,.125);padding: 20px;">
            <div class="card-block">
                <h3 class="card-title"><%=book.get("NAME")%></h3>
                <div class="text-left" >

                    <h5>AUTHOR: <%=book.get("AUTHOR")%> </h5>   
                    <h5>CONDITION: <%=book.get("CONDITION")%> </h5>
                    <% if (book.get("GENRE_NAME") != null) {%>
                    <h5>GENRE: <%=book.get("GENRE_NAME")%> </h5>        
                    <% }%>
                    <h5>COPIES: <%=book.get("COPIES")%> </h5>  
                </div>
                <%if (premissionLVL > 1) {%>
                <form action="/Books" method="post">
                    <input type="hidden" name="create" value="rent"/>
                    <input type="hidden" name="book_name" value="<%=book.get("NAME")%>"/>
                    <input type="hidden" name="condition_id" value="<%=book.get("CONDITION_ID")%>"/>
                    <button type="submit" class="btn btn-primary">Rent</button>
                </form>
                <%}%>
            </div>
        </div>
    </div>
    <%}%>
</div>
<%} else {%>
<h2>Genres</h2>
<div class="row">
    <%for (Map<String, Object> genre
                : genres) {%>


    <div class="col-sm-6 text-center">
        <div class="card" style="border:1px solid rgba(0,0,0,.125);padding: 20px;">
            <div class="card-block">
                <h3 class="card-title"><%=genre.get("NAME")%></h3>
                <a href="/Books?genre_id=<%=genre.get("ID")%>" class="btn btn-primary">Peak</a>
            </div>
        </div>
    </div>

    <%}%>
</div>
<%}%>



