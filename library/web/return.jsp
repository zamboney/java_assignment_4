<%-- 
    Document   : return
    Created on : Feb 8, 2017, 3:20:01 PM
    Author     : ritzhaki
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    Object str = request.getSession().getAttribute("premission");
    int premissionLVL = 0;
    if (str != null) {
        premissionLVL = Integer.parseInt(request.getSession().getAttribute("premission").toString());
    }
    List<Map<String, Object>> permissions = (List<Map<String, Object>>) request.getServletContext().getAttribute("permissions");
%>
<% if (premissionLVL < 2) {%>
<script>
    location.replace("/");
</script>
<% } else {%>
<jsp:useBean id="rents" scope="request" class="List<modals.Rent>"/>
<h2>Rented Books</h2>
<table class="table table-striped">
    <thead>
        <tr>
            <th>Book Name</th>
            <th>Penalty</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${rents}" var="rent">
            <tr>
                <td><jsp:getProperty name="rent" property="bookName"/></td>  
                <td><jsp:getProperty name="rent" property="penalty"/></td>  
                <td class="pull-right">

                    <!-- Trigger the modal with a button -->
                    <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal">Return</button>

                    <!-- Modal -->
                    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModal">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="myModalLabel">Return Book</h4>
                                </div>
                                <form  action="/Return" method="POST" >
                                    <input type="hidden" name="type" value="return"/>
                                    <input type="hidden" name="id" value="${rent.getId()}"/>
                                    <input type="hidden" name="bookId" value="${rent.getBookId()}"/>
                                    <div class="modal-body">
                                        <table class="table table-striped">
                                            <tbody>
                                                <tr>
                                                    <td>Book Name:</td>
                                                    <td>${rent.getBookName()}</td>
                                                </tr>
                                                <tr>
                                                    <td>Condition</td>
                                                    <td><select id="PENALTY_SELECT" onchange="(function (e) {
                                                                document.querySelector('[name=PENALTY]').value =
                                                                        document.querySelector('#PENALTY_TEXT').innerHTML =
                                                                        parseInt(document.querySelector('[name=PENALTY_HIDDEN]').value) +
                                                                        parseInt(document.querySelector('#PENALTY_SELECT').selectedOptions[0].value) -
                                                                        parseInt(document.querySelector('#PENALTY_SELECT').querySelector('[selected]').value);
                                                            })()" class="form-control" name="condition_id">
                                                            <c:forEach items="${conditions}" var="condition">
                                                                <c:if test="${rent.getConditionId() == condition.getId()}">
                                                                    <option selected="" value="${condition.getId()}">${condition.getName()}</option>
                                                                </c:if>
                                                                <c:if test="${rent.getConditionId() < condition.getId()}">
                                                                    <option value="${condition.getId()}">${condition.getName()}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select></td>

                                                </tr>

                                                    <tr class="danger">
                                                        <td>PENALTY:</td>
                                                        <td id="PENALTY_TEXT">${rent.getPenalty()}</td>

                                            <input name="PENALTY" type="hidden" value="${rent.getPenalty()}"/>

                                            <input name="PENALTY_HIDDEN" type="hidden" value="${rent.getPenalty()}"/>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                        <button type="submit" class="btn btn-primary">Return</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
        </c:forEach>



    </tbody>
</table>
<%}%>
