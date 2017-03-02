<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List"%>
<%@page import="modals.Student"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="students" type="List<Student>" scope="request"/>

<table>
    <thead>
        <tr>
            <th>
                ID
            </th>
            <th>
                First Name
            </th>
            <th>
                Last Name
            </th>
            <th>
                simester
            </th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${students}" var="student">
            <c:forEach items="${student.getSimesters()}" var="simester">
                <tr>
                    <td>${student.getId()}</td>
                    <td>${student.getfName()}</td>
                    <td>${student.getlName()}</td>
                    <td>${simester}</td>
                    <td><a href="Grade?license_number=${student.getId()}&simester_number=${simester}">find simester</a></td>
                </tr>
            </c:forEach>
        </c:forEach>


    </tbody>
</table>


