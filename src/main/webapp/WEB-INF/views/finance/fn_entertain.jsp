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
            ${search}
            <div class="row">
                <!-- Table Condensed -->
                <div class="panel panel-success">
                    <table class="table table-striped">
                        <div class="panel-heading">
                            <h3 class="panel-title">Finance Approve</h3>
                        </div>


                        <thead>
                            <tr>
                                <th> # </th>
                                <th> Ticket No </th>
                                <th> Date </th>
                                <th> Topic </th>
                                <th> Application By </th>
                                <th> 1st Approve </th>
                                <th> 2nd Approve </th>
                                <th> Status </th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="ticketHeader" items="${ticketHeaderListFinance}">

                  
                                    <tr>
                                        <td> <a href="<c:url value='show?id=${ticketHeader.ticketNo}'/>" class="btn btn-danger">show</a></td>
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
                                        <td>${ticketHeader.ticketFinished}

                                            <c:if test="${ticketHeader.ticketFinished eq 'W'}"> 
                                                <a href="<c:url value='/clearticket?id=${ticketHeader.ticketNo}'/>" class="btn btn-info btn-sm "> Clear <span class="fa fa-hand-pointer-o"></span></a>
                                                </c:if>

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
