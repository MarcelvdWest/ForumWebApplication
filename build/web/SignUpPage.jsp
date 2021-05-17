<%-- 
    Document   : SignUpPage
    Created on : Dec 6, 2019, 12:39:08 PM
    Author     : Marcel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>  
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
                      
                </form>
            </div>
        </nav>
        
        <div class="container-fluid" > 
            <div class="col-sm-2 sidenav"></div>    
            <div class="col-sm-8 center_div">
                <div class="media">
                    <div class="media-left"></div>
                    <div class="media-body">
                        <h3>Sign Up</h3>
                        <br/>
                        <form action="CreateUser" method="POST">
                            <div class="form-group">
                                <label for="username">Username:</label><br/>
                                <input type="text" name="username" id="username" required="">
                            </div>
                            <div class="form-group">
                                <label for="email">Email:</label><br/>
                                <input type="text" name="email" id="email" required="">
                            </div>
                            <div class="form-group">
                                <label for="name">Name:</label><br/>
                                <input type="text" name="name" id="name" required="">
                            </div>
                            <div class="form-group">
                                <label for="surname">Surname:</label><br/>
                                <input type="text" name="surname" id="surname" required="">
                            </div>
                            <div class="form-group">
                                <label for="password">Password:</label><br/>
                                <input type="password" name="password" id="password"required="">
                            </div>
                            <div class="form-group">
                                <label for="confirmPassw">Confirm Password:</label><br/>
                                <input type="password" name="confirmPassw" id="confirmPassw"required="">
                            </div>
                            <div class="form-group">
                                <label for="securityAnsw">What is/was the name of your first pet?</label><br/>
                                <input type="text" name="securityAnsw" id="securityAnsw" required="">
                            </div>
                            <br/>
                                <button type="submit" class="btn btn-default">Submit</button>
                        </form>
                        <br/>
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


