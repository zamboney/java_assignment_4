<%-- 
    Document   : users
    Created on : Feb 5, 2017, 5:50:16 PM
    Author     : ritzhaki
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Object str = request.getSession().getAttribute("premission");
    int premissionLVL = 0;
    if (str != null) {
        premissionLVL = Integer.parseInt(request.getSession().getAttribute("premission").toString());
    }
    List<Map<String, Object>> permissions = (List<Map<String, Object>>) request.getServletContext().getAttribute("premissions");
%>
<% if (premissionLVL != 3) {%>
<script>
    location.replace("/");
</script>
<% } else {%>


<% List<Map<String, Object>> users = (List<Map<String, Object>>) request.getAttribute("users");%>
<div class="container">
    <h2>Users</h2>
    <button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
        Create User
    </button>

    <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Create New User</h4>
                </div>
                <form action="/Users" method="POST">
                    <div class="modal-body">
                        <input type="hidden" name="Create"/>
                        <div class="form-group">
                            <label for="UserName">User Name</label>
                            <input required type="text" class="form-control" name="UserName" id="UserName"/>
                        </div>
                        <div class="form-group">
                            <label for="Password">Password</label>
                            <input required type="text" class="form-control" name="Password" id="Password"/>
                        </div>
                        <div class="form-group">
                            <label for="FirstName">First Name</label>
                            <input required type="text" class="form-control" name="FirstName" id="FirstName"/>
                        </div>
                        <div class="form-group">
                            <label for="LastName">Last Name</label>
                            <input required type="text" class="form-control" name="LastName" id="LastName"/>
                        </div>
                        <div class="form-group">
                            <label for="Permission">Permission</label>
                            <select required name="Permission" class="form-control" id="Permission">
                                <% for (Map<String, Object> permission : permissions) {%>
                                <option value="<%=permission.get("ID")%>"><%=permission.get("NAME")%></option>
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
    <table class="table table-striped">
        <thead>
            <tr>
                <th>User Name</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Penalty</th>
                <th>Permission</th>
            </tr>
        </thead>
        <tbody>
            <% for (Map<String, Object> user
                        : users) {%>
            <tr>
                <td><%=user.get("USER_NAME")%></td>
                <td><%=user.get("FNAME")%></td>
                <td><%=user.get("LNAME")%></td>
                <td><%=user.get("PENALTY")%></td>
                <td><%=user.get("PERMISSION")%></td>                
                <td>
                    <% if(!user.get("ID").toString().equals("1")) { %>
                    <form action="/Users" method="get">
                        <input type="hidden" name="delete-id" value="<%=user.get("ID").toString().trim()%>"/>
                        <button class="btn btn-danger">Delete</button>
                    </form></td>
                    <% } %>
            </tr>
            <% }
                ;%>


        </tbody>
    </table>
</div>

<!-- Button trigger modal -->

<%}%>