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
                <legend><i class="fa fa-btc"></i> Finance (Advance)</legend>
            </fieldset>
            ${search}
            <ul class="nav nav-tabs">
                <li <c:if test="${areaTab eq 'all'}">class="active"</c:if>>
                        <a  href="#1" data-toggle="tab">ALL</a>
                    </li>
                    <li <c:if test="${areaTab eq 'thaniya'}">class="active"</c:if>>
                        <a href="#2" data-toggle="tab">THANIYA</a>
                    </li>
                    <li <c:if test="${areaTab eq 'blc'}">class="active"</c:if>>
                        <a href="#3" data-toggle="tab">BLC</a>
                    </li>
                    <li <c:if test="${areaTab eq 'nlc'}">class="active"</c:if>>
                        <a href="#4" data-toggle="tab">NLC</a>
                    </li>
                    <li <c:if test="${areaTab eq 'airport'}">class="active"</c:if>>
                        <a href="#5" data-toggle="tab">AIRPORT</a>
                    </li>
                    <li <c:if test="${areaTab eq 'ncc'}">class="active"</c:if>>
                        <a href="#6" data-toggle="tab">NCC</a>
                    </li>
                    <li <c:if test="${areaTab eq 'laemchabang'}">class="active"</c:if>>
                        <a href="#7" data-toggle="tab">LAEMCHABANG</a>
                    </li>
                    <li <c:if test="${areaTab eq 'korat'}">class="active"</c:if>>
                        <a href="#8" data-toggle="tab">KORAT</a>
                    </li>

                </ul>
                <div class="tab-content ">
                    <div class="tab-pane <c:if test="${areaTab eq 'all'}">active</c:if>" id="1"> 
                        </br>
                        <div class="row">
                            <!-- Table Condensed -->
                            <div class="panel panel-success">
                                <table class="table table-striped" id="example">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">Finance Approve</h3>
                                    </div>
                                    <thead>
                                        <tr>
                                            <th style="width: 30px"> # </th>
                                            <th> Ticket No </th>
                                            <th> Date </th>
                                            <th style="width: 200px"> Topic </th>
                                            <th> Application By </th>
                                            <th> 1st Approve </th>
                                            <th> 2nd Approve </th>
                                            <th> Print </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="ticketHeader" items="${ticketHeaderListFinance}">


                                        <tr>
                                            <td> <a href="<c:url value='showAdvance?id=${ticketHeader.ticketNo}&areaTab=all'/>" class="btn btn-danger">show</a></td>
                                            <td><c:if test="${empty ticketHeader.showTicket}">
                                                    ${ticketHeader.ticketNo}
                                                </c:if>
                                                <c:if test="${not empty ticketHeader.showTicket}">
                                                    ${ticketHeader.showTicket}
                                                </c:if></td>
                                            <td>${ticketHeader.applicationDate}</td>
                                            <td style="max-width: 200px; word-wrap: break-word;">${ticketHeader.detailHeader}</td>
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
                                                <c:if test="${ticketHeader.ticketFinished eq 'P' and !fn:contains(ticketHeader.ticketNo, 'C') and ticketHeader.ticketType eq 'ADV'}"> 
                                                    <a href="<c:url value='/report/download/pdf_advance?id=${ticketHeader.ticketNo}'/>" class="btn btn-info btn-sm " target="_blank"> Print <span class="fa fa-hand-pointer-o"></span></a>
                                                    </c:if>

                                                <c:if test="${ticketHeader.ticketFinished eq 'P' and fn:contains(ticketHeader.ticketNo, 'C') and ticketHeader.ticketType eq 'ADV'}"> 
                                                    <a href="<c:url value='/report/download/pdf_advanceC?id=${ticketHeader.ticketNo}'/>" class="btn btn-info btn-sm " target="_blank"> Print <span class="fa fa-hand-pointer-o"></span></a>
                                                </c:if>       </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>

                            </table>
                        </div>

                    </div>
                </div>
                <div class="tab-pane <c:if test="${areaTab eq 'thaniya'}">active</c:if>" id="2"> 
                        </br>
                        <div class="row">
                            <!-- Table Condensed -->
                            <div class="panel panel-success">
                                <table class="table table-striped" id="thaniya">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">Finance Approve</h3>
                                    </div>
                                    <thead>
                                        <tr>
                                            <th style="width: 30px"> # </th>
                                            <th> Ticket No </th>
                                            <th> Date </th>
                                            <th style="width: 200px"> Topic </th>
                                            <th> Application By </th>
                                            <th> 1st Approve </th>
                                            <th> 2nd Approve </th>
                                            <th> Print </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="ticketHeader" items="${THANIYA}">


                                        <tr>
                                            <td> <a href="<c:url value='showAdvance?id=${ticketHeader.ticketNo}&areaTab=thaniya'/>" class="btn btn-danger">show</a></td>
                                            <td><c:if test="${empty ticketHeader.showTicket}">
                                                    ${ticketHeader.ticketNo}
                                                </c:if>
                                                <c:if test="${not empty ticketHeader.showTicket}">
                                                    ${ticketHeader.showTicket}
                                                </c:if></td>
                                            <td>${ticketHeader.applicationDate}</td>
                                            <td style="max-width: 200px; word-wrap: break-word;">${ticketHeader.detailHeader}</td>
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
                                                <c:if test="${ticketHeader.ticketFinished eq 'P' and !fn:contains(ticketHeader.ticketNo, 'C') and ticketHeader.ticketType eq 'ADV'}"> 
                                                    <a href="<c:url value='/report/download/pdf_advance?id=${ticketHeader.ticketNo}'/>" class="btn btn-info btn-sm " target="_blank"> Print <span class="fa fa-hand-pointer-o"></span></a>
                                                    </c:if>

                                                <c:if test="${ticketHeader.ticketFinished eq 'P' and fn:contains(ticketHeader.ticketNo, 'C') and ticketHeader.ticketType eq 'ADV'}"> 
                                                    <a href="<c:url value='/report/download/pdf_advanceC?id=${ticketHeader.ticketNo}'/>" class="btn btn-info btn-sm " target="_blank"> Print <span class="fa fa-hand-pointer-o"></span></a>
                                                </c:if>        </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>

                            </table>
                        </div>

                    </div>
                </div>


                <div class="tab-pane <c:if test="${areaTab eq 'blc'}">active</c:if>" id="3"> 
                        </br>
                        <div class="row">
                            <!-- Table Condensed -->
                            <div class="panel panel-success">
                                <table class="table table-striped" id="blc">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">Finance Approve</h3>
                                    </div>
                                    <thead>
                                        <tr>
                                            <th style="width: 30px"> # </th>
                                            <th> Ticket No </th>
                                            <th> Date </th>
                                            <th style="width: 200px"> Topic </th>
                                            <th> Application By </th>
                                            <th> 1st Approve </th>
                                            <th> 2nd Approve </th>
                                            <th> Print </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="ticketHeader" items="${BLC}">


                                        <tr>
                                            <td> <a href="<c:url value='showAdvance?id=${ticketHeader.ticketNo}&areaTab=blc'/>" class="btn btn-danger">show</a></td>
                                            <td><c:if test="${empty ticketHeader.showTicket}">
                                                    ${ticketHeader.ticketNo}
                                                </c:if>
                                                <c:if test="${not empty ticketHeader.showTicket}">
                                                    ${ticketHeader.showTicket}
                                                </c:if></td>
                                            <td>${ticketHeader.applicationDate}</td>
                                            <td style="max-width: 200px; word-wrap: break-word;">${ticketHeader.detailHeader}</td>
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
                                                <c:if test="${ticketHeader.ticketFinished eq 'P' and !fn:contains(ticketHeader.ticketNo, 'C') and ticketHeader.ticketType eq 'ADV'}"> 
                                                    <a href="<c:url value='/report/download/pdf_advance?id=${ticketHeader.ticketNo}'/>" class="btn btn-info btn-sm " target="_blank"> Print <span class="fa fa-hand-pointer-o"></span></a>
                                                    </c:if>

                                                <c:if test="${ticketHeader.ticketFinished eq 'P' and fn:contains(ticketHeader.ticketNo, 'C') and ticketHeader.ticketType eq 'ADV'}"> 
                                                    <a href="<c:url value='/report/download/pdf_advanceC?id=${ticketHeader.ticketNo}'/>" class="btn btn-info btn-sm " target="_blank"> Print <span class="fa fa-hand-pointer-o"></span></a>
                                                </c:if>       </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>

                            </table>
                        </div>

                    </div>
                </div>

                <div class="tab-pane <c:if test="${areaTab eq 'nlc'}">active</c:if>" id="4"> 
                        </br>

                        <div class="row">
                            <!-- Table Condensed -->
                            <div class="panel panel-success">
                                <table class="table table-striped" id="nlc">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">Finance Approve</h3>
                                    </div>
                                    <thead>
                                        <tr>
                                            <th style="width: 30px"> # </th>
                                            <th> Ticket No </th>
                                            <th> Date </th>
                                            <th style="width: 200px"> Topic </th>
                                            <th> Application By </th>
                                            <th> 1st Approve </th>
                                            <th> 2nd Approve </th>
                                            <th> Print </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="ticketHeader" items="${NLC}">
                                        <tr>
                                            <td> <a href="<c:url value='showAdvance?id=${ticketHeader.ticketNo}&areaTab=nlc'/>" class="btn btn-danger">show</a></td>
                                            <td><c:if test="${empty ticketHeader.showTicket}">
                                                    ${ticketHeader.ticketNo}
                                                </c:if>
                                                <c:if test="${not empty ticketHeader.showTicket}">
                                                    ${ticketHeader.showTicket}
                                                </c:if></td>
                                            <td>${ticketHeader.applicationDate}</td>
                                            <td style="max-width: 200px; word-wrap: break-word;">${ticketHeader.detailHeader}</td>
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
                                                <c:if test="${ticketHeader.ticketFinished eq 'P' and !fn:contains(ticketHeader.ticketNo, 'C') and ticketHeader.ticketType eq 'ADV'}"> 
                                                    <a href="<c:url value='/report/download/pdf_advance?id=${ticketHeader.ticketNo}'/>" class="btn btn-info btn-sm " target="_blank"> Print <span class="fa fa-hand-pointer-o"></span></a>
                                                    </c:if>

                                                <c:if test="${ticketHeader.ticketFinished eq 'P' and fn:contains(ticketHeader.ticketNo, 'C') and ticketHeader.ticketType eq 'ADV'}"> 
                                                    <a href="<c:url value='/report/download/pdf_advanceC?id=${ticketHeader.ticketNo}'/>" class="btn btn-info btn-sm " target="_blank"> Print <span class="fa fa-hand-pointer-o"></span></a>
                                                </c:if>       </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>

                            </table>
                        </div>

                    </div>
                </div>

                <div class="tab-pane <c:if test="${areaTab eq 'airport'}">active</c:if>" id="5"> 
                        </br>
                        <div class="row">
                            <!-- Table Condensed -->
                            <div class="panel panel-success">
                                <table class="table table-striped" id="airport">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">Finance Approve</h3>
                                    </div>
                                    <thead>
                                        <tr>
                                            <th style="width: 30px"> # </th>
                                            <th> Ticket No </th>
                                            <th> Date </th>
                                            <th style="width: 200px"> Topic </th>
                                            <th> Application By </th>
                                            <th> 1st Approve </th>
                                            <th> 2nd Approve </th>
                                            <th> Print </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="ticketHeader" items="${AIRPORT}">


                                        <tr>
                                            <td> <a href="<c:url value='showAdvance?id=${ticketHeader.ticketNo}&areaTab=airport'/>" class="btn btn-danger">show</a></td>
                                            <td><c:if test="${empty ticketHeader.showTicket}">
                                                    ${ticketHeader.ticketNo}
                                                </c:if>
                                                <c:if test="${not empty ticketHeader.showTicket}">
                                                    ${ticketHeader.showTicket}
                                                </c:if></td>
                                            <td>${ticketHeader.applicationDate}</td>
                                            <td style="max-width: 200px; word-wrap: break-word;">${ticketHeader.detailHeader}</td>
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
                                                <c:if test="${ticketHeader.ticketFinished eq 'P' and !fn:contains(ticketHeader.ticketNo, 'C') and ticketHeader.ticketType eq 'ADV'}"> 
                                                    <a href="<c:url value='/report/download/pdf_advance?id=${ticketHeader.ticketNo}'/>" class="btn btn-info btn-sm " target="_blank"> Print <span class="fa fa-hand-pointer-o"></span></a>
                                                    </c:if>

                                                <c:if test="${ticketHeader.ticketFinished eq 'P' and fn:contains(ticketHeader.ticketNo, 'C') and ticketHeader.ticketType eq 'ADV'}"> 
                                                    <a href="<c:url value='/report/download/pdf_advanceC?id=${ticketHeader.ticketNo}'/>" class="btn btn-info btn-sm " target="_blank"> Print <span class="fa fa-hand-pointer-o"></span></a>
                                                </c:if>     </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>

                            </table>
                        </div>

                    </div>
                </div>


                <div class="tab-pane <c:if test="${areaTab eq 'ncc'}">active</c:if>" id="6"> 
                        </br>
                        <div class="row">
                            <!-- Table Condensed -->
                            <div class="panel panel-success">
                                <table class="table table-striped" id="ncc">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">Finance Approve</h3>
                                    </div>
                                    <thead>
                                        <tr>
                                            <th style="width: 30px"> # </th>
                                            <th> Ticket No </th>
                                            <th> Date </th>
                                            <th style="width: 200px"> Topic </th>
                                            <th> Application By </th>
                                            <th> 1st Approve </th>
                                            <th> 2nd Approve </th>
                                            <th> Print </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="ticketHeader" items="${NCC}">


                                        <tr>
                                            <td> <a href="<c:url value='showAdvance?id=${ticketHeader.ticketNo}&areaTab=ncc'/>" class="btn btn-danger">show</a></td>
                                            <td><c:if test="${empty ticketHeader.showTicket}">
                                                    ${ticketHeader.ticketNo}
                                                </c:if>
                                                <c:if test="${not empty ticketHeader.showTicket}">
                                                    ${ticketHeader.showTicket}
                                                </c:if></td>
                                            <td>${ticketHeader.applicationDate}</td>
                                            <td style="max-width: 200px; word-wrap: break-word;">${ticketHeader.detailHeader}</td>
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
                                                <c:if test="${ticketHeader.ticketFinished eq 'P' and !fn:contains(ticketHeader.ticketNo, 'C') and ticketHeader.ticketType eq 'ADV'}"> 
                                                    <a href="<c:url value='/report/download/pdf_advance?id=${ticketHeader.ticketNo}'/>" class="btn btn-info btn-sm " target="_blank"> Print <span class="fa fa-hand-pointer-o"></span></a>
                                                    </c:if>

                                                <c:if test="${ticketHeader.ticketFinished eq 'P' and fn:contains(ticketHeader.ticketNo, 'C') and ticketHeader.ticketType eq 'ADV'}"> 
                                                    <a href="<c:url value='/report/download/pdf_advanceC?id=${ticketHeader.ticketNo}'/>" class="btn btn-info btn-sm " target="_blank"> Print <span class="fa fa-hand-pointer-o"></span></a>
                                                </c:if>      </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>

                            </table>
                        </div>

                    </div>
                </div>


                <div class="tab-pane <c:if test="${areaTab eq 'laemchabang'}">active</c:if>" id="7"> 
                        </br>
                        <div class="row">
                            <!-- Table Condensed -->
                            <div class="panel panel-success">
                                <table class="table table-striped" id="laemchabang">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">Finance Approve</h3>
                                    </div>
                                    <thead>
                                        <tr>
                                            <th style="width: 30px"> # </th>
                                            <th> Ticket No </th>
                                            <th> Date </th>
                                            <th style="width: 200px"> Topic </th>
                                            <th> Application By </th>
                                            <th> 1st Approve </th>
                                            <th> 2nd Approve </th>
                                            <th> Print </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="ticketHeader" items="${LAEMCHABANG}">


                                        <tr>
                                            <td> <a href="<c:url value='showAdvance?id=${ticketHeader.ticketNo}&areaTab=laemchabang'/>" class="btn btn-danger">show</a></td>
                                            <td><c:if test="${empty ticketHeader.showTicket}">
                                                    ${ticketHeader.ticketNo}
                                                </c:if>
                                                <c:if test="${not empty ticketHeader.showTicket}">
                                                    ${ticketHeader.showTicket}
                                                </c:if></td>
                                            <td>${ticketHeader.applicationDate}</td>
                                            <td style="max-width: 200px; word-wrap: break-word;">${ticketHeader.detailHeader}</td>
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
                                                <c:if test="${ticketHeader.ticketFinished eq 'P' and !fn:contains(ticketHeader.ticketNo, 'C') and ticketHeader.ticketType eq 'ADV'}"> 
                                                    <a href="<c:url value='/report/download/pdf_advance?id=${ticketHeader.ticketNo}'/>" class="btn btn-info btn-sm " target="_blank"> Print <span class="fa fa-hand-pointer-o"></span></a>
                                                    </c:if>

                                                <c:if test="${ticketHeader.ticketFinished eq 'P' and fn:contains(ticketHeader.ticketNo, 'C') and ticketHeader.ticketType eq 'ADV'}"> 
                                                    <a href="<c:url value='/report/download/pdf_advanceC?id=${ticketHeader.ticketNo}'/>" class="btn btn-info btn-sm " target="_blank"> Print <span class="fa fa-hand-pointer-o"></span></a>
                                                </c:if>      </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>

                            </table>
                        </div>
                    </div>
                </div>
                <div class="tab-pane <c:if test="${areaTab eq 'korat'}">active</c:if>" id="8"> 
                        </br>
                        <div class="row">
                            <!-- Table Condensed -->
                            <div class="panel panel-success">
                                <table class="table table-striped" id="korat">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">Finance Approve</h3>
                                    </div>
                                    <thead>
                                        <tr>
                                            <th style="width: 50px"> # </th>
                                            <th> Ticket No </th>
                                            <th> Date </th>
                                            <th style="width: 200px"> Topic </th>
                                            <th> Application By </th>
                                            <th> 1st Approve </th>
                                            <th> 2nd Approve </th>
                                            <th> Print </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="ticketHeader" items="${KORAT}">


                                        <tr>
                                            <td> <a href="<c:url value='showAdvance?id=${ticketHeader.ticketNo}&areaTab=korat'/>" class="btn btn-danger">show</a></td>
                                            <td><c:if test="${empty ticketHeader.showTicket}">
                                                    ${ticketHeader.ticketNo}
                                                </c:if>
                                                <c:if test="${not empty ticketHeader.showTicket}">
                                                    ${ticketHeader.showTicket}
                                                </c:if></td>
                                            <td>${ticketHeader.applicationDate}</td>
                                            <td style="max-width: 200px; word-wrap: break-word;">${ticketHeader.detailHeader}</td>
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
                                                <c:if test="${ticketHeader.ticketFinished eq 'P' and !fn:contains(ticketHeader.ticketNo, 'C') and ticketHeader.ticketType eq 'ADV'}"> 
                                                    <a href="<c:url value='/report/download/pdf_advance?id=${ticketHeader.ticketNo}'/>" class="btn btn-info btn-sm " target="_blank"> Print <span class="fa fa-hand-pointer-o"></span></a>
                                                    </c:if>

                                                <c:if test="${ticketHeader.ticketFinished eq 'P' and fn:contains(ticketHeader.ticketNo, 'C') and ticketHeader.ticketType eq 'ADV'}"> 
                                                    <a href="<c:url value='/report/download/pdf_advanceC?id=${ticketHeader.ticketNo}'/>" class="btn btn-info btn-sm " target="_blank"> Print <span class="fa fa-hand-pointer-o"></span></a>
                                                </c:if>      </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>

                            </table>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jspf" %>
        <%@ include file="../template/scripts.jspf" %>

    </body>
</html>
