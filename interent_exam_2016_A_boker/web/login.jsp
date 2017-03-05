<%-- 
    Document   : login
    Created on : Mar 5, 2017, 1:08:47 PM
    Author     : ritzhaki
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean class="String" id="error" scope="request"/>
<jsp:useBean class="String" id="USER_NAME" scope="application"/>
<jsp:useBean class="ArrayList<String>" id="actions" scope="application"/>
<div style="clear:both">
    <c:if test="${USER.getID() > 0}">
        <script>
            location.href = "List";
        </script>
    </c:if>
    <c:if test="${actions.size()> 0}">
        
        <table class="table">
            <c:forEach items="${actions}" var="action">
                <tr><td>${action}</td></tr>
            </c:forEach>
</table>
        
    </c:if>
    <c:if test="${actions.size() == 0}">


        <c:if test='${!error.equals("")}'>
            <div class="alert alert-danger" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                <span class="sr-only">Error:</span>
                ${error}
            </div>

        </c:if>

        <form action='Login' method='post'>
            <div class="form-group">
                <label for="exampleInputEmail1">User Name</label>
                <input required="" type="text" class="form-control" id="exampleInputEmail1" value="${USER_NAME}" name='USER_NAME' placeholder="user name">
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1">Password</label>
                <input required="" type="text" class="form-control" id="exampleInputPassword1" name='PASSWORD' placeholder="Password">
            </div>
            <button type="submit" class="btn btn-default">Submit</button>
        </form> 
    </c:if>
</div>
