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
            <fieldset>
                <legend>Clear - Advance</legend>
            </fieldset>
            <form:form id="form1" class="form-horizontal" enctype="multipart/form-data"
                       modelAttribute="ticketHeader" method="post" action="advance/preview_clear?${_csrf.parameterName}=${_csrf.token}" >

                <input type="hidden" name="ticketType" value="ADV"/>
                <input type="hidden" name="dateAlert" value="7"/>
                <input type="hidden" name="ticketNo" value="${ticketHeader.ticketNo}"/>
                <form:input type="hidden" path="applicationName" value="${ticketHeader.applicationName}" id="applicationName" class="form-control" />

                <div class="form-group form-group-sm">

                    <label for="detailHeader" class="col-sm-2 control-label"><FONT color="red">*</FONT>Objective / Reason:</label>
                    <div class="col-sm-4">
                        <form:input type="text"  path="detailHeader" class="form-control" placeholder="Objective / Reason" />
                        <div class="has-error">
                            <form:errors path="detailHeader" class="help-inline"/>
                        </div> </div>    
                    <div class="col-sm-5">
                        <label><span style="color: red;"> ** </span> Car license (In case for driver), Submit job to customs /Month Meeting /Training /Support working at branch 
                            , Contact Officer /Customer  </label>
                    </div>

                </div>
                <div class="form-group form-group-sm">
                    <label for="dateAlert" class="col-sm-2 control-label">Settlement Within: </label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" value="7 Days" disabled/> </div>
                </div>
                <div class="form-group form-group-sm">
                     <label for="dateAlert" class="col-sm-2 control-label"><FONT color="red">*</FONT>File Input: </label>
                    <div class="col-sm-3">
                        <input type="file" class="filestyle" id="file" name="file" accept="application/pdf"  required   />
                        <p class="help-block"><FONT color="red">require .pdf</FONT></p>
                    </div>

                </div>   



                <div class="col-sm-12">
                    <table class="table" id="tbbudget">
                        <thead>
                            <tr>
                                <th scope="col">No</th>
                                <th scope="col" width="420px">Description <FONT color="red">**Don't duplicate each line</FONT></th>
                                <th scope="col">Details</th>
                                <th scope="col" width="200px">Amount</th>
                            </tr>
                        </thead>
                        <tbody id="mybody2">
                            <tr>
                                <td>1</td>
                                <td> <form:select path="ticketdetail[0].financeChargeCode.id"   class="form-control input-sm">
                                        <form:option value="0">------------------------------------ S E L E C T ------------------------------------</form:option>
                                        <form:options items="${chargeCode}" itemValue="id" itemLabel="description" />
                                    </form:select>
                                    <div class="has-error">
                                        <form:errors path="ticketdetail[0].financeChargeCode.id" class="help-inline"/>
                                    </div>
                                </td>
                                <td> 
                                    <form:textarea type="text" rows="5" path="ticketdetail[0].detail" class="form-control col-sm-12" placeholder="Details"/>
                                </td>
                                <td>
                                    <form:input type="text" path="ticketdetail[0].amount" class="form-control" placeholder="Amount" />
                                </td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td> <form:select path="ticketdetail[1].financeChargeCode.id"   class="form-control input-sm">
                                        <form:option value="0">------------------------------------ S E L E C T ------------------------------------</form:option>
                                        <form:options items="${chargeCode}" itemValue="id" itemLabel="description" />
                                    </form:select>
                                    <div class="has-error">
                                        <form:errors path="ticketdetail[1].financeChargeCode.id" class="help-inline"/>
                                    </div>
                                </td>
                                <td> 
                                    <form:textarea type="text" rows="5" path="ticketdetail[1].detail" class="form-control col-sm-12" placeholder="Details"/>
                                </td>
                                <td>
                                    <form:input type="text" path="ticketdetail[1].amount" class="form-control" placeholder="Amount" />
                                </td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td> <form:select path="ticketdetail[2].financeChargeCode.id"   class="form-control input-sm">
                                        <form:option value="0">------------------------------------ S E L E C T ------------------------------------</form:option>
                                        <form:options items="${chargeCode}" itemValue="id" itemLabel="description" />
                                    </form:select>
                                    <div class="has-error">
                                        <form:errors path="ticketdetail[2].financeChargeCode.id" class="help-inline"/>
                                    </div>
                                </td>
                                <td> 
                                    <form:textarea type="text" rows="5" path="ticketdetail[2].detail" class="form-control col-sm-12" placeholder="Details"/>
                                </td>
                                <td>
                                    <form:input type="text" path="ticketdetail[2].amount" class="form-control" placeholder="Amount" /></td>
                            </tr>
                            <tr>
                                <td>4</td>
                                <td> <form:select path="ticketdetail[3].financeChargeCode.id"   class="form-control input-sm">
                                        <form:option value="0">------------------------------------ S E L E C T ------------------------------------</form:option>
                                        <form:options items="${chargeCode}" itemValue="id" itemLabel="description" />
                                    </form:select>
                                    <div class="has-error">
                                        <form:errors path="ticketdetail[3].financeChargeCode.id" class="help-inline"/>
                                    </div>
                                </td>
                                <td> 
                                    <form:textarea type="text" rows="5" path="ticketdetail[3].detail" class="form-control col-sm-12" placeholder="Details"/>
                                </td>
                                <td>
                                    <form:input type="text" path="ticketdetail[3].amount" class="form-control" placeholder="Amount" />
                                </td>
                            </tr>

                        </tbody>

                    </table>

                </div>

                <center>
                    <input type="submit" class="btn btn-primary" value="SUBMIT"  style="width: 70px;"/>
                    <button type="reset" class="btn btn-danger">CANCEL</button>
                </center>
            </form:form>




        </div>


        <%@ include file="template/footer.jspf" %>
        <%@ include file="template/scripts.jspf" %>

    </body>
</html>
