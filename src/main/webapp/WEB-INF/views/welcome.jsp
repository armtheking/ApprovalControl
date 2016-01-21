<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
    <head>
        <%@ include file="template/head.jspf" %>
    </head>
    <body>

        <%@ include file="template/pageTitle.jspf" %>

        <div class="container">
            ${search}
            <div class="row">
                <!-- Table Condensed -->
                <div class="panel panel-success">
                    <table class="table table-striped" id="example">
                        <div class="panel-heading">
                            <h3 class="panel-title">List of your approval request.</h3>
                        </div>


                        <thead>
                            <tr>
                                <th width="10px"> # </th>
                                <th width="100px"> Ticket No </th>
                                <th width="80px"> Date </th>
                                <th> Topic </th>
                                <th width="110px"> Application By </th>
                                <th width="110px"> 1st Approve </th>
                                <th width="110px"> 2nd Approve </th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="ticketHeader" items="${ticketHeaderList}">

                                <!-- Show specifically person permission-->
                                <c:if test="${(fn:contains(ticketHeader.approvedName1, principal.username)) or (fn:contains(ticketHeader.approvedName2, principal.username))}"> 

                                    <tr>

                                        <td>
                                            <c:if test="${ticketHeader.ticketType eq 'ENT'}">
                                                <a href="<c:url value='createticket/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger">show</a>
                                            </c:if>
                                            <c:if test="${ticketHeader.ticketType eq 'TRN'}">
                                                <a href="<c:url value='training/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger">show</a>
                                            </c:if>
                                            <c:if test="${ticketHeader.ticketType eq 'ADV'}">
                                                <a href="<c:url value='advance/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger">show</a>
                                            </c:if>
                                        </td>
                                        <td>${ticketHeader.ticketNo}</td>
                                        <td>${ticketHeader.applicationDate}</td>
                                        <td>${ticketHeader.detailHeader}</td>
                                        <td>${ticketHeader.applicationName}</td>
                                        <td><c:if test="${not ticketHeader.approvedStatus1}">
                                                <a href="#" data-toggle="tooltip" title="${ticketHeader.approvedName1}!">Waiting</a>
                                            </c:if>
                                            <c:if test="${ticketHeader.approvedStatus1}">

                                                ${ticketHeader.approvedName1}
                                            </c:if>
                                        </td>
                                        <td><c:if test="${not ticketHeader.approvedStatus2}">
                                                <a href="#" data-toggle="tooltip" title="${ticketHeader.approvedName2}!">Waiting</a>
                                            </c:if>

                                            <c:if test="${ticketHeader.approvedStatus2}">
                                                ${ticketHeader.approvedName2}
                                            </c:if>
                                        </td>



                                    </tr>

                                </c:if>

                            </c:forEach>
                        </tbody>

                    </table>
                </div>


                <div class="panel panel-primary">
                    <table class="table table-striped" id="example">
                        <div class="panel-heading">
                            <h3 class="panel-title">Your current request.</h3>
                        </div>


                        <thead>
                            <tr>
                                <th width="10px"> # </th>
                                <th width="100px"> Ticket No </th>
                                <th width="80px"> Date </th>
                                <th width="220px"> Topic </th>
                                <th width="110px"> Application By </th>
                                <th width="110px"> 1st Approve </th>
                                <th width="110px"> 2nd Approve </th>
                                <th width="70px"> Status </th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="ticketHeader" items="${ticketHeaderListBelowTable}">
                                <tr>
                                    <td><c:if test="${ticketHeader.ticketType eq 'ENT'}">
                                            <a href="<c:url value='createticket/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger">show</a>
                                        </c:if>
                                        <c:if test="${ticketHeader.ticketType eq 'TRN'}">
                                            <a href="<c:url value='training/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger">show</a>
                                        </c:if>
                                        <c:if test="${ticketHeader.ticketType eq 'ADV'}">
                                            <a href="<c:url value='advance/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger">show</a>
                                        </c:if>    
                                    </td>
                                    <td>${ticketHeader.ticketNo}</td>
                                    <td>${ticketHeader.applicationDate}</td>
                                    <td>${ticketHeader.detailHeader}</td>
                                    <td>${ticketHeader.applicationName}</td>
                                    <td><c:if test="${not ticketHeader.approvedStatus1}">
                                            <a href="#" data-toggle="tooltip" title="${ticketHeader.approvedName1}!">Waiting</a>
                                        </c:if>
                                        <c:if test="${ticketHeader.approvedStatus1}">

                                            ${ticketHeader.approvedName1}
                                        </c:if>
                                    </td>
                                    <td><c:if test="${not ticketHeader.approvedStatus2}">
                                            <a href="#" data-toggle="tooltip" title="${ticketHeader.approvedName2}!">Waiting</a>
                                        </c:if>
                                        <c:if test="${ticketHeader.approvedStatus2}">

                                            ${ticketHeader.approvedName2}
                                        </c:if></td>

                                    <td>

                                        <c:if test="${ticketHeader.ticketType eq 'TRN' and ticketHeader.ticketFinished eq 'P'}">

                                            <a href="<c:url value='training/print?id=${ticketHeader.ticketNo}'/>" class="btn btn-info btn-sm " target="_blank">Print</a> 
                                        </c:if>

                                        <c:if test="${ticketHeader.ticketFinished eq 'P' and !fn:contains(ticketHeader.ticketNo, 'C') and ticketHeader.ticketType eq 'ENT'}"> 
                                            <a href="<c:url value='/report/download/pdf?id=${ticketHeader.ticketNo}'/>" class="btn btn-info btn-sm " target="_blank"> Print <span class="fa fa-hand-pointer-o"></span></a>
                                        </c:if>

                                        <c:if test="${ticketHeader.ticketFinished eq 'P' and fn:contains(ticketHeader.ticketNo, 'C') and ticketHeader.ticketType eq 'ENT'}"> 
                                            <a href="<c:url value='/report/download/pdfEntertainC?id=${ticketHeader.ticketNo}'/>" class="btn btn-info btn-sm " target="_blank"> Print <span class="fa fa-hand-pointer-o"></span></a>
                                        </c:if>
                                            
                                        <c:if test="${ticketHeader.ticketFinished eq 'P' and !fn:contains(ticketHeader.ticketNo, 'C') and ticketHeader.ticketType eq 'ADV'}"> 
                                            <a href="<c:url value='/report/download/pdf_advance?id=${ticketHeader.ticketNo}'/>" class="btn btn-info btn-sm " target="_blank"> Print <span class="fa fa-hand-pointer-o"></span></a>
                                        </c:if>

                                        <c:if test="${ticketHeader.ticketFinished eq 'P' and fn:contains(ticketHeader.ticketNo, 'C') and ticketHeader.ticketType eq 'ADV'}"> 
                                            <a href="<c:url value='/report/download/pdf_advanceC?id=${ticketHeader.ticketNo}'/>" class="btn btn-info btn-sm " target="_blank"> Print <span class="fa fa-hand-pointer-o"></span></a>
                                        </c:if>    

                                        <c:if test="${ticketHeader.ticketFinished eq 'W'}"> 
                                            <a href="<c:url value='/clearticket?id=${ticketHeader.ticketNo}'/>" class="btn btn-success btn-sm "> Clear <span class="fa fa-hand-pointer-o"></span></a>
                                            </c:if>

                                        <c:if test="${ticketHeader.ticketFinished eq 'R'}"> 
                                            <font style="color: red;font-weight: bold"> Reject </font> 
                                        </c:if>

                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>

                    </table>
                </div>
            </div>
        </div>





        <%@ include file="template/footer.jspf" %>
        <%@ include file="template/scripts.jspf" %>

    </body>
</html>