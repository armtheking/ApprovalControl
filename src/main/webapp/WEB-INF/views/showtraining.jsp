<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
    <head>

        <%@ include file="template/head.jspf" %>

    </head>
    <body>

        <%@ include file="template/pageTitle.jspf" %>


        <div class="container">

            <fieldset>
                <legend>Show Detail</legend>



                <!-- Row 2 -->
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-danger">
                            <div class="panel-heading clearfix">
                                <h3 class="panel-title pull-left" style="padding-top: 15px; font-weight: bold;">Ticket No: <c:if test="${empty ticketHTraining.ticketHeader.showTicket}">
                                        ${ticketHTraining.ticketHeader.ticketNo}
                                    </c:if>
                                    <c:if test="${not empty ticketHTraining.ticketHeader.showTicket}">
                                        ${ticketHTraining.ticketHeader.showTicket}
                                        &nbsp;<a href="<c:url value='../showRevise?id=${ticketHTraining.ticketHeader.ticketNo}'/>" style="font-weight: bold; color: #FFFFFF;" class="btn btn-danger" target="_blank"> <i class="fa fa-search"> View</i></a>               
                                    </c:if></h3>

                                <div class="btn-group pull-right">
                                    <c:if test="${principal.username eq ticketHTraining.ticketHeader.applicationName and ticketHTraining.ticketHeader.approvedStatus1 eq false or ticketHTraining.ticketHeader.ticketFinished eq 'R'}"> 

                                        <a href="<c:url value='/training/toEdit?id=${ticketHTraining.ticketHeader.ticketNo}'/>" class="btn btn-primary btn-lg"><i class="fa fa-pencil-square-o"></i> Edit </a>
                                    </c:if>
                                </div>

                            </div>

                            <div class="panel-body">

                                <c:if test="${principal.username eq ticketHTraining.ticketHeader.applicationName and ticketHTraining.ticketHeader.ticketFinished eq 'F'}"> 
                                    <br>
                                    <div class="panel panel-primary">
                                        <div class="panel-heading clearfix">
                                            <h3 class="panel-title pull-left" style="padding-top: 7.5px;"><b>PRINT</b></h3>


                                        </div>
                                        <div class="row">
                                            <div class="col-md-6">

                                                <div class="well">
                                                    <center> <a href="<c:url value='/report/download/pdf_HR?id=${ticketHTraining.ticketHeader.ticketNo}'/>" class="btn btn-info btn-lg " target="_blank">HR <span class="fa fa-print"></span></a> </center>
                                                </div>
                                            </div>

                                            <div class="col-md-6">

                                                <div class="well">
                                                    <center> <a href="<c:url value='/report/download/pdf_Finance?id=${ticketHTraining.ticketHeader.ticketNo}'/>" class="btn btn-warning btn-lg " target="_blank">FINANCE <span class="fa fa-print"></span></a> </center>
                                                </div>
                                            </div>

                                        </div> 
                                    </div>
                                    <br>
                                </c:if>   



                                <div class="row">
                                    <div class="col-md-6">
                                        <label> Training Title </label>
                                        <div class="well" style="width:540px; word-wrap: break-word; display:block;">
                                            ${ticketHTraining.ticketHeader.detailHeader}
                                        </div>
                                    </div>

                                    <div class="col-md-3">
                                        <label> Application Name </label>
                                        <div class="well">
                                            ${ticketHTraining.ticketHeader.applicationName}
                                        </div>
                                    </div>
                                    <div class="col-md-2">
                                        <label> Application Date </label>
                                        <div class="well">
                                            ${ticketHTraining.ticketHeader.applicationDate}
                                        </div>
                                    </div>

                                </div>

                                <div class="row">


                                    <div class="col-md-6">
                                        <label> Objective </label>
                                        <div class="well" style="width:540px; word-wrap: break-word; display:block;">
                                            ${ticketHTraining.ticketHeader.remarkHeader}
                                        </div>
                                    </div>

                                </div>

                                <div class="row">
                                    <div class="col-md-3">
                                        <label> Type of Training </label>
                                        <div class="well">
                                            ${ticketHTraining.trainingType.typeDescription}
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <label> Plan </label>
                                        <div class="well">
                                            ${ticketHTraining.trainingPlan.planDescription}
                                        </div>
                                    </div>    
                                    <div class="col-md-3">
                                        <label> Start Date </label>
                                        <div class="well">
                                            ${ticketHTraining.startDate}
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <label> End Date </label>
                                        <div class="well">
                                            ${ticketHTraining.endDate}
                                        </div>
                                    </div>    
                                </div>       

                                <div class="row">
                                    <div class="col-md-6">
                                        <label> Organized By </label>
                                        <div class="well">
                                            ${ticketHTraining.organizeBy}
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <label> Payment </label>
                                        <div class="well">
                                            ${ticketHTraining.trainingPayment.paymentDescription}
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <label> Date of Payment </label>
                                        <div class="well">
                                            ${ticketHTraining.paymentDate}
                                        </div>
                                    </div>
                                </div>        



                                <div class="row">

                                    <div class="col-md-3">
                                        <label> Attachments </label>
                                        <div class="well">
                                            <a href="<c:url value='/downloadTraining?id=${ticketHTraining.ticketHeader.ticketNo}' />" target="_blank">
                                                <img border="0" alt="${principal.username}" src="<c:url value='/resources/img/pdf.png' />" width="35" height="32">
                                            </a>
                                            Content of Training
                                        </div>
                                    </div>

                                    <div class="col-md-3">
                                        <label> &nbsp; </label>
                                        <div class="well">
                                            <a href="<c:url value='/downloadTraining?id=${ticketHTraining.ticketHeader.ticketNo}-Participant' />" target="_blank">
                                                <img border="0" alt="${principal.username}" src="<c:url value='/resources/img/pdf.png' />" width="35" height="32">
                                            </a>
                                            List of Participant
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <label> Place </label>
                                        <div class="well">
                                            ${ticketHTraining.place}
                                        </div>
                                    </div>   
                                    <div class="col-md-3">
                                        <label> Total Person </label>
                                        <div class="well">
                                            ${ticketHTraining.totalPerson}
                                        </div>
                                    </div>     
                                </div>






                                <div class="row"> 
                                    <div class="col-md-12">
                                        <div class="panel panel-danger">
                                            <div class="panel-heading" >
                                                <h4 style=" font-weight: bold;">List of Participant
                                                    <span class="btn-group pull-right">  <a href="<c:url value='/report/download/pdf_participant?id=${ticketHTraining.ticketHeader.ticketNo}'/>" class="btn btn-danger
                                                                                            " target="_blank">PRINT<span class="fa fa-print"></span></a> 
                                                    </span>
                                                </h4>

                                            </div>
                                            <div class="panel-body">
                                                <table id="tb_participant"  class="table table-striped" cellspacing="0" width="100%">
                                                    <thead>
                                                        <tr>
                                                            <th>No</th>                               
                                                            <th>EmployeeID</th>
                                                            <th>Firstname</th>
                                                            <th>Lastname</th>
                                                            <th>Section</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>  

                                                        <c:forEach varStatus="varStatus" var="trainingParticipant" items="${ticketHTraining.ticketHeader.trainingParticipant}">
                                                            <tr>     
                                                                <c:if test="${trainingParticipant.empID != ''}">
                                                                    <td>${varStatus.count}</td>
                                                                    <td>${trainingParticipant.empID}</td>
                                                                    <td>${trainingParticipant.firstName}</td>
                                                                    <td>${trainingParticipant.lastName}</td>
                                                                    <td>${trainingParticipant.section}</td>
                                                                </c:if>
                                                                <c:if test="${trainingParticipant.empID == ''}">
                                                                    <td>-</td>
                                                                    <td>-</td>
                                                                    <td>-</td>
                                                                    <td>-</td>
                                                                    <td>-</td>
                                                                </c:if>   
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>



                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="panel panel-success">
                                            <div class="panel-heading">
                                                <h4 style=" font-weight: bold;">Detail</h4>
                                            </div>
                                            <div class="panel-body">
                                                <table class="table table-striped">
                                                    <thead>
                                                        <tr>
                                                            <th class="col-md-1"> No </th>
                                                            <th class="col-md-2"> Item </th>
                                                            <th class="col-md-4"> Details </th>
                                                            <th class="col-md-1"> Amount </th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:set var="count" value="0" scope="page" />
                                                        <c:forEach var="ticketDTraining" items="${ticketDTraining}">
                                                            <tr>
                                                                <td><c:set var="count" value="${count + 1}" scope="page"/> 
                                                                    <c:out value="${count}" />
                                                                </td>
                                                                <td>${ticketDTraining.item}</td>
                                                                <td style="max-width: 360px; word-wrap: break-word;">
                                                                    ${ticketDTraining.budgetDetail}
                                                                </td>
                                                                <td align="right">
                                                                    ${ticketDTraining.amount}
                                                                </td>
                                                            </tr>
                                                        </c:forEach>

                                                    </tbody>

                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>



                                <div class="row text-right">
                                    <div class="col-xs-2 col-xs-offset-8">
                                        <p>
                                            <strong>
                                                Total amount : <br>

                                            </strong>
                                        </p>
                                    </div>
                                    <div class="col-xs-2">
                                        <strong>
                                            <span class="doubleUnderline">${number_sumAmount}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <br>

                                        </strong>
                                    </div>
                                </div>
                                <div class="row text-right">
                                    <div class="col-xs-2 col-xs-offset-8">
                                        <p>
                                            <strong>
                                                Cost/Head : <br>

                                            </strong>
                                        </p>
                                    </div>
                                    <div class="col-xs-2">
                                        <strong>
                                            <span class="doubleUnderline">${number_costPerHead}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <br>

                                        </strong>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="panel panel-warning">
                                            <div class="panel-heading">
                                                <h4 style=" font-weight: bold;">Status Approve</h4>
                                            </div>
                                            <div class="panel-body">

                                                <div class="row">
                                                    <div class="col-md-5">
                                                        <label> 1st Approve </label>
                                                        <div class="well well-sm">
                                                            ${ticketHTraining.ticketHeader.approvedName1}
                                                        </div>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label> Date </label>
                                                        <div class="well well-sm">
                                                            ${ticketHTraining.ticketHeader.approvedDate1}
                                                        </div>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <label> Note </label>
                                                        <div class="well well-sm">

                                                            <c:if test="${ticketHTraining.ticketHeader.approvedRemark1 eq null or ticketHTraining.ticketHeader.approvedRemark1 eq ''}">
                                                                <font style="color: red; font-weight: bold">-</font>
                                                            </c:if>

                                                            <font style="color: red;font-weight: bold">${ticketHTraining.ticketHeader.approvedRemark1}</font>
                                                        </div>
                                                    </div>

                                                </div>


                                                <div class="row">
                                                    <div class="col-md-5">
                                                        <label> 2nd Approve </label>
                                                        <div class="well well-sm">
                                                            ${ticketHTraining.ticketHeader.approvedName2}
                                                        </div>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label> Date </label>
                                                        <div class="well well-sm">
                                                            ${ticketHTraining.ticketHeader.approvedDate2}
                                                        </div>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <label> Note </label>
                                                        <div class="well well-sm">
                                                            <c:if test="${ticketHTraining.ticketHeader.approvedRemark2 eq null or ticketHTraining.ticketHeader.approvedRemark2 eq ''}">
                                                                <font style="color: red; font-weight: bold">-</font>
                                                            </c:if>

                                                            <font style="color: red;font-weight: bold">${ticketHTraining.ticketHeader.approvedRemark2}</font>
                                                        </div>
                                                    </div>

                                                </div>







                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
                                    <c:if test="${ticketHTraining.ticketHeader.ticketFinished eq 'F'}">
                                        <div class="row">

                                            <div class="col-md-12">
                                                <div class="panel panel-primary">
                                                    <div class="panel-heading">
                                                        <h4 style=" font-weight: bold;">Finance</h4>
                                                    </div>
                                                    <div class="panel-body">
                                                        <div class="row">
                                                            <div class="col-md-4">
                                                                <label> Receiver By: </label>
                                                                <div class="well well-sm">
                                                                    -
                                                                </div>
                                                            </div>
                                                            <div class="col-md-4">
                                                                <label> Paid By: </label>
                                                                <div class="well well-sm">
                                                                    -
                                                                </div>
                                                            </div>
                                                            <div class="col-md-4">
                                                                <label> Paid Date: </label>
                                                                <div class="well well-sm">
                                                                    -
                                                                </div>
                                                            </div> 
                                                        </div>  
                                                        <div class="row">
                                                            <div class="col-md-4">
                                                                <label> Request Total Amount: </label>
                                                                <div class="well well-sm">
                                                                    ${ticketHTraining.ticketHeader.reqTotalAmt + ticketHTraining.ticketHeader.payBack}
                                                                </div>
                                                            </div>

                                                            <div class="col-md-4">
                                                                <label> Paid Total Amount: </label>
                                                                <div class="well well-sm">
                                                                    ${ticketHTraining.ticketHeader.reqTotalAmt}
                                                                </div>
                                                            </div>
                                                            <div class="col-md-4">
                                                                <label> Balance / Refund </label>
                                                                <div class="well well-sm">
                                                                    <c:if test="${ticketHTraining.ticketHeader.payBack eq null}">
                                                                        0.00
                                                                    </c:if>
                                                                    <c:if test="${ticketHTraining.ticketHeader.payBack >= 0}">
                                                                        ${ticketHTraining.ticketHeader.payBack}
                                                                    </c:if>
                                                                </div>
                                                            </div> 
                                                        </div>              
                                                        <div class="row">
                                                            <div class="col-md-4">
                                                                <label> Payment: </label>
                                                                <div class="well well-sm">
                                                                    -
                                                                </div>
                                                            </div>
                                                            <div class="col-md-8">
                                                                <label> Paid Note: </label>
                                                                <div class="well well-sm" style="width:706 px; word-wrap: break-word; display:block;">
                                                                    -
                                                                </div>
                                                            </div> 
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </sec:authorize>
                            </div>
                            <div class = "panel-footer">
<!--                                <button class="btn btn-primary" onclick="goBack()">Go Back</button>-->
                                <c:if test="${principal.username eq ticketHTraining.ticketHeader.applicationName and ticketHTraining.ticketHeader.approvedStatus1 eq false or (ticketHTraining.ticketHeader.ticketFinished eq 'R' and principal.username eq ticketHTraining.ticketHeader.applicationName)}"> 

                                    <a style="margin-left: 81%" href="<c:url value='/createticket/delete?id=${ticketHTraining.ticketHeader.ticketNo}'/>" class="btn btn-danger btn-delete btn-lg" onclick="return confirm('Are you sure?')"><i class="fa fa-trash"></i> Delete</a>&nbsp;
                                </c:if>

                                <c:if test="${(fn:contains(ticketHTraining.ticketHeader.approvedName1, principal.username)) or (fn:contains(ticketHTraining.ticketHeader.approvedName2, principal.username))}"> 
                                    <div class="row">
                                        <div class="col-md-12" style="text-align: center">

                                            <c:if test="${ticketHTraining.ticketHeader.ticketFinished eq '0' and (fn:contains(ticketHTraining.ticketHeader.approvedName1, principal.username)) or ticketHTraining.ticketHeader.ticketFinished eq 'C' and (fn:contains(ticketHTraining.ticketHeader.approvedName1, principal.username))}"> 
                                                <a href="<c:url value='/approve/personone?id=${ticketHTraining.ticketHeader.ticketNo}'/>" class="btn btn-danger btn-lg "> 1st Approved <span class="fa fa-hand-pointer-o"></span></a>
                                                <!-- Button trigger modal -->
                                                <button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
                                                    Reject <i class="fa fa-reply"></i>
                                                </button>

                                                <!-- Modal -->
                                                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                                    <div class="modal-dialog" role="document">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                                <h4 class="modal-title" id="myModalLabel">Reject Message</h4>
                                                            </div>
                                                            <div class="modal-body">
                                                                <form action="${pageContext.request.contextPath}/reject/firstApprove" method="GET">
                                                                    <div class="form-group">
                                                                        <input type="hidden" class="form-control" id="id" name="id" value="${ticketHTraining.ticketHeader.ticketNo}">
                                                                        <label for="remark" class="control-label">Message:</label>
                                                                        <textarea class="form-control" id="remark" name="remark"></textarea>
                                                                    </div>
                                                                    <button type="submit" class="btn btn-primary">SUBMIT</button>
                                                                </form>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>

                                            <c:if test="${(ticketHTraining.ticketHeader.ticketFinished eq '1' or ticketHTraining.ticketHeader.ticketFinished eq 'P') and (fn:contains(ticketHTraining.ticketHeader.approvedName2, principal.username))}"> 

                                                <c:if test="${ticketHTraining.ticketHeader.ticketFinished eq '1'}">  
                                                    <a href="<c:url value='/approve/persontwo?id=${ticketHTraining.ticketHeader.ticketNo}'/>" class="btn btn-danger btn-lg "> 2nd Approved <span class="fa fa-hand-pointer-o"></span></a>
                                                </c:if>
                                                <!-- Button trigger modal -->
                                                <button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
                                                    Reject <i class="fa fa-reply"></i>
                                                </button>

                                                <!-- Modal -->
                                                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                                    <div class="modal-dialog" role="document">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                                <h4 class="modal-title" id="myModalLabel">Reject Message</h4>
                                                            </div>
                                                            <div class="modal-body">
                                                                <form action="${pageContext.request.contextPath}/reject/secondApprove" method="GET">
                                                                    <div class="form-group">
                                                                        <input type="hidden" class="form-control" id="id" name="id" value="${ticketHTraining.ticketHeader.ticketNo}">
                                                                        <label for="remark" class="control-label">Message:</label>
                                                                        <textarea class="form-control" id="remark" name="remark"></textarea>
                                                                    </div>
                                                                    <button type="submit" class="btn btn-primary">SUBMIT</button>
                                                                </form>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>

                                        </div>
                                    </div>


                                </c:if>

                            </div>



                        </div>
                    </div>
                </div>
            </fieldset>
        </div>




        <%@ include file="template/footer.jspf" %>
        <%@ include file="template/scripts.jspf" %>

    </body>
</html>
