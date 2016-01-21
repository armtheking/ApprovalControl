<%-- 
    Document   : pettycash
    Created on : Sep 30, 2015, 10:05:47 AM
    Author     : C13.207
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-ng-app="formSubmit">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Pettycash Page</title>
        <link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" />
        <link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-theme.min.css" />
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

        <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>

        <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
        <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
        <script src= "http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">

        <script src="${pageContext.request.contextPath}/resources/js/jquery-2.0.3.min.js"></script>
    </head>
    <body>

        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">
                        <i class="fa fa-home"> HOME </i>
                    </a>

                    <a class="navbar-brand" href="${pageContext.request.contextPath}/pettycash">
                        PettyCash
                    </a>
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/restpettycash">
                        Rest
                    </a>

                    <a class="navbar-brand" href="#">
                        Member
                    </a>
                </div>
            </div>
        </nav>

        <!-- enctype="multipart/form-data" ติดปัญหาตรงนี้ เลยไม่สามารถทำการส่ง variable ได้ -->

        <div class="container">
            <form data-ng-submit="submit()" class="form-horizontal" data-ng-controller="FormSubmitController"
                  >
                <fieldset>
                    <legend>Petty Cash</legend>
                    <div class="form-group">
                        <label for="detailHeader" class="col-sm-2 control-label">Topic: </label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="detailHeader" name="detailHeader" data-ng-model="detailHeader" placeholder="Topic">
                        </div>
                        <div class="col-sm-5">
                            <label>**The red text it can changes as appropriate<br/>
                                1.I'm create ticket for someone.For the cost of something.(such as Express Way)
                                2.The cost of travel from someplace go to someplace.(Multiple description in one ticket)
                                3.The cost for something(such as Parking Fee /Express way /Fuel /Hired Car and Taxi /etc.) </label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="remarkHeader" class="col-sm-2 control-label">Reason</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="remarkHeader" name="remarkHeader" data-ng-model="remarkHeader" placeholder="Reason">
                        </div>
                    </div>
                </fieldset>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-primary">submit</button>
                        <button type="reset" class="btn btn-danger">reset</button>
                    </div>
                </div>
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">id</th>
                            <th scope="col">id card</th>
                            <th scope="col">Name</th>
                            <th scope="col">Action</th>
                            <th scope="col">#</th>


                        </tr>
                    </thead>
                    <tbody>

                        <tr ng-repeat="x in list">
                            <td>{{x.ticketNo}}</td>
                            <td>{{x.detailHeader}}</td>
                            <td>{{x.remarkHeader}}</td>
<td>        
                                <a href="pettycash/show-{{x.ticketNo}}" class="btn btn-primary">show</a></td>
                            <td>       
                                
                                <a href="pettycash/edit_page?id={{x.ticketNo}}" class="btn btn-primary">edit</a>
                                <a href="pettycash/delete-{{x.ticketNo}}" class="btn btn-danger btn-delete">delete</a>
                                
                                <button type="button" ng-click="delete(x.ticketNo)" class="btn btn-danger custom-width">Remove</button>
                            </td>
                        </tr>

                    </tbody>

                </table>

            </form>
        </div>
        <script src="${pageContext.request.contextPath}/resources/js/app.js"></script>
    </body>
</html>
