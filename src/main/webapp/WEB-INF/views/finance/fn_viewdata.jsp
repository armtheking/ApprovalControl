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
            ${search}
            <div class="row">
                <!-- Table Condensed -->
                <div class="panel panel-success">
                    <table class="table table-striped" id="example2">
                        <div class="panel-heading">
                            <h3 class="panel-title">Finance Approve</h3>
                        </div>

                         
                       <thead>
                            <tr>
                                <th width="10px"> # </th>
                                <th width="100px"> Ticket No </th>
                                <th  width="120px"> Date </th>
                                <th> Topic </th>
                                <th width="110px"> Application By </th>
                                <th width="110px"> 1st Approve </th>
                                <th width="110  px"> 2nd Approve </th>
                                <th width="20px"> Payment </th>
                                <th width="110px"> Paid By </th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="ticketHeader" items="${ticketHeaderListFinance}">

                  
                                    <tr>
                                        <td>
                                            <c:if test="${ticketHeader.ticketType eq 'ENT'}">
                                                <a href="<c:url value='/createticket/show?id=${ticketHeader.ticketNo}-C'/>" class="btn btn-danger">show</a>
                                            </c:if>
                                            <c:if test="${ticketHeader.ticketType eq 'TRN'}">
                                                <a href="<c:url value='/training/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger">show</a>
                                            </c:if>
                                            <c:if test="${ticketHeader.ticketType eq 'ADV'}">
                                                <a href="<c:url value='/advance/show?id=${ticketHeader.ticketNo}-C'/>" class="btn btn-danger">show</a>
                                            </c:if>
                                            <c:if test="${ticketHeader.ticketType eq 'PTC'}">
                                                <a href="<c:url value='/pettycash/show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger">show</a>
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
        <%@ include file="../template/footer.jspf" %>
        <%@ include file="../template/scripts.jspf" %>

    </body>
</html>
