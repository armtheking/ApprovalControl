<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>

        <%@ include file="../template/head.jspf" %>

    </head>
    <body>

        <%@ include file="../template/pageTitle.jspf" %>

        <div class="container">

            <form:form id="form1" class="form-horizontal" enctype="multipart/form-data"
                       method="post" action="create?${_csrf.parameterName}=${_csrf.token}" >
                <fieldset>
                    <legend>Advance</legend>
                </fieldset>

                </br>
                <div class="panel panel-info" style="width: 100%; margin: auto">
                    <div class="panel-heading">
                        <h3 class="panel-title"><b>Preview</b></h3>
                    </div>

                    <div class="panel-body">
                        <div class="row" style="margin: 0.5%;"> 
                            <div class="form-group form-group-sm">
                                <div class="col-sm-3">
                                    <b>Objective / Reason:</b> 
                                </div>
                                <div class="col-sm-9"  style="width:840px; word-wrap: break-word; display:block;">
                                    <mark>${ticketHeader.detailHeader}</mark>
                                </div>
                            </div>
                        </div>

                        <div class="row" style="margin: 0.5%;"> 
                            <div class="form-group form-group-sm">
                                <div class="col-sm-3">
                                    <b>Remark:</b> 
                                </div>
                                <div class="col-sm-9" style="width:840px; word-wrap: break-word; display:block;">
                                    <mark>${ticketHeader.remarkHeader}</mark>
                                </div>
                            </div>
                        </div>

                        <div class="row" style="margin: 0.5%;"> 
                            <div class="form-group form-group-sm">
                                <div class="col-sm-3">
                                    <b>Settlement Within: </b> 
                                </div>
                                <div class="col-sm-3">
                                    <mark>${ticketHeader.dateAlert} Days</mark>
                                </div>
                            </div>
                        </div>

                    </div>


                    <div class="row" style="margin: 0.5%;"> 
                        <table id="preview_advance"  class="table table-bordered" cellspacing="0" width="100%">
                            <thead>
                                <tr class="success">
                                    <th width="30px">No</th>                               
                                    <th width="300px">Description</th>
                                    <th width="360px">Details</th>
                                    <th width="100px">Amount</th>
                                </tr>
                            </thead>
                            <tbody>   
                                <c:forEach varStatus="varStatus" var="ticketDetail" items="${ticketDetail}">
                                    <tr>
                                        <c:if test="${ticketDetail.amount != null}">
                                            <td>${varStatus.count}</td>
                                            <td>${ticketDetail.description}</td> 
                                            <td style="max-width: 360px; word-wrap: break-word;">${ticketDetail.detail}</td>                           
                                            <td align="right">${ticketDetail.amount}</td>
                                        </c:if>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <div style="margin-left: 82%"><font color="red"> <u>  Total Amount:  ${number_sumAmount} </u></font></div>
                        <center>          
                                <input type="submit" class="btn btn-success" value="SUBMIT" name="submit" >
                            <a href="<c:url value='previous'/>" class="btn btn-danger">PREVIOUS </a>
                        </center>
                    </div>

                </div>


            </form:form>

        </div>
        <%@ include file="../template/footer.jspf" %>
        <%@ include file="../template/scripts.jspf" %>
    </body>
</html>
