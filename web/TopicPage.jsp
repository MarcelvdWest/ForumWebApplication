<%-- 
    Document   : TopicPage
    Created on : Dec 5, 2019, 11:47:48 AM
    Author     : Marcel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="beans.DatabaseBean"%>
<!DOCTYPE html>
<html>
    <%
            
            /**
             * Holds the value of the topic parameter retrieved from the URL
             */
            String topic = request.getParameter("topic");
            
            if(session.getAttribute("logginStatus") != null){
                /**
                * The search attribute currently in the session variable
                */
                boolean logginStatus = (boolean)request.getSession().getAttribute("logginStatus");
                
                if(logginStatus == true){
                    topic = (String)request.getSession().getAttribute("topic");
                    response.sendRedirect("TopicPage2.jsp?topic=" + topic);
                }
            }
            
            /**
             * This holds the URI the previous setRequest method loaded to the session variable
             */
            String loc = request.getRequestURI();
            loc = loc.substring(loc.lastIndexOf("/") + 1);
            request.getSession().setAttribute("loc", loc);
            DatabaseBean db = new DatabaseBean();
            
            String [][] feed = db.getTopic(topic);
            String [][] comments = db.getTopicComment(feed[0][0]);
 
            request.getSession().setAttribute("topic", topic);
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DeBugged</title>
        <!--Links to Bootstrap-->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        
        <link rel="stylesheet" href="style.css">
    </head>
    <body>
        <div class="container">
            <div class="jumbotron">
        <!--<h1 class="head">DeBugged</h1>--> 
                <h2 class="head">DeBugged</h2>
            </div>    
        </div>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="index.jsp">Home</a></li>              
                </ul>
                <form class="navbar-form" action="SearchPage.jsp">
                    <div class="form-group">
                        <input type="text" class="form-control" name="search" placeholder="Search...">
                    </div>
                    <button type="submit" class="btn btn-default">Search</button>
                    
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="next.jsp"><span class="glyphicon glyphicon-user"></span>Sign Up</a></li>
                        <li><a href="LoginPage.jsp"><span class="glyphicon glyphicon-log-in"></span>Login</a></li>
                    </ul>  
                </form>   
            </div>
        </nav>
        
        <div class="container-fluid" > 
            <div class="col-sm-2 sidenav"></div>    
            <div class="col-sm-8">
                <div class="media">
                    <div class="media-left"></div>
                    <div class="media-body">

                        <h3 class="media-heading"><%=feed[0][1]%><small><%= "  " + feed[0][3]%></small></h3>
                        <br/>
                        <div class="content"><%=feed[0][2]%></div>
                            <form action="LoginPage.jsp">    
                                <button type="submit" class="btn btn-default pull-right">Comment</button>
                            </form>    
                        <br/>
                        
                        <div class="media">
                            <div class="media-left"></div>
                            <div class="media-body">
                                <h4 class="media-heading">Comments</h4>
                                <hr/>
<%
                        if(!comments[0][0].equals("-1")){  
                            for(int i = 0 ; i < comments.length ; i++){

%>                            
                                <div class="media">
                                    <div class="media-left"></div>
                                    <div class="media-body">
                                        <div class="content"><strong><%=comments[i][2]%></strong><%=" " + comments[i][1]%></div>
                                    </div>
                                    
<%
                                
                                String[][] replies = db.getCommentReplies(Integer.parseInt(comments[i][0]));
                                
                                if(!replies[0][0].equals("-1")){
%>                                    
                                    <div class="media">
                                        <div class="media-left"></div>
                                        <div class="media-body">
                                            <br/>
                                            <h5 class="media-heading" style="margin-left:20px"><strong>Replies</strong></h5>
<%                                    
                                    for(int j = 0 ; j < replies.length ; j++){     
                                    
%>           
                                        <div class="media" style="margin-left:30px">
                                            <div class="media-left"></div>
                                            <div class="media-body" >
                                                <div class="content" ><strong><%=replies[j][1]%></strong><%=" " + replies[j][0]%></div>
                                            </div>                                             
                                        </div>        
<%                                  
                                    }
%>
                                            <br/>
                                            <form action="LoginPage.jsp">
                                                <input type="hidden" name="comment" value="<%=comments[i][0]%>">
                                                <button type="submit" class="btn btn-default">Reply</button>
                                            </form>
                                            <hr/>  
                                        </div>
                                    </div> 
<%                                               
                                }else{
%>
                                    <br/>
                                    <form action="LoginPage.jsp">    
                                        <button type="submit" class="btn btn-default">Reply</button>
                                    </form>
                                    <hr/>
<%
                                }
%>                
                                </div> 
<%
                            } 
                               
                        }else{
%>
                            <div class="media">
                                <div class="media-left"></div>
                                <div class="media-body">
                                    <p>There are no comments for this topic yet!!</p>
                                </div>
                            </div> 
<%                            
                        }        
%>
                            <br/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>  
            <div class="col-sm-2 sidenav"></div>
        </div>
                    
        <nav class="navbar fixed-bottom navbar-inverse ">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">DeBugged</a>
                </div>
            </div>
        </nav>
    </body>
</html>
