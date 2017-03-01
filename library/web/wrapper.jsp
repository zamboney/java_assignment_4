<%-- 
    Document   : wrapper
    Created on : Feb 5, 2017, 1:04:58 PM
    Author     : ritzhaki
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.lang.*" %>
<%String jspPage = (String) request.getAttribute("jsp");%>
<%
    Object str = request.getSession().getAttribute("premission");
    int premissionLVL = 0;
    if (str != null) {
        premissionLVL = Integer.parseInt(request.getSession().getAttribute("premission").toString());
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <title>JSP Page</title>
        <link href="/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <!-- Fixed navbar -->
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container">
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="/home">Home</a></li>
                            <%
                                switch (premissionLVL) {
                                    case 3:%>
                        <li><a href="/Users">Users</a></li>
                            <%
                                case 2:%>
                        <li><a href="/Return">Return</a></li>                       
                            <%
                                case 1:
                                default:%>
                        <li><a href="/Books">Books</a></li>
                            <%
                                        break;
                                }%>




                    </ul>
                    <ul class="nav navbar-nav navbar-right">

                        <li><a href="/User">
                                <% if (request.getSession().getAttribute("user") != null) { %>
                                Profile
                                <% } else { %>
                                Login
                                <% }%>
                            </a></li>

                    </ul>
                </div>     
            </div>
        </nav>
        <div class="container">
            <!-- Main component for a primary marketing message or call to action -->
            <div class="jumbotron">
                <jsp:include page="<%= jspPage%>"/>
            </div>

        </div> <!-- /container -->
        <script src="js/jquery.min.js" type="text/javascript"></script>
        <script src="js/jquery.validate.min.js" type="text/javascript"></script>
        <script src="js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>
