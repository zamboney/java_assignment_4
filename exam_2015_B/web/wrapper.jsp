<%-- 
    Document   : warper
    Created on : Mar 2, 2017, 1:11:53 PM
    Author     : ritzhaki
--%>

<%@page import="modals.Student"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="currentView" scope="request" class="String"/>
<jsp:useBean id="student" scope="session" class="Student"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:if test="${student.getId() > 0}" >
            <span>
                hello ${student.getfName()} ${student.getlName()}
            </span>
    </c:if>
        <jsp:include page="${currentView}"/>
    </body>
</html>
