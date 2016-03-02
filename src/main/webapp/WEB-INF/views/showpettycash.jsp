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
                <legend>Details</legend>

                <!-- Row 2 -->
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-danger">

                            <div class="panel-heading clearfix">
                                <h3 class="panel-title pull-left" style="padding-top: 15px; font-weight: bold;">Ticket Number : <c:if test="${empty ticketHeader.showTicket}">
                                        ${ticketHeader.ticketNo}
                                    </c:if>
                                    <c:if test="${not empty ticketHeader.showTicket}">
                                        ${ticketHeader.showTicket}
                                        &nbsp;<a href="<c:url value='../showRevise?id=${ticketHeader.ticketNo}'/>" style="font-weight: bold; color: #FFFFFF;" class="btn btn-danger" target="_blank"> <i class="fa fa-search"> View</i></a>               
                                    </c:if></h3>
                                <div class="btn-group pull-right">                             
                                    <c:if test="${principal.username eq ticketHeader.applicationName and ticketHeader.approvedStatus1 eq false or ticketHeader.ticketFinished eq 'R'}"> 
                                        <a href="<c:url value='/pettycash/edit?id=${ticketHeader.ticketNo}'/>" class="btn btn-primary btn-lg"><i class="fa fa-pencil-square-o"></i> Edit </a>
                                    </c:if>
                                </div>

                            </div>

                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <label> Objective / Reason </label>
                                        <div class="well" style="width:540px; word-wrap: break-word; display:block;">
                                            ${ticketHeader.detailHeader}
                                        </div>
                                    </div>

                                    <div class="col-md-3">
                                        <label> Application Name </label>
                                        <div class="well">
                                            ${ticketHeader.applicationName}
                                        </div>
                                    </div>
                                    <div class="col-md-2">
                                        <label> Application Date </label>
                                        <div class="well">
                                            ${ticketHeader.applicationDate}
                                        </div>
                                    </div>

                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <label> Remark </label>
                                        <div class="well" style="width:540px; word-wrap: break-word; display:block;">
                                            <c:if test="${ticketHeader.remarkHeader eq null or ticketHeader.remarkHeader eq ''}">
                                                -
                                            </c:if>
                                            ${ticketHeader.remarkHeader}
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <c:set var="theTicketNo" value="${ticketHeader.ticketNo}"/>
                                        <c:if test="${fn:contains(theTicketNo, 'C') or fn:contains(theTicketNo, 'PTC')}" >
                                            <label> Attachments </label>
                                            <div class="well">
                                                <a href="<c:url value='/download?id=${ticketHeader.ticketNo}' />" target="_blank">
                                                    <img border="0" alt="${ticketHeader.ticketNo}" src="<c:url value='/resources/img/pdf.png' />" width="35" height="32">

                                                </a>
                                                << View attach file
                                            </div>
                                        </c:if>

                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-2">
                                        <label> Settlement Within </label>
                                        <div class="well">
                                            ${ticketHeader.dateAlert} Days
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
                                                            <th class="col-md-4"> Description </th>
                                                            <th class="col-md-4"> Details </th>
                                                            <th class="col-md-2"> Receipt No </th>
                                                            <th class="col-md-1"> Amount </th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:set var="count" value="0" scope="page" />
                                                        <c:forEach var="ticketdetail" items="${ticketDetail}">
                                                            <tr>
                                                                <td><c:set var="count" value="${count + 1}" scope="page"/> 
                                                                    <c:out value="${count}" />
                                                                </td>
                                                                <td>${ticketdetail.description}</td>
                                                                <td style="max-width: 360px; word-wrap: break-word;">
                                                                    ${ticketdetail.detail}
                                                                </td>
                                                                <td>
                                                                    ${ticketdetail.receiptNo}
                                                                </td>
                                                                <td align="right">
                                                                    ${ticketdetail.amount}
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

                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="panel panel-warning">
                                            <div class="panel-heading">
                                                <h4 style=" font-weight: bold;">Approval Status</h4>
                                            </div>
                                            <div class="panel-body">

                                                <div class="row">
                                                    <div class="col-md-5">
                                                        <label> 1st Approval </label>
                                                        <div class="well well-sm">
                                                            ${ticketHeader.approvedName1}
                                                        </div>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label> Date </label>
                                                        <div class="well well-sm">
                                                            ${ticketHeader.approvedDate1}
                                                        </div>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <label> Note </label>
                                                        <div class="well well-sm">
                                                            <c:if test="${ticketHeader.approvedRemark1 eq null or ticketHeader.approvedRemark1 eq ''}">
                                                                <font style="color: red; font-weight: bold">-</font>
                                                            </c:if>
                                                            <font style="color: red;font-weight: bold"> ${ticketHeader.approvedRemark1} </font> 
                                                        </div>
                                                    </div>

                                                </div>


                                                <div class="row">
                                                    <div class="col-md-5">
                                                        <label> 2nd Approval </label>
                                                        <div class="well well-sm">
                                                            ${ticketHeader.approvedName2}
                                                        </div>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label> Date </label>
                                                        <div class="well well-sm">
                                                            ${ticketHeader.approvedDate2}
                                                        </div>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <label> Note </label>
                                                        <div class="well well-sm">
                                                            <c:if test="${ticketHeader.approvedRemark2 eq null or ticketHeader.approvedRemark2 eq ''}">
                                                                <font style="color: red;font-weight: bold">-</font>
                                                            </c:if>

                                                            <font style="color: red;font-weight: bold"> ${ticketHeader.approvedRemark2} </font> 
                                                        </div>
                                                    </div>

                                                </div>







                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
                                    <c:if test="${ticketHeader.ticketFinished eq 'F'}">
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
                                                                    ${ticketHeader.receiverBy}
                                                                </div>
                                                            </div>
                                                            <div class="col-md-4">
                                                                <label> Paid By: </label>
                                                                <div class="well well-sm">
                                                                    ${ticketHeader.paidBy}
                                                                </div>
                                                            </div>
                                                            <div class="col-md-4">
                                                                <label> Paid Date: </label>
                                                                <div class="well well-sm">
                                                                    ${ticketHeader.paidDate}
                                                                </div>
                                                            </div> 
                                                        </div>  
                                                        <div class="row">
                                                            <div class="col-md-4">
                                                                <label> Request Total Amount: </label>
                                                                <div class="well well-sm">
                                                                    ${ticketHeader.reqTotalAmt + ticketHeader.payBack}
                                                                </div>
                                                            </div>

                                                            <div class="col-md-4">
                                                                <label> Paid Total Amount: </label>
                                                                <div class="well well-sm">
                                                                    ${ticketHeader.reqTotalAmt}
                                                                </div>
                                                            </div>
                                                            <div class="col-md-4">
                                                                <label> Balance / Refund </label>
                                                                <div class="well well-sm">
                                                                    <c:if test="${ticketHeader.payBack eq null}">
                                                                        0.00
                                                                    </c:if>
                                                                    <c:if test="${ticketHeader.payBack >= 0}">
                                                                        ${ticketHeader.payBack}
                                                                    </c:if>
                                                                </div>
                                                            </div> 
                                                        </div>              
                                                        <div class="row">
                                                            <div class="col-md-4">
                                                                <label> Payment: </label>
                                                                <div class="well well-sm">
                                                                    <c:if test="${ticketHeader.payBack eq null or ticketHeader.payBack eq ''}">
                                                                        -
                                                                    </c:if>
                                                                    ${ticketHeader.payBack}
                                                                </div>
                                                            </div>
                                                            <div class="col-md-8">
                                                                <label> Paid Note: </label>
                                                                <div class="well well-sm" style="width:706 px; word-wrap: break-word; display:block;">
                                                                    <c:if test="${ticketHeader.paidRemark eq null or ticketHeader.paidRemark eq ''}">
                                                                        -
                                                                    </c:if>
                                                                    ${ticketHeader.paidRemark}
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
                                <c:if test="${principal.username eq ticketHeader.applicationName and ticketHeader.approvedStatus1 eq false or (ticketHeader.ticketFinished eq 'R' and principal.username eq ticketHeader.applicationName )}"> 

                                    <a style="margin-left: 81%" href="<c:url value='/createticket/delete?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger btn-delete btn-lg" onclick="return confirm('Are you sure?')"><i class="fa fa-trash"></i> Delete</a>&nbsp;
                                </c:if>
                                <c:if test="${(fn:contains(ticketHeader.approvedName1, principal.username)) or (fn:contains(ticketHeader.approvedName2, principal.username))}"> 
                                    <div class="row">
                                        <div class="col-md-12" style="text-align: center">

                                            <c:if test="${ticketHeader.ticketFinished eq '0' and (fn:contains(ticketHeader.approvedName1, principal.username)) or ticketHeader.ticketFinished eq 'C' and (fn:contains(ticketHeader.approvedName1, principal.username))}"> 
                                                <a href="<c:url value='/approve/personone?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger btn-lg "> 1st Approved <span class="fa fa-hand-pointer-o"></span></a>
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
                                                                        <input type="hidden" class="form-control" id="id" name="id" value="${ticketHeader.ticketNo}">
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

                                            <c:if test="${(ticketHeader.ticketFinished eq '1' or ticketHeader.ticketFinished eq 'P') and (fn:contains(ticketHeader.approvedName2, principal.username))}"> 
                                                <c:if test="${ticketHeader.ticketFinished eq '1'}">
                                                    <a href="<c:url value='/approve/persontwo?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger btn-lg "> 2nd Approved <span class="fa fa-hand-pointer-o"></span></a>
                                                </c:if> <!-- Button trigger modal -->
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
                                                                        <input type="hidden" class="form-control" id="id" name="id" value="${ticketHeader.ticketNo}">
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
