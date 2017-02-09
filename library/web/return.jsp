<%-- 
    Document   : return
    Created on : Feb 8, 2017, 3:20:01 PM
    Author     : ritzhaki
--%>

<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    Object str = request.getSession().getAttribute("premission");
    int premissionLVL = 0;
    if (str != null) {
        premissionLVL = Integer.parseInt(request.getSession().getAttribute("premission").toString());
    }
    List<Map<String, Object>> permissions = (List<Map<String, Object>>) request.getServletContext().getAttribute("premissions");
%>
<% if (premissionLVL < 2) {%>
<script>
    location.replace("/");
</script>
<% } else {
    List<Map<String, Object>> rents = (List<Map<String, Object>>) request.getAttribute("rents");
%>
<h2>Rented Books</h2>
<table class="table table-striped">
    <thead>
        <tr>
            <th>Book Name</th>
        </tr>
    </thead>
    <tbody>
        <% for (Map<String, Object> rent
                    : rents) {%>
        <tr>
            <td><%=rent.get("BOOK_NAME")%></td>              
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
                                <input type="hidden" name="id" value="<%=rent.get("RENT_ID")%>"/>
                                <input type="hidden" name="bookId" value="<%=rent.get("BOOK_ID")%>"/>
                                <div class="modal-body">
                                    <table class="table table-striped">
                                        <tbody>
                                            <tr>
                                                <td>Book Name:</td>
                                                <td><%=rent.get("BOOK_NAME")%></td>
                                            </tr>
                                            <tr>
                                                <td>Condition</td>
                                                <td><select class="form-control" name="condition_id">
                                                        <%for (Map<String, Object> condition
                                                                    : (List<Map<String, Object>>) request.getSession().getAttribute("conditions")) {
                                                                if (Integer.parseInt((String) condition.get("ID")) >= Integer.parseInt((String) rent.get("CONDITION_ID"))) {
                                                        %>
                                                        <option <%if (Integer.parseInt((String) condition.get("ID")) == Integer.parseInt((String) rent.get("CONDITION_ID"))) {%>selected<%}%> value="<%=condition.get("ID")%>"><%=condition.get("NAME")%></option>
                                                        <%}
                                                            }%>       
                                                    </select></td>

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
        <% } %>


    </tbody>
</table>
<%}%>
