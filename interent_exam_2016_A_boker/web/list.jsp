<%-- 
    Document   : list
    Created on : Mar 5, 2017, 1:08:59 PM
    Author     : ritzhaki
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List"%>
<%@page import="modals.Contact"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="contetns" scope="request" type="List<Contact>"/>
<div style="clear:both">
    <form class="form-inline" action="List" method="GET">
        <div class="form-group">
            <input type="text" name="search" class="form-control" id="exampleInputEmail2" placeholder="Search">
        </div>
        <button type="submit" class="btn btn-default">Search</button>
    </form>
    <hr/>
    <!-- Button trigger modal -->
    <button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#modal_create">
        Create
    </button>
    <hr/>

    <!-- Modal -->
    <form class="modal fade" action="List" method="POST" id="modal_create" tabindex="-1" role="dialog" aria-labelledby="modal_create">
        <input type="hidden" name="action" value="CREATE"/>
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Create New Contact</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="exampleInputEmail1">First Name</label>
                        <input type="text" name="FNAME" class="form-control" required="">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputEmail1">Last Name</label>
                        <input type="text"  name="LNAME" class="form-control" required="">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputEmail1">Phone Number</label>
                        <input type="text" name="PHONE" class="form-control"  required="">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Save changes</button>
                </div>
            </div>
        </div>
    </form>
    <table class="table"> 
        <thead> 
            <tr> 
                <th>#</th> 
                <th>First Name</th> 
                <th>Last Name</th> 
                <th>Phone Number</th> 
                <th>Edit</th> 
            </tr> 
        </thead> 
        <tbody> 
            <c:forEach items="${contetns}" var="content">
                <tr>
                    <td>
                        ${content.getID()}
                    </td>
                    <td>
                        ${content.getFNAME()}
                    </td>
                    <td>
                        ${content.getLNAME()}
                    </td>
                    <td>
                        ${content.getPHONE()}
                    </td>
                    <td>
                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#modal_${content.getID()}">
                            edit
                        </button>

                        <!-- Modal -->
                        <form class="modal fade" action="List" method="POST" id="modal_${content.getID()}" tabindex="-1" role="dialog" aria-labelledby="modal_${content.getID()}">
                            <input type="hidden" name="ID" value="${content.getID()}"/>
                            <input type="hidden" name="action" value="UPDATE"/>
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                        <h4 class="modal-title" id="myModalLabel">Edit ${content.getID()}</h4>
                                    </div>
                                    <div class="modal-body">
                                        <div class="form-group">
                                            <label for="exampleInputEmail1">First Name</label>
                                            <input type="text" value="${content.getFNAME()}" name="FNAME" class="form-control" required="">
                                        </div>
                                        <div class="form-group">
                                            <label for="exampleInputEmail1">Last Name</label>
                                            <input type="text" value="${content.getLNAME()}" name="LNAME" class="form-control" required="">
                                        </div>
                                        <div class="form-group">
                                            <label for="exampleInputEmail1">Phone Number</label>
                                            <input type="text" value="${content.getPHONE()}" name="PHONE" class="form-control"  required="">
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                        <button type="submit" class="btn btn-primary">Save changes</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </td>
                    <td>
                        <form action="List" method="POST" >
                            <input type="hidden" name="ID" value="${content.getID()}"/>
                            <input type="hidden" name="action" value="DELETE"/>
                            <button type="submit" class="btn btn-danger">DELETE</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>

        </tbody> 
    </table>

</div>

