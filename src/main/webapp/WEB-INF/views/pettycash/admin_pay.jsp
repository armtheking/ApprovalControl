<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
    <head>

        <%@ include file="../template/head.jspf" %>

    </head>
    <body>

        <%@ include file="../template/pageTitle.jspf" %>

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

                            </div>

                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h4>Admin Pay</h4>
                            </div>
                            <div class="panel-body">

                                <form:form class="form-horizontal" action="adminPaySuccess?${_csrf.parameterName}=${_csrf.token}" method="post" modelAttribute="ticketHeader" enctype="multipart/form-data">

                                    <input type="hidden"  name="areaTab" value="${areaTab}">
                                    <div class="form-group">
                                        <input type="hidden" name="ticketNo" value="${ticketHeader.ticketNo}" id="ticketNo"/>
                                    </div>
                                    <div class="form-group">
                                        <label  class="col-sm-3 control-label">Request Total Amount : </label>

                                        <div class="col-sm-5"  style="margin-top:5px">
                                            <ins><b>${number_sumAmount}</b></ins>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="receiverBy" class="col-sm-3 control-label">Amount : </label>
                                        <div class="col-sm-4">

                                            <input type="text"  id="paidByAdmin" name="paidByAdmin" class="form-control" placeholder="Amount" autofocus="autofocus"/>

                                        </div>
                                    </div>



                                    <div class="row">
                                        <div class="col-md-8 col-md-offset-5">
                                            <button type="submit" class="btn btn-primary btn-lg">Paid</button>
                                        </div>
                                    </div>

                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>                             




            </fieldset>
        </div>


        <%@ include file="../template/footer.jspf" %>
        <%@ include file="../template/scripts.jspf" %>

    </body>
</html>
