<%-- 
    Document   : ReplyPage
    Created on : Dec 7, 2019, 10:23:26 AM
    Author     : Marcel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="beans.DatabaseBean" %>%>
<!DOCTYPE html>
<html>
    <%
        
        /**
         * Holds current value of the topic in the session variable
         */
        String topic = (String)request.getSession().getAttribute("topic");
        /**
         *  Holds current value of the username in the session variable
         */
        String username = (String)request.getSession().getAttribute("username");
        /**
         *  Holds current value of comment parameter sent in the URL
         */
        String commentId = request.getParameter("comment");
        DatabaseBean db = new DatabaseBean();
        
        String[][] feed = db.getTopic(topic);
        String[][] comments = db.getComment(Integer.parseInt(commentId));
        
        System.out.println(commentId);
	
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
                    <li class="active"><a href="index2.jsp">Home</a></li>              
                </ul>
                <form class="navbar-form" action="SearchPage2.jsp">
                    <div class="form-group">
                        <input type="text" class="form-control" name="search" placeholder="Search...">
                    </div>
                    <button type="submit" class="btn btn-default">Search</button>
                    
                    <ul class="nav navbar-nav navbar-right">
                        <li class="navbar-brand">Hello <%=username%></li>
                        <li><a href="LogOff"><span class="glyphicon glyphicon-log-in"></span>Log Out</a></li>   
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
                        <br/>
                        
                        <div class="media">
                            <div class="media-left"></div>
                            <div class="media-body">
                                <h4 class="media-heading">Comments</h4>
                                
<%
                        if(!comments[0][0].equals("-1")){  
%>                            
                                <div class="media">
                                    <div class="media-left"></div>
                                    <div class="media-body">
                                        <div class="content"><strong><%=comments[0][1]%></strong><%=" " + comments[0][0]%></div>
                                    </div>
                                    
<%
                                String[][] replies = db.getCommentReplies(Integer.parseInt(commentId));
                                
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
                                            <div class="media-body">
                                                <div class="content"><strong><%=replies[j][1]%></strong><%=" " + replies[j][0]%></div>
                                            </div>
                                            
                                            <hr/>    
                                        </div>        
<%                                  
                                    }
%>
                                        </div>
                                    </div>            
<%
                                }else{   
%>
                                    
                                    <hr/>
<%
                                }
%>                
                                </div> 
<%                               
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
                               <div class="form-group">
                                    <form action="AddReply" method="POST">
                                        <label for="reply">Reply:</label>
                                        <textarea class="form-control" rows="5" name="reply" id="reply" required=""></textarea>
                                        <input type="hidden" name="comment" value="<%=commentId%>">
                                        <input type="submit" name="Submit" class="btn btn-default pull-right">
                                        <br/> 
                                    </form>
                                   
                                </div>
                               
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
