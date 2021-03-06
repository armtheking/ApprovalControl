<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
    <head>

        <%@ include file="../template/head.jspf" %>

    </head>
    <body>

        <%@ include file="../template/pageTitle.jspf" %>
        <div class="container">
            <form:form class="form-horizontal" action="${pageContext.request.contextPath}/createticket/edit?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data"
                       modelAttribute="ticketHeader" >
                <fieldset>
                    <legend><h3><i class="fa fa-pencil-square-o"> Edit :: ${ticketHeader.ticketNo}</i></h3></legend>

                    <input type="hidden" name="ticketType" value="ENT"/>
                    <input type="hidden" name="ticketNo" value="${ticketHeader.ticketNo}"/>
                    <input type="hidden" name="item" value="${ticketHeader.item}"/>
                    <input type="hidden" name="ticketFinished" value="${ticketHeader.ticketFinished}"/>
                    <input type="hidden" name="refTicketNo" value="${ticketHeader.refTicketNo}"/>

                    <div class="form-group">
                        <label for="detailHeader" class="col-sm-3 control-label"> Customer's Name : </label>
                        <div class="col-sm-4">

                            <form:input type="text" path="detailHeader" id="detailHeader" class="form-control" placeholder="Customer's Name" autofocus="autofocus"/>

                            <div class="has-error">
                                <form:errors path="detailHeader" class="help-inline" cssClass="error"/>
                            </div>
                        </div>
                        <div class="col-sm-5">
                            <label><span style="color: red;"> ** </span>Company Name</label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="remarkHeader" class="col-sm-3 control-label">Purpose / Reason : </label>
                        <div class="col-sm-4">
                            <form:input type="text" path="remarkHeader" class="form-control" placeholder="Purpose / Reason"/>
                            <div class="has-error">
                                <form:errors path="remarkHeader" class="help-inline" cssClass="error"/>
                            </div>
                        </div>
                        <div class="col-sm-5">
                            <label><span style="color: red;"> ** </span> Customer / Guest Visit, Reception Party, ISO Audit, Souvenir / Gift for customer 

                            </label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="dateAlert" class="col-md-3 control-label">Settlement Within : </label>
                        <div class="col-sm-2">
                            <form:select path="dateAlert" class="form-control input-sm">

                                <form:option value="7">7 Day</form:option>
                                <form:option value="14">14 Day</form:option>
                            </form:select>
                            <div class="has-error">
                                <form:errors path="dateAlert" class="help-inline" cssClass="error"/>
                            </div>
                        </div>
                    </div>

                    <div class="col-sm-12">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col"> No </th>
                                    <th scope="col"> Description </th>
                                    <th scope="col"> Place </th>
                                    <th scope="col"> Number & List of Participant </th>
                                    <th scope="col"> Amount </th>
                                </tr>
                            </thead>
                            <tbody>

                                <tr>
                                    <td class="col-sm-1">
                                        <label class="col-sm-12" >1</label>
                                    </td>
                                    <td class="col-sm-4">

                                        <form:select path="ticketdetail[0].financeChargeCode.id" items="${chargeCode}" itemValue="id" itemLabel="description" class="form-control input-sm"/>
                                        <div class="has-error">
                                            <form:errors path="ticketdetail[0].financeChargeCode.id" class="help-inline"/>
                                        </div>

                                    </td>
                                    <td class="col-sm-2">
                                        <form:textarea type="text" rows="4" path="ticketdetail[0].place" class="form-control col-sm-12" placeholder="Name of restaurant / Golf Course, Location, etc."/>
                                        <div class="has-error">
                                            <form:errors path="ticketdetail[0].place" class="help-inline" cssClass="error"/>
                                        </div>
                                    </td>
                                    <td class="col-sm-3">
                                        <form:textarea type="text" path="ticketdetail[0].detail" class="form-control col-sm-12" placeholder="Participant" rows="5"/>
                                        <div class="has-error">
                                            <form:errors path="ticketdetail[0].detail" class="help-inline" cssClass="error"/>
                                        </div>
                                    </td>
                                    <td class="col-sm-2">
                                        <form:input type="text" path="ticketdetail[0].amount" class="form-control col-sm-12" placeholder="Amount"/>
                                        <div class="has-error">
                                            <form:errors path="ticketdetail[0].amount" class="help-inline" cssClass="error"/>
                                        </div>
                                    </td>                           
                                </tr>


                            </tbody>

                        </table>

                    </div>

                </fieldset>
                <br/>
                <div class="row">
                    <div class="col-md-8 col-md-offset-5">
                        <button type="submit" class="btn btn-primary btn-lg">SAVE</button>
                        <button type="reset" class="btn btn-danger btn-lg">CANCEL</button>

                    </div>
                </div>

            </form:form>
        </div>
        <%@ include file="../template/footer.jspf" %>
        <%@ include file="../template/scripts.jspf" %>

    </body>
</html>