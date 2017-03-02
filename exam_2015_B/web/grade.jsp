<%-- 
    Document   : grade
    Created on : Mar 2, 2017, 2:07:39 PM
    Author     : ritzhaki
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="error" type="String" scope="request"/>
<jsp:useBean id="avg" type="String" scope="request"/>
<jsp:useBean id="avg_all" type="String" scope="request"/>


<jsp:useBean id="scores" type="List<modals.Score>" scope="request"/>
<br/>

<c:if test='${!error.equals("")}'>
    <button type='button' onclick='history.back()'>Go Back</button>
    <span style="color:red">Error ${error}</span>

</c:if>
<c:if test='${error.equals("")}'>
    <ul>
        <li><a href='Home'>Peak another student</a></li>
        <li><a href='Home?license_number=<%=request.getParameter("license_number")%>'>Peak another simester</a></li>
        <li><a href='List'>list</a></li>
        <li><a href='Finish'>finish</a></li>
    </ul>
    <table>
        <thead>
            <tr>
                <th colspan='4'>
                    ${student.getfName()} ${student.getlName()}


                </th>

            </tr>
            <tr>
                <th colspan="4">
                    grad chart
                </th>
            </tr>
            <tr>
                <th>
                    Course Code
                </th>
                <th>
                    Course Name
                </th>
                <th>
                    Points
                </th>
                <th>
                    Grade
                </th>
            </tr>


        </thead>
        <tbody>
            <c:forEach items="${scores}" var="score">
                <tr>
                    <td>
                        <jsp:getProperty name="score" property="COURSE_NUMBER"/>
                    </td>
                    <td>
                        <jsp:getProperty name="score" property="COURSE_NAME"/>
                    </td>
                    <td>
                        <jsp:getProperty name="score" property="POINTS"/>
                    </td>
                    <td>
                        <jsp:getProperty name="score" property="GRADE"/>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td></td> 
                <td></td> 
                <td>avg simester</td> 
                <td>${avg}</td> 
            </tr>
            <tr>
                <td></td> 
                <td></td> 
                <td>avg all</td> 
                <td>${avg_all}</td> 
            </tr>
        </tbody>
    </table>
</c:if>

