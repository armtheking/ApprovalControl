<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>

<html>
    <head>

        <%@ include file="template/head.jspf" %>

    </head>
    <body>

        <%@ include file="template/pageTitle.jspf" %>


        <div class="container">

            <form:form class="form-horizontal" action="save?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data"
                       modelAttribute="ticketHeader" >
                <fieldset>
                    <legend>Preview Data</legend>
                    <!-- Row 2 -->
                    <div class="row">
                        <div class="col-md-12">
                            <div class="panel panel-danger">
                                <div class="panel-heading clearfix">


                                    <div class="btn-group pull-right">






                                        <c:if test="${ticketHeader.ticketType eq 'ENT'}">

                                            <label> <b>Entertain</b> </label>
                                        </c:if>




                                    </div>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <label> Customer's Name </label>
                                            <div class="well">
                                                ${ticketHeader.detailHeader}
                                            </div>
                                        </div>

                                        <div class="col-md-6">
                                            <label> Purpose / Reason </label>
                                            <div class="well">
                                                ${ticketHeader.remarkHeader}
                                            </div>
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
                                                                <th class="col-md-4"> Participant </th>
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
                                                <span class="doubleUnderline">${ticketHeader.reqTotalAmt}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <br>

                                            </strong>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                </fieldset>
                <br/>
                <div class="row">          
                    <div class="col-md-8 col-md-offset-5">
                        <button type="submit" class="btn btn-primary btn-lg "><i class="glyphicon glyphicon-floppy-disk"></i> SUBMIT</button>
                        <a href="<c:url value='/createticket/previous'/>" class="btn btn-danger btn-lg"><i class="glyphicon glyphicon-refresh"></i> PREVIOUS </a>
                    </div>
                </div>
            </form:form>
        </div>

        <%@ include file="template/footer.jspf" %>
        <%@ include file="template/scripts.jspf" %>

    </body>
</html>
