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
%>
<% if (premissionLVL != 3) {%>
<script>
   location.replace("/");
</script>
<% } else {%>
<% List<Map<String, Object>> users = (List<Map<String, Object>>) request.getAttribute("users");%>
<div class="container">
    <h2>Users</h2>
    <table class="table table-striped">
        <thead>
            <tr>
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
                <td><%=user.get("FNAME")%></td>
                <td><%=user.get("LNAME")%></td>
                <td><%=user.get("PENALTY")%></td>
                <td><%=user.get("PERMISSION")%></td>                
                <td><form action="/Users" method="get">
                        <input type="hidden" name="delete-id" value="<%=user.get("ID").toString().trim()%>"/>
                        <button class="btn btn-danger">Delete</button>
                    </form></td>
            </tr>
            <% }
                ;%>


        </tbody>
    </table>
</div>
<%}%>