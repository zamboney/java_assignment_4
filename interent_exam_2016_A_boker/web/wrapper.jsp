<%-- 
    Document   : wraper
    Created on : Mar 5, 2017, 1:23:46 PM
    Author     : ritzhaki
--%>

<%@page import="modals.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="USER" scope="session" class="User"/>
<!DOCTYPE html>
<html>
    <jsp:useBean id="currentView" scope="request" class="String"/>
    <head>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="jumbotron">

            <div class="container">
                <c:if test="${USER.getID() > 0}">
                    <div class="navbar-text navbar-right">Signed in as <a href="Login?logout=true" class="navbar-link">${USER.getUSER_NAME()}</a></div>
                </c:if>
                <c:if test="${USER.getID() == 0}">
                    <div class="navbar-text navbar-right"><a href="Login" class="navbar-link">Signed in</a></div>
                </c:if>
                <jsp:include page="${currentView}"/>

            </div>
        </div>

    </body>
    <script src="js/jquery.min.js" type="text/javascript"></script>
    <script src="js/jquery.validate.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
</html>
