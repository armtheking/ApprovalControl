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
            <form:form class="form-horizontal" action="${pageContext.request.contextPath}/createticket/editClear?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data"
                       modelAttribute="ticketHeader" >
                <fieldset>
                    <legend><h3><i class="fa fa-pencil-square-o"> Edit :: ${ticketHeader.ticketNo}</i></h3></legend>

                    <input type="hidden" name="ticketType" value="ENT"/>
                    <input type="hidden" name="ticketNo" value="${ticketHeader.ticketNo}"/>
                    <input type="hidden" name="item" value="${ticketHeader.item}"/>
                    <input type="hidden" name="ticketFinished" value="${ticketHeader.ticketFinished}"/>
                    <input type="hidden" name="refTicketNo" value="${ticketHeader.refTicketNo}"/>

                    <div class="form-group">
                        <label for="detailHeader" class="col-sm-3 control-label">Topic : </label>
                        <div class="col-sm-4">

                            <form:input type="text" path="detailHeader" id="detailHeader" class="form-control" placeholder="Topic" autofocus="autofocus"/>

                            <div class="has-error">
                                <form:errors path="detailHeader" class="help-inline" cssClass="error"/>
                            </div>
                        </div>
                        <div class="col-sm-5">
                            <label><span style="color: red;"> ** The red text it can changes as appropriate </span><br/>
                                1. The expense for entertain <span style="color: red">Customer Name</span>
                            </label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="remarkHeader" class="col-sm-3 control-label">Reason : </label>
                        <div class="col-sm-4">
                            <form:input type="text" path="remarkHeader" class="form-control" placeholder="Reason"/>
                            <div class="has-error">
                                <form:errors path="remarkHeader" class="help-inline" cssClass="error"/>
                            </div>
                        </div>
                        <div class="col-sm-5">
                            <label> 1. Welcome party /Farewell <br/>
                                2. Introduce Officer /Consult /Customer visiting
                            </label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="exampleInputFile" class="col-sm-3 control-label">File input : </label>
                        <div class="col-sm-5">
                            <input type="file" name="file" size="60" required="true"/>
                            <p class="help-block">require .pdf</p>
                        </div>

                    </div>    
                    <div class="col-sm-12">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col"> No </th>
                                    <th scope="col"> Description </th>
                                    <th scope="col"> Place </th>
                                    <th scope="col"> Detail (Max 120 Char) </th>
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
                                        <form:input type="text" path="ticketdetail[0].place"  class="form-control col-sm-12" placeholder="Place"/>
                                        <div class="has-error">
                                            <form:errors path="ticketdetail[0].place" class="help-inline" cssClass="error"/>
                                        </div>
                                    </td>
                                    <td class="col-sm-3">
                                        <form:textarea type="text" path="ticketdetail[0].detail" class="form-control col-sm-12" placeholder="Detail"/>
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