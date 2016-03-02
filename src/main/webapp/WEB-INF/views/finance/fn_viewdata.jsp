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

        <div class="container-fluid">
            <fieldset>
                <legend><i class="fa fa-btc"></i> Finance (View Data)</legend>
            </fieldset>


            ${search}

            <ul class="nav nav-tabs">
                <li class="active">
                    <a  href="#1" data-toggle="tab">ALL</a>
                </li>
                <li><a href="#2" data-toggle="tab">THANIYA</a>
                </li>
                <li><a href="#3" data-toggle="tab">BLC</a>
                </li>
                <li><a href="#4" data-toggle="tab">NLC</a>
                </li>
                <li><a href="#5" data-toggle="tab">AIRPORT</a>
                </li>
                <li><a href="#6" data-toggle="tab">NCC</a>
                </li>
                <li><a href="#7" data-toggle="tab">LAEMCHABANG</a>
                </li>
                <li><a href="#8" data-toggle="tab">KORAT</a>
                </li>

            </ul>
            <div class="tab-content ">
                <div class="tab-pane active" id="1"> 
                    </br>
                    <div class="row">
                        <!-- Table Condensed -->
                        <div class="panel panel-success">
                            <table class="table table-striped" id="all">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Finance Approve</h3>
                                </div>
                                <thead>
                                    <tr>
                                        <th width="10px"> # </th>
                                        <th width="140px"> Ticket No </th>
                                        <th width="80px"> Date </th>
                                        <th width="200px"> Topic </th>
                                        <th width="120px"> Application By </th>
                                        <th width="110px"> 1st Approve </th>
                                        <th width="110px"> 2nd Approve </th>
                                        <th width="20px"> Payment </th>
                                        <th width="110px"> Paid By </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="ticketHeader" items="${ticketHeaderListFinance}">
                                        <tr>
                                            <td>
                                                <c:if test="${ticketHeader.ticketType eq 'ENT'}">
                                                    <a href="<c:url value='/createticket/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'TRN'}">
                                                    <a href="<c:url value='/training/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'ADV'}">
                                                    <a href="<c:url value='/advance/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'PTC'}">
                                                    <a href="<c:url value='/pettycash/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                            </td>
                                            <td><c:if test="${empty ticketHeader.showTicket}">
                                                    ${ticketHeader.ticketNo}
                                                </c:if>
                                                <c:if test="${not empty ticketHeader.showTicket}">
                                                    ${ticketHeader.showTicket}
                                                </c:if></td>
                                            <td>${ticketHeader.applicationDate}</td>
                                            <td style="max-width: 200px; word-wrap: break-word;">${ticketHeader.detailHeader}</td>
                                            <td>${ticketHeader.applicationName}</td>
                                            <td>
                                                ${ticketHeader.approvedName1}
                                            </td>
                                            <td>
                                                ${ticketHeader.approvedName2}
                                            </td>
                                            <td>
                                                <c:if test="${ticketHeader.payment eq null}">
                                                    -
                                                </c:if>
                                                ${ticketHeader.payment}
                                            </td>
                                            <td>
                                                <c:if test="${ticketHeader.paidBy eq null}">
                                                    -
                                                </c:if>

                                                ${ticketHeader.paidBy} 
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>

                            </table>
                        </div>

                    </div>
                </div>


                <div class="tab-pane" id="2"> 
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
                                        <th width="10px"> # </th>
                                        <th width="140px"> Ticket No </th>
                                        <th width="80px"> Date </th>
                                        <th width="200px"> Topic </th>
                                        <th width="120px"> Application By </th>
                                        <th width="110px"> 1st Approve </th>
                                        <th width="110px"> 2nd Approve </th>
                                        <th width="20px"> Payment </th>
                                        <th width="110px"> Paid By </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="ticketHeader" items="${THANIYA}">
                                        <tr>
                                            <td>
                                                <c:if test="${ticketHeader.ticketType eq 'ENT'}">
                                                    <a href="<c:url value='/createticket/show?id=${ticketHeader.ticketNo}-C'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'TRN'}">
                                                    <a href="<c:url value='/training/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'ADV'}">
                                                    <a href="<c:url value='/advance/show?id=${ticketHeader.ticketNo}-C'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'PTC'}">
                                                    <a href="<c:url value='/pettycash/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                            </td>
                                            <td><c:if test="${empty ticketHeader.showTicket}">
                                                    ${ticketHeader.ticketNo}
                                                </c:if>
                                                <c:if test="${not empty ticketHeader.showTicket}">
                                                    ${ticketHeader.showTicket}
                                                </c:if></td>
                                            <td>${ticketHeader.applicationDate}</td>
                                            <td style="max-width: 200px; word-wrap: break-word;">${ticketHeader.detailHeader}</td>
                                            <td>${ticketHeader.applicationName}</td>
                                            <td>
                                                ${ticketHeader.approvedName1}
                                            </td>
                                            <td>
                                                ${ticketHeader.approvedName2}
                                            </td>
                                            <td>
                                                <c:if test="${ticketHeader.payment eq null}">
                                                    -
                                                </c:if>
                                                ${ticketHeader.payment}
                                            </td>
                                            <td>
                                                <c:if test="${ticketHeader.paidBy eq null}">
                                                    -
                                                </c:if>

                                                ${ticketHeader.paidBy} 
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>

                            </table>
                        </div>

                    </div>
                </div>

                <div class="tab-pane" id="3"> 
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
                                        <th width="10px"> # </th>
                                        <th width="140px"> Ticket No </th>
                                        <th width="80px"> Date </th>
                                        <th width="200px"> Topic </th>
                                        <th width="120px"> Application By </th>
                                        <th width="110px"> 1st Approve </th>
                                        <th width="110px"> 2nd Approve </th>
                                        <th width="20px"> Payment </th>
                                        <th width="110px"> Paid By </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="ticketHeader" items="${BLC}">
                                        <tr>
                                            <td>
                                                <c:if test="${ticketHeader.ticketType eq 'ENT'}">
                                                    <a href="<c:url value='/createticket/show?id=${ticketHeader.ticketNo}-C'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'TRN'}">
                                                    <a href="<c:url value='/training/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'ADV'}">
                                                    <a href="<c:url value='/advance/show?id=${ticketHeader.ticketNo}-C'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'PTC'}">
                                                    <a href="<c:url value='/pettycash/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                            </td>
                                            <td><c:if test="${empty ticketHeader.showTicket}">
                                                    ${ticketHeader.ticketNo}
                                                </c:if>
                                                <c:if test="${not empty ticketHeader.showTicket}">
                                                    ${ticketHeader.showTicket}
                                                </c:if></td>
                                            <td>${ticketHeader.applicationDate}</td>
                                            <td style="max-width: 200px; word-wrap: break-word;">${ticketHeader.detailHeader}</td>
                                            <td>${ticketHeader.applicationName}</td>
                                            <td>
                                                ${ticketHeader.approvedName1}
                                            </td>
                                            <td>
                                                ${ticketHeader.approvedName2}
                                            </td>
                                            <td>
                                                <c:if test="${ticketHeader.payment eq null}">
                                                    -
                                                </c:if>
                                                ${ticketHeader.payment}
                                            </td>
                                            <td>
                                                <c:if test="${ticketHeader.paidBy eq null}">
                                                    -
                                                </c:if>

                                                ${ticketHeader.paidBy} 
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>

                            </table>
                        </div>

                    </div>
                </div>
                <div class="tab-pane" id="4"> 
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
                                        <th width="10px"> # </th>
                                        <th width="140px"> Ticket No </th>
                                        <th width="80px"> Date </th>
                                        <th width="200px"> Topic </th>
                                        <th width="120px"> Application By </th>
                                        <th width="110px"> 1st Approve </th>
                                        <th width="110px"> 2nd Approve </th>
                                        <th width="20px"> Payment </th>
                                        <th width="110px"> Paid By </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="ticketHeader" items="${NLC}">
                                        <tr>
                                            <td>
                                                <c:if test="${ticketHeader.ticketType eq 'ENT'}">
                                                    <a href="<c:url value='/createticket/show?id=${ticketHeader.ticketNo}-C'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'TRN'}">
                                                    <a href="<c:url value='/training/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'ADV'}">
                                                    <a href="<c:url value='/advance/show?id=${ticketHeader.ticketNo}-C'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'PTC'}">
                                                    <a href="<c:url value='/pettycash/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                            </td>
                                            <td><c:if test="${empty ticketHeader.showTicket}">
                                                    ${ticketHeader.ticketNo}
                                                </c:if>
                                                <c:if test="${not empty ticketHeader.showTicket}">
                                                    ${ticketHeader.showTicket}
                                                </c:if></td>
                                            <td>${ticketHeader.applicationDate}</td>
                                            <td style="max-width: 200px; word-wrap: break-word;">${ticketHeader.detailHeader}</td>
                                            <td>${ticketHeader.applicationName}</td>
                                            <td>
                                                ${ticketHeader.approvedName1}
                                            </td>
                                            <td>
                                                ${ticketHeader.approvedName2}
                                            </td>
                                            <td>
                                                <c:if test="${ticketHeader.payment eq null}">
                                                    -
                                                </c:if>
                                                ${ticketHeader.payment}
                                            </td>
                                            <td>
                                                <c:if test="${ticketHeader.paidBy eq null}">
                                                    -
                                                </c:if>

                                                ${ticketHeader.paidBy} 
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>

                            </table>
                        </div>

                    </div>
                </div>
                <div class="tab-pane" id="5"> 
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
                                        <th width="10px"> # </th>
                                        <th width="140px"> Ticket No </th>
                                        <th width="80px"> Date </th>
                                        <th width="200px"> Topic </th>
                                        <th width="120px"> Application By </th>
                                        <th width="110px"> 1st Approve </th>
                                        <th width="110px"> 2nd Approve </th>
                                        <th width="20px"> Payment </th>
                                        <th width="110px"> Paid By </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="ticketHeader" items="${AIRPORT}">
                                        <tr>
                                            <td>
                                                <c:if test="${ticketHeader.ticketType eq 'ENT'}">
                                                    <a href="<c:url value='/createticket/show?id=${ticketHeader.ticketNo}-C'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'TRN'}">
                                                    <a href="<c:url value='/training/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'ADV'}">
                                                    <a href="<c:url value='/advance/show?id=${ticketHeader.ticketNo}-C'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'PTC'}">
                                                    <a href="<c:url value='/pettycash/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                            </td>
                                            <td><c:if test="${empty ticketHeader.showTicket}">
                                                    ${ticketHeader.ticketNo}
                                                </c:if>
                                                <c:if test="${not empty ticketHeader.showTicket}">
                                                    ${ticketHeader.showTicket}
                                                </c:if></td>
                                            <td>${ticketHeader.applicationDate}</td>
                                            <td style="max-width: 200px; word-wrap: break-word;">${ticketHeader.detailHeader}</td>
                                            <td>${ticketHeader.applicationName}</td>
                                            <td>
                                                ${ticketHeader.approvedName1}
                                            </td>
                                            <td>
                                                ${ticketHeader.approvedName2}
                                            </td>
                                            <td>
                                                <c:if test="${ticketHeader.payment eq null}">
                                                    -
                                                </c:if>
                                                ${ticketHeader.payment}
                                            </td>
                                            <td>
                                                <c:if test="${ticketHeader.paidBy eq null}">
                                                    -
                                                </c:if>

                                                ${ticketHeader.paidBy} 
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>

                            </table>
                        </div>

                    </div>
                </div>
                <div class="tab-pane" id="6"> 
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
                                        <th width="10px"> # </th>
                                        <th width="140px"> Ticket No </th>
                                        <th width="80px"> Date </th>
                                        <th width="200px"> Topic </th>
                                        <th width="120px"> Application By </th>
                                        <th width="110px"> 1st Approve </th>
                                        <th width="110px"> 2nd Approve </th>
                                        <th width="20px"> Payment </th>
                                        <th width="110px"> Paid By </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="ticketHeader" items="${NCC}">
                                        <tr>
                                            <td>
                                                <c:if test="${ticketHeader.ticketType eq 'ENT'}">
                                                    <a href="<c:url value='/createticket/show?id=${ticketHeader.ticketNo}-C'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'TRN'}">
                                                    <a href="<c:url value='/training/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'ADV'}">
                                                    <a href="<c:url value='/advance/show?id=${ticketHeader.ticketNo}-C'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'PTC'}">
                                                    <a href="<c:url value='/pettycash/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                            </td>
                                            <td><c:if test="${empty ticketHeader.showTicket}">
                                                    ${ticketHeader.ticketNo}
                                                </c:if>
                                                <c:if test="${not empty ticketHeader.showTicket}">
                                                    ${ticketHeader.showTicket}
                                                </c:if></td>
                                            <td>${ticketHeader.applicationDate}</td>
                                            <td style="max-width: 200px; word-wrap: break-word;">${ticketHeader.detailHeader}</td>
                                            <td>${ticketHeader.applicationName}</td>
                                            <td>
                                                ${ticketHeader.approvedName1}
                                            </td>
                                            <td>
                                                ${ticketHeader.approvedName2}
                                            </td>
                                            <td>
                                                <c:if test="${ticketHeader.payment eq null}">
                                                    -
                                                </c:if>
                                                ${ticketHeader.payment}
                                            </td>
                                            <td>
                                                <c:if test="${ticketHeader.paidBy eq null}">
                                                    -
                                                </c:if>

                                                ${ticketHeader.paidBy} 
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>

                            </table>
                        </div>

                    </div>
                </div>
                <div class="tab-pane" id="7"> 
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
                                        <th width="10px"> # </th>
                                        <th width="140px"> Ticket No </th>
                                        <th width="80px"> Date </th>
                                        <th width="200px"> Topic </th>
                                        <th width="120px"> Application By </th>
                                        <th width="110px"> 1st Approve </th>
                                        <th width="110px"> 2nd Approve </th>
                                        <th width="20px"> Payment </th>
                                        <th width="110px"> Paid By </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="ticketHeader" items="${LAEMCHABANG}">
                                        <tr>
                                            <td>
                                                <c:if test="${ticketHeader.ticketType eq 'ENT'}">
                                                    <a href="<c:url value='/createticket/show?id=${ticketHeader.ticketNo}-C'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'TRN'}">
                                                    <a href="<c:url value='/training/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'ADV'}">
                                                    <a href="<c:url value='/advance/show?id=${ticketHeader.ticketNo}-C'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'PTC'}">
                                                    <a href="<c:url value='/pettycash/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                            </td>
                                            <td><c:if test="${empty ticketHeader.showTicket}">
                                                    ${ticketHeader.ticketNo}
                                                </c:if>
                                                <c:if test="${not empty ticketHeader.showTicket}">
                                                    ${ticketHeader.showTicket}
                                                </c:if></td>
                                            <td>${ticketHeader.applicationDate}</td>
                                            <td style="max-width: 200px; word-wrap: break-word;">${ticketHeader.detailHeader}</td>
                                            <td>${ticketHeader.applicationName}</td>
                                            <td>
                                                ${ticketHeader.approvedName1}
                                            </td>
                                            <td>
                                                ${ticketHeader.approvedName2}
                                            </td>
                                            <td>
                                                <c:if test="${ticketHeader.payment eq null}">
                                                    -
                                                </c:if>
                                                ${ticketHeader.payment}
                                            </td>
                                            <td>
                                                <c:if test="${ticketHeader.paidBy eq null}">
                                                    -
                                                </c:if>

                                                ${ticketHeader.paidBy} 
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>

                            </table>
                        </div>

                    </div>
                </div>

                <div class="tab-pane" id="8"> 
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
                                        <th width="10px"> # </th>
                                        <th width="140px"> Ticket No </th>
                                        <th width="80px"> Date </th>
                                        <th width="200px"> Topic </th>
                                        <th width="120px"> Application By </th>
                                        <th width="110px"> 1st Approve </th>
                                        <th width="110px"> 2nd Approve </th>
                                        <th width="20px"> Payment </th>
                                        <th width="110px"> Paid By </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="ticketHeader" items="${KORAT}">
                                        <tr>
                                            <td>
                                                <c:if test="${ticketHeader.ticketType eq 'ENT'}">
                                                    <a href="<c:url value='/createticket/show?id=${ticketHeader.ticketNo}-C'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'TRN'}">
                                                    <a href="<c:url value='/training/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'ADV'}">
                                                    <a href="<c:url value='/advance/show?id=${ticketHeader.ticketNo}-C'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                                <c:if test="${ticketHeader.ticketType eq 'PTC'}">
                                                    <a href="<c:url value='/pettycash/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger" target="_blank">show</a>
                                                </c:if>
                                            </td>
                                            <td><c:if test="${empty ticketHeader.showTicket}">
                                                    ${ticketHeader.ticketNo}
                                                </c:if>
                                                <c:if test="${not empty ticketHeader.showTicket}">
                                                    ${ticketHeader.showTicket}
                                                </c:if></td>
                                            <td>${ticketHeader.applicationDate}</td>
                                            <td style="max-width: 200px; word-wrap: break-word;">${ticketHeader.detailHeader}</td>
                                            <td>${ticketHeader.applicationName}</td>
                                            <td>
                                                ${ticketHeader.approvedName1}
                                            </td>
                                            <td>
                                                ${ticketHeader.approvedName2}
                                            </td>
                                            <td>
                                                <c:if test="${ticketHeader.payment eq null}">
                                                    -
                                                </c:if>
                                                ${ticketHeader.payment}
                                            </td>
                                            <td>
                                                <c:if test="${ticketHeader.paidBy eq null}">
                                                    -
                                                </c:if>

                                                ${ticketHeader.paidBy} 
                                            </td>
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
