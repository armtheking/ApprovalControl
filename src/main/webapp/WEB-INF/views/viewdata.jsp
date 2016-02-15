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

        <div class="container-fluid">

            <div class="row">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <form class="form-inline" role="form" action="${pageContext.request.contextPath}/viewdata_all/search?${_csrf.parameterName}=${_csrf.token}" method="POST" enctype="multipart/form-data">
                            <div class="form-group">
                                <label class="filter-col" style="margin-right:0;" for="pref-perpage"> Month :</label>
                                <select id="pref-perpage" class="form-control" name="month">
                                    <option selected="selected" value="0">--SELECT--</option>
                                    <option value="1">January</option>
                                    <option value="2">February</option>
                                    <option value="3">March</option>
                                    <option value="4">April</option>
                                    <option value="5">May</option>
                                    <option value="6">June</option>
                                    <option value="7">July</option>
                                    <option value="8">August</option>
                                    <option value="9">September</option>
                                    <option value="10">October</option>
                                    <option value="11">November</option>
                                    <option value="12">December</option>

                                </select>                                
                            </div> <!-- form group [rows] -->

                            <div class="form-group">
                                <label class="filter-col" style="margin-right:0;" for="pref-perpage"> Year :</label>
                                <select id="pref-perpage" class="form-control" name="year">
                                    <option selected="selected" value="0">--SELECT--</option>
                                    <option value="2013">2013</option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>

                                </select>                                
                            </div>
                            <button type="submit" class="btn btn-default filter-col">
                                <span class="glyphicon glyphicon-record"></span> Search
                            </button>  
                        </form>
                    </div>
                </div>
            </div>

            <div class="row">
                <!-- Table Condensed -->
                <div class="panel panel-success">
                    <table class="table table-striped" id="example2">
                        <div class="panel-heading ">
                            <h3>View Data In Your Division [${month}, ${year}]</h3>
                          
                        </div>

                        <thead>
                            <tr>
                                <th width="10px"> # </th>
                                <th width="140px"> Ticket No </th>
                                <th width="20px"> Type </th>
                                <th width="80px"> Date </th>
                                <th> Topic </th>
                                <th width="100px"> Amount </th>
                                <th width="110px"> Application By </th>
                                <th width="110px"> 1st Approval </th>
                                <th width="110px"> 2nd Approval </th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="ticketHeader" items="${ticketHeaderView}">

                                <!-- Show specifically person permission-->


                                <tr>
                                    <td> <c:if test="${ticketHeader.ticketType eq 'ENT'}">
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
                                    
                                    <td>${ticketHeader.ticketNo} &nbsp;
                                         <c:if test="${!fn:contains(ticketHeader.ticketNo, '-C')}">
                                              &nbsp;&nbsp;
                                         </c:if>
                                      
                                        <c:if test="${ticketHeader.ticketFinished eq 'F'}">
                                            <sup><img src="<c:url value='/resources/img/success.png' />" alt="Mountain View" style="width:16px;height:16px;"></sup>

                                        </c:if>

                                        <c:if test="${ticketHeader.ticketFinished eq 'R'}">
                                            <sup><img src="<c:url value='/resources/img/reject.png' />" alt="Mountain View" style="width:16px;height:16px;"></sup>

                                        </c:if>




                                    </td>
                                    <td>${ticketHeader.ticketType}</td>
                                    <td>${ticketHeader.applicationDate}</td>
                                    <td id="something">${ticketHeader.detailHeader}</td>
                                    <td>${ticketHeader.reqTotalAmt}</td>
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
