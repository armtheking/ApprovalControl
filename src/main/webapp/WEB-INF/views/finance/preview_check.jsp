<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
    <head>
        <%@ include file="../template/head.jspf" %>

    </head>
    <body>

        <%@ include file="../template/pageTitle.jspf" %>

        <div class="container">

            <fieldset>
                <legend>Show Detail</legend>
                <!-- Row 2 -->
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-danger">
                            <div class="panel-heading">
                                <h3>Ticket Number : ${ticketHeader.ticketNo}</h3>
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <label> Topic </label>
                                        <div class="well">
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
                                        <label> Reason </label>
                                        <div class="well">
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
                                    <div class="col-md-12">
                                        <div class="panel panel-success">
                                            <div class="panel-heading">
                                                <h4>Detail</h4>
                                            </div>
                                            <div class="panel-body">
                                                <table class="table table-striped">
                                                    <thead>
                                                        <tr>
                                                            <th class="col-md-1"> No </th>
                                                            <th class="col-md-4"> Description </th>
                                                            <th class="col-md-2"> Place </th>
                                                            <th class="col-md-4"> Detail </th>
                                                            <th class="col-md-1"> Amount </th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:set var="count" value="0" scope="page" />
                                                        <c:forEach var="ticketdetail" items="${ticketHeader.ticketdetail}">
                                                            <tr>
                                                                <td><c:set var="count" value="${count + 1}" scope="page"/> 
                                                                    <c:out value="${count}" />
                                                                </td>
                                                                <td>${ticketdetail.financeChargeCode.description}</td>
                                                                <td>${ticketdetail.place}</td>
                                                                <td>
                                                                    ${ticketdetail.detail}
                                                                </td>
                                                                <td>
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

                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="panel panel-warning">
                                            <div class="panel-heading">
                                                <h4>Status Approve</h4>
                                            </div>
                                            <div class="panel-body">

                                                <div class="row">
                                                    <div class="col-md-5">
                                                        <label> 1st Approve </label>
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
                                                            ${ticketHeader.approvedRemark1}
                                                        </div>
                                                    </div>

                                                </div>


                                                <div class="row">
                                                    <div class="col-md-5">
                                                        <label> 2nd Approve </label>
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
                                                            ${ticketHeader.approvedRemark2}
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
                                                <h4>Finance Approve</h4>
                                            </div>
                                            <div class="panel-body">

                                                <form:form class="form-horizontal" action="approve?${_csrf.parameterName}=${_csrf.token}" method="post" modelAttribute="ticketHeader" enctype="multipart/form-data">


                                                    <div class="form-group">
                                                        <input type="hidden" name="ticketNo" value="${ticketHeader.ticketNo}" id="ticketNo"/>

                                                        <label class="col-sm-12">Receiver && Paid By  </label>

                                                    </div>
                                                    <div class="form-group">
                                                        <label  class="col-sm-3 control-label">Request Total Amount : </label>

                                                        <div class="col-sm-5">
                                                            <ins><b>${ticketHeader.reqTotalAmt}</b></ins>
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label for="receiverBy" class="col-sm-3 control-label">Receiver By : </label>
                                                        <div class="col-sm-4">

                                                            <input type="text"  id="receiverBy" name="receiverBy" class="form-control" placeholder="Receiver By" autofocus="autofocus"/>

                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label for="payment" class="col-sm-3 control-label">Payment : </label>
                                                        <div class="col-sm-4">

                                                            <select id="payment" name="payment">
                                                                <option value="Cash">Cash</option>
                                                                <option value="Cheque">Cheque / Credit Card</option>
                                                            </select> 
                                                        </div>
                                                    </div>


                                                    <div class="form-group">
                                                        <label for="paidRemark" class="col-sm-3 control-label">Paid Note : </label>
                                                        <div class="col-sm-4">

                                                            <textarea class="form-control" rows="2" id="paidRemark" name="paidRemark"></textarea>
                                                        </div>
                                                    </div>

                                                    <div class="row">
                                                        <div class="col-md-8 col-md-offset-5">
                                                            <button type="submit" class="btn btn-primary btn-lg">SAVE</button>
                                                        </div>
                                                    </div>

                                                </form:form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class = "panel-footer">

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
