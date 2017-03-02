<%-- 
    Document   : home
    Created on : Mar 2, 2017, 1:13:39 PM
    Author     : ritzhaki
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="error" type="String" scope="request"/>
<jsp:useBean id="license_number" type="String" scope="request"/>
<c:if test='${license_number.equals("")}'>

    <h1>welcome to the grades app</h1>
    <h2> please enter your license number</h2>
    <c:if test='${!error.equals("")}'>
        <span style="color:red">
            error! ${error}
        </span>
    </c:if>
    <form action="Home">
        <input type='text' name='license_number'/>
        <button type='submit'>Enter</button>
    </form>
    <hr/>
    <h3>
        <a href="List">or you can get a full list of the students</a>
    </h3>
</c:if>
<c:if test='${!license_number.equals("")}'>
    <h2>please enter the simester number</h2>
        <form action="Grade">
            <input type ="hidden" name='license_number' value="${license_number}"/>
            <input type='text' name='simester_number'/>
        <button type='submit'>Enter</button>
    </form>
</c:if>
