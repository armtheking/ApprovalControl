<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login page</title>
        <link href="<c:url value='/resources/css/bootstrap.css' />"  rel="stylesheet"></link>
        <link href="<c:url value='/resources/css/app.css' />" rel="stylesheet"></link>
        <link rel="shortcut icon" type="image/png" href="<c:url value='/resources/img/favicon.png' />"/>
        <link rel="stylesheet" type="text/css" href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.css" />

        <style type="text/css">
            @font-face {
                font-family: myFirstFont;
                src: url("resources/fonts/Rubber Stamp2.ttf");
            }

            p {
                display: block;
                margin-top: 0;
                margin-bottom: 0;
                margin-left: 0;
                margin-right: 0;
            }

        </style>



    </head>

    <body>
        <div class="container">
            <font color="#FFFFFF">
            <header class="w3-container w3-teal">
                <br><br><br><br><br><br>

                <center> 
                    <img src="resources/img/approved-icon.png" style="width:150px;height:150px; margin-bottom: 10px;">
                    <h1><font size="10px" face="myFirstFont">Approval Control V2</font></h1> 
                </center>
                <br><br><br><br>
            </header>
            <center>
                <div class="navbar" style="width: 30%">
                    <c:url var="loginUrl" value="/login" />
                    <form action="${loginUrl}" method="post" class="form-horizontal">
                        <c:if test="${param.error != null}">
                            <div class="alert alert-danger">
                                <p>Invalid username and password.</p>
                            </div>
                        </c:if>
                        <c:if test="${param.logout != null}">
                            <div class="alert alert-success">
                                <p>You have been logged out successfully.</p>
                            </div>
                        </c:if>
                        <p align="left"><font size="2px"> Username </font></p>
                        <div class="input-group input-sm">
                            <label class="input-group-addon" for="username"><i class="fa fa-user"></i></label>
                            <input type="text" class="form-control" id="username" name="ssoId" placeholder="Enter Username" autofocus="true" required>
                        </div>
                        <p align="left"><font size="2px"> Password </font></p>
                        <div class="input-group input-sm">
                            <label class="input-group-addon" for="password"><i class="fa fa-lock"></i></label> 
                            <input type="password" class="form-control" id="password" name="password" placeholder="Enter Password" required>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}"   value="${_csrf.token}" />


                        <div class="col-md-7"> </div>

                        <div class="form-actions" style="margin-top: 15px;">
                            <input type="submit" class="btn btn-block btn-primary btn-default" value="Log In">
                        </div>

                    </form>       
                </div>
            </center>
            </font>

            <div class="navbar navbar-fixed-bottom">
                <div class="container-fluid c">
                    <span class="navbar-text">
                        <br>
                        <img src="resources/img/LOGONEC.png" style="width:50px;height:20px; margin-bottom: 10px;"><b>&nbsp; Nippon Express NEC Logistics (Thailand) Co., Ltd.</b>                

                    </span>
                    <span class="navbar-text navbar-right" style="margin-right: 2%">         
                        <b><font face="myFirstFont" size="2px" >Develope By: </font></b> 
                        <b><font face="myFirstFont" size="2px" color="#08298A">Mr.Surasak Potipipit</font></b>

                        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><font face="myFirstFont" size="2px" color="#08298A">Mr.Samart Panwan</font></b></p>

                    </span>
                </div>
            </div>


        </div>
    </body>
</html>