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
            <form:form class="form-horizontal" action="clearticket/preview?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data"
                       modelAttribute="ticketHeader" >
                <fieldset>
                    <legend>Clear ${ticketHeader.ticketNo} (Entertain)</legend>
                   
                    <form:input type="hidden" path="ticketNo" value="${ticketHeader.ticketNo}" id="ticketNo" class="form-control" />
                    <form:input type="hidden" path="ticketType" value="${ticketHeader.ticketType}" id="ticketType" class="form-control" />
                    <form:input type="hidden" path="applicationName" value="${ticketHeader.applicationName}" id="applicationName" class="form-control" />

                    <div class="form-group">
                        <label for="detailHeader" class="col-sm-2 control-label">Customer's Name : </label>
                        <div class="col-sm-5">
                            <form:input type="text" path="detailHeader" value="${ticketHeader.detailHeader}" id="detailHeader" class="form-control" placeholder="Customer's Name" autofocus="autofocus"/>
                            <div class="has-error">
                                <form:errors path="detailHeader" class="help-inline" cssClass="error"/>
                            </div>
                        </div>
                        <div class="col-sm-5">
                            <label><span style="color: red;"> ** </span>Company Name</label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="remarkHeader" class="col-sm-2 control-label">Purpose / Reason : </label>
                        <div class="col-sm-5">
                            <form:input type="text" path="remarkHeader" class="form-control" placeholder="Purpose / Reason"/>
                            <div class="has-error">
                                <form:errors path="remarkHeader" class="help-inline" cssClass="error"/>
                            </div>
                        </div>
                        <div class="col-sm-5">
                              <label><span style="color: red;"> ** </span> Customer / Gust Visit, Reception Party, ISO Audit, Souvenir / Gift for customer  </label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="exampleInputFile" class="col-sm-2 control-label">File input : </label>
                        <div class="col-sm-5">
                            <input type="file" name="file" size="60" accept="application/pdf" required="true"/>
                            <p class="help-block">require .pdf</p>
                        </div>

                    </div>        




                    <div class="col-sm-12">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">No</th>
                                    <th scope="col">Description</th>
                                    <th scope="col">Place</th>
                                    <th scope="col">Number & List of Participant</th>
                                    <th scope="col">Amount</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="count" value="0" scope="page" />
                                <c:forEach var="ticketdetail" items="${ticketHeader.ticketdetail}" varStatus="i">
                                    <tr>
                                        <td class="col-sm-1">
                                            <c:set var="count" value="${count + 1}" scope="page"/> 
                                            <c:out value="${count}" />
                                        </td>
                                        <td class="col-sm-4">
                                            <form:input type="hidden"  path="ticketdetail[${i.index}].financeChargeCode.id" class="form-control col-sm-12"/>
                                            <form:input type="text"  path="ticketdetail[${i.index}].financeChargeCode.description" class="form-control col-sm-12" disabled="true"/>
                                            <div class="has-error">
                                                <form:errors path="ticketdetail[${i.index}].financeChargeCode.description" class="help-inline" cssClass="error"/>
                                            </div>
                                        </td>

                                        <td class="col-sm-2">
                                            <form:textarea type="text" rows="4" path="ticketdetail[${i.index}].place" class="form-control col-sm-12" placeholder="Name of restaurant / Golf Course, Location, etc."/>
                                            <div class="has-error">
                                                <form:errors path="ticketdetail[${i.index}].place" class="help-inline" cssClass="error"/>
                                            </div>
                                        </td>
                                        <td class="col-sm-3">
                                            <form:textarea type="text" path="ticketdetail[${i.index}].detail" class="form-control col-sm-12" placeholder="Participant"/>
                                            <div class="has-error">
                                                <form:errors path="ticketdetail[${i.index}].detail" class="help-inline" cssClass="error"/>
                                            </div>
                                        </td>
                                        <td class="col-sm-2">
                                            <form:input type="text" path="ticketdetail[${i.index}].amount" class="form-control col-sm-12" placeholder="Amount"/>
                                            <div class="has-error">
                                                <form:errors path="ticketdetail[${i.index}].amount" class="help-inline" cssClass="error"/>
                                            </div>
                                        </td>   

                                    </tr>
                                </c:forEach>


                            </tbody>

                        </table>

                    </div>

                </fieldset>
                <br/>
                                 
                <div class="row">
                    <div class="col-md-8 col-md-offset-5">
                        <button type="submit" class="btn btn-primary btn-lg">NEXT</button>
                        <button type="reset" class="btn btn-danger btn-lg">CANCEL</button>

                    </div>
                </div>
            </form:form>

        </div>



        <%@ include file="template/footer.jspf" %>
        <%@ include file="template/scripts.jspf" %>

    </body>
</html>
