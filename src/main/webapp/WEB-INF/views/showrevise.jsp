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

        <div class="container-fluid">
            <fieldset>
                <legend><i class="fa fa-history"></i> Show Revise</legend>
            </fieldset>
         
            <div class="row">
                <!-- Table Condensed -->
              
                    <table class="table table-striped" id="example2">
                        <thead>
                            <tr>
                                <th style="width: 10px"> No </th>
                                <th style="width: 130px"> Ticket No </th>
                                <th style="width: 80px" > Date </th>
                                <th style="width: 150px" > 1st Approve </th>
                                <th style="width: 150px"> 1st Remark </th>
                                <th style="width: 150px"> 2nd Approve </th>
                                <th style="width: 150px"> 2nd Remark </th>
                                <th style="width: 80px"> Amount </th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach varStatus="varStatus" var="historyList" items="${historyList}">


                                <tr>
                                    <td style="max-width: 10px; word-wrap: break-word;">
                                        ${varStatus.count}
                                    </td>
                                    <td style="max-width: 130px; word-wrap: break-word;">${historyList.ticketRev}</td>
                                    <td style="max-width: 80px; word-wrap: break-word;">${historyList.date}</td>
                                    <td style="max-width: 150px; word-wrap: break-word;"><FONT style="color:#FF0000;">${historyList.approvedName1}</FONT></td>
                                    <td style="max-width: 150px; word-wrap: break-word;">
                                        <c:if test="${empty historyList.approvedRemark1}">
                                            -
                                        </c:if>
                                        <c:if test="${not empty historyList.approvedRemark1}">
                                            <FONT style="color:#FF0000;"> ${historyList.approvedRemark1} </FONT>
                                        </c:if>
                                    </td>
                                    <td style="max-width: 150px; word-wrap: break-word;"><c:if test="${empty historyList.approvedName2}">
                                            -
                                        </c:if>
                                        <c:if test="${not empty historyList.approvedName2}">
                                            <FONT style="color:#FF0000;">${historyList.approvedName2}</FONT>
                                        </c:if></td>
                                    <td style="max-width: 150px; word-wrap: break-word;"><c:if test="${empty historyList.approvedRemark2}">
                                            -
                                        </c:if>
                                        <c:if test="${not empty historyList.approvedRemark2}">
                                            <FONT style="color:#FF0000;"> ${historyList.approvedRemark2} </FONT>
                                        </c:if></td>
                                    <td style="max-width: 80px; word-wrap: break-word;">${historyList.reqTotalAmt}</td>
                                </tr>
                            </c:forEach>
                        </tbody>

                    </table>
            

            </div>
        </div>
        <%@ include file="template/footer.jspf" %>
        <%@ include file="template/scripts.jspf" %>

    </body>
</html>
