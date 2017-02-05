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
    if(str != null)
     premissionLVL = Integer.parseInt(request.getSession().getAttribute("premission").toString());
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
        <title>JSP Page</title>
    </head>
    <body>
        <!-- Fixed navbar -->
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container">
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="/">Home</a></li>
                            <%
                                switch (premissionLVL) {
                                    case 3:%>
                        <li><a href="/Users">Users</a></li>
                            <%
                                case 2:%>
                        <li><a href="/Rent">Rent</a></li>
                        <li><a href="/Return">Return</a></li>                       
                            <%
                                case 1:%>
                        <li><a href="/Books">Books</a></li>
                            <%
                                    default:
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
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    </body>
</html>
