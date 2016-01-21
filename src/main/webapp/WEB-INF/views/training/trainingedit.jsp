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
                       modelAttribute="ticketHTraining" method="post" action="edit?${_csrf.parameterName}=${_csrf.token}">

                <fieldset>
                    <legend>Training</legend>
                </fieldset>

                <input type="hidden" name="ticketHeader.ticketType" value="TRN"/>
                <input type="hidden" name="ticketHeader.ticketNo" value="${ticketHTraining.ticketHeader.ticketNo}"/>
                <input type="hidden" name="ticketHeader.item" value="${ticketHTraining.ticketHeader.item}"/>


                <div class="form-group form-group-sm">
                    <label for="ticketHTraining.typeID" class="col-sm-2 control-label"><FONT color="red">*</FONT>Type of Training: </label>
                    <div class="col-sm-2">
                        <select id="typeID" name="trainingType.typeID" class="form-control" onchange="selectType();" />
                        <option value="0" label="--- Select ---"/>
                        <c:if test="${ticketHTraining.trainingType.typeID==1}">
                            <option value="1" selected>External</option>
                            <option value="2">In-house</option>
                            <option value="3">Internal</option>
                        </c:if>
                        <c:if test="${ticketHTraining.trainingType.typeID==2}">
                            <option value="1">External</option>
                            <option value="2" selected>In-house</option>
                            <option value="3">Internal</option>
                        </c:if>
                        <c:if test="${ticketHTraining.trainingType.typeID==3}">
                            <option value="1">External</option>
                            <option value="2">In-house</option>
                            <option value="3" selected>Internal</option>
                        </c:if>
                        </select>
                    </div>
                    <div class="col-sm-3"></div>
                    <div class="col-sm-4">
                        <select id="planID" name="trainingPlan.planID" class="form-control" />       
                        <option value="0" label="--- Select ---"/>
                        <c:if test="${ticketHTraining.trainingPlan.planID==1}">
                            <option value="1" selected>As to Training Plan</option>
                            <option value="2">Out of Training Plan</option>
                        </c:if>
                        <c:if test="${ticketHTraining.trainingPlan.planID==2}">
                            <option value="1">As to Training Plan</option>
                            <option value="2" selected>Out of Training Plan</option>
                        </c:if>
                        </select>
                    </div>     
                </div>
                <div class="form-group form-group-sm">
                    <label for="detailHeader" class="col-sm-2 control-label"><FONT color="red">*</FONT>Training Title: </label>
                    <div class="col-sm-4">
                        <input type="text" id="detailHeader" name="ticketHeader.detailHeader" class="form-control" placeholder="Training Title" value="${ticketHTraining.ticketHeader.detailHeader}" required/>
                    </div>               
                    <label class="col-sm-2 control-label">Content of Training:</label>
                    <div class="col-sm-3">
                        <input type="file" class="filestyle" id="mainfile" name="mainfile"/>
                    </div> 
                </div>
                <div class="form-group form-group-sm">
                    <label for="remarkHeader" class="col-sm-2 control-label"><FONT color="red">*</FONT>Objective: </label>
                    <div class="col-sm-4">
                        <input type="text"  id="remarkHeader" name="ticketHeader.remarkHeader" class="form-control" placeholder="Objective" value="${ticketHTraining.ticketHeader.remarkHeader}" required/>
                    </div>
                </div>

                <div class="form-group form-group-sm">
                    <label for="ticketHTraining.startdate" class="col-sm-2 control-label">Start Date: </label> 
                    <div class="col-sm-4">
                        <div class="input-group">  
                            <input id="startDate" name="startDate" class="form-control" placeholder="Start Date" value="${ticketHTraining.startDate}" required/>
                            <span class="input-group-addon">
                                <span class="fa fa-calendar" aria-hidden="true"></span>
                            </span>
                        </div>
                    </div>  
                    <label for="ticketHTraining.enddate" class="col-sm-2 control-label">End Date: </label> 
                    <div class="col-sm-4">
                        <div class="input-group">  
                            <input id="endDate" name="endDate" class="form-control" placeholder="End Date" value="${ticketHTraining.endDate}" required/>
                            <span class="input-group-addon">
                                <span class="fa fa-calendar" aria-hidden="true"></span>
                            </span>
                        </div>
                    </div>  
                </div>    

                <div class="form-group form-group-sm">
                    <label for="ticketHTraining.place" class="col-sm-2 control-label">Place: </label> 
                    <div class="col-sm-4">
                        <input type="text"  id="place" name="place" class="form-control" placeholder="Place" value="${ticketHTraining.place}"/>
                    </div>  
                    <label for="ticketHTraining.organizeby" class="col-sm-2 control-label">Organized by: </label> 
                    <div class="col-sm-4">
                        <input type="text"  id="organizeBy" name="organizeBy" class="form-control" placeholder="Organized By" value="${ticketHTraining.organizeBy}"/>
                    </div>   
                </div>    

                <div class="form-group form-group-sm">
                    <label for="ticketHTraining.paymentid" class="col-sm-2 control-label"><FONT color="red">*</FONT>Payment: </label> 
                    <div class="col-sm-4">

                        <select id="paymentID" name="trainingPayment.paymentID" class="form-control">
                            <option value="0" label="--- Select ---"/>
                            <c:if test="${ticketHTraining.trainingPayment.paymentID==1}">
                                <option value="1" selected>Cheque</option>
                                <option value="2">Bank Transfer</option>
                                <option value="3">Credit</option>
                                <option value="4">Cash</option>
                            </c:if>
                            <c:if test="${ticketHTraining.trainingPayment.paymentID==2}">
                                <option value="1">Cheque</option>
                                <option value="2" selected>Bank Transfer</option>
                                <option value="3">Credit</option>
                                <option value="4">Cash</option>
                            </c:if>
                            <c:if test="${ticketHTraining.trainingPayment.paymentID==3}">
                                <option value="1">Cheque</option>
                                <option value="2">Bank Transfer</option>
                                <option value="3" selected>Credit</option>
                                <option value="4">Cash</option>
                            </c:if>
                            <c:if test="${ticketHTraining.trainingPayment.paymentID==4}">
                                <option value="1">Cheque</option>
                                <option value="2">Bank Transfer</option>
                                <option value="3">Credit</option>
                                <option value="4" selected>Cash</option>
                            </c:if>
                        </select>
                        <!--                                       
                                                <input type="checkbox" id="cheque" name="ticketHTraining.paymentID" value="cheque" onclick="CheckCheque()" /> Cheque
                                                <input type="checkbox" id="bank" name="ticketHTraining.paymentID" value="bank" onclick="CheckBank()"/> Bank Transfer
                                                <input type="checkbox" id="credit" name="ticketHTraining.paymentID" value="credit" onclick="CheckCredit()"/> Credit
                                                <input type="checkbox" id="cash" name="ticketHTraining.paymentID" value="cash" onclick="CheckCash()"/> Cash-->
                    </div>  
                    <label for="ticketHTraining.paymentDate" class="col-sm-2 control-label">Date of Payment: </label> 
                    <div class="col-sm-4">
                        <div class="input-group">  
                            <input id="paymentDate" name="paymentDate" class="form-control" placeholder="Date of Payment" value="${ticketHTraining.paymentDate}" required/>
                            <span class="input-group-addon">
                                <span class="fa fa-calendar" aria-hidden="true"></span>
                            </span>
                        </div> </div>
                </div>    


                <div class="form-group form-group-sm">
                    <label class="col-sm-2 control-label"><FONT color="red">*</FONT>Total Person:</label>
                    <div class="col-sm-4">
                        <input type="text"  id="totalPerson" name="totalPerson" class="form-control" placeholder="Total Persons" value="${ticketHTraining.totalPerson}" required/>
                    </div> 
                    <label class="col-sm-2 control-label">List of Participant:</label>
                    <div class="col-sm-3">
                        <input type="file" class="filestyle" id="listfile" name="listfile" />
                    </div> 
                </div>            



                <div class="form-group form-group-sm">
                    <label class="col-sm-2 control-label">List of Participant: </label> 
                    <div class="col-sm-24">
                        <table id="myTbl" border="0" >  
                            <tbody id="mybody">
                                <c:forEach varStatus="varStatus" var="trainingParticipant" items="${trainingParticipant}">
                                    <tr>  
                                        <td>
                                            ${varStatus.index+1}.&nbsp;
                                        </td>
                                        <td  style="width:200px">
                                            <input type="text"  id="empid[${varStatus.index}]" name="ticketHeader.trainingParticipant[${varStatus.index}].empID" class="form-control" value="${trainingParticipant.empID}" placeholder="EmployeeID" />
                                        </td>  
                                        <td  style="width:200px">
                                            <input type="text"  id="firstname[${varStatus.index}]" name="ticketHeader.trainingParticipant[${varStatus.index}].firstName" class="form-control" value="${trainingParticipant.firstName}" placeholder="Firstname"/>
                                        </td>
                                        <td  style="width:200px">
                                            <input type="text"  id="lastname[${varStatus.index}]" name="ticketHeader.trainingParticipant[${varStatus.index}].lastName" class="form-control" value="${trainingParticipant.lastName}" placeholder="Lastname"/>
                                        </td>
                                        <td  style="width:200px">
                                            <input type="text"  id="section[${varStatus.index}]" name="ticketHeader.trainingParticipant[${varStatus.index}].section" class="form-control" value="${trainingParticipant.section}" placeholder="Section"/>
                                        </td>

                                        <c:if test="${varStatus.index < 1}" >
                                            <td>&nbsp;<button id="addRow" class="btn btn-success" style="width: 35px;"  type="button" onclick="addRowParticipant();">+</button> </td>
                                            <td><button id="delRow" class="btn btn-danger"  style="width: 35px;"  type="button" onclick="deleteRowParticipant();">-</button> </td>
                                        </c:if>
                                    </tr> 
                                </c:forEach>
                            </tbody>
                        </table>    
                    </div>

                </div>



                <div class="col-sm-12">
                    <table class="table" id="tbbudget">
                        <thead>
                            <tr>

                                <th scope="col">No</th>
                                <th scope="col">Item</th>
                                <th scope="col">Detail</th>
                                <th scope="col">Amount</th>
                                <th scope="col"></th>
                            </tr>
                        </thead>
                        <tbody id="mybody2">
                            <tr>
                                <td>1</td>
                                <td><input type="hidden" id="item0" name="ticketHeader.ticketDTraining[0].item" value="Hotel"/>Hotel</td>
                                <td> 
                                    <form:textarea class="form-control" id="budgetDetail0" style=" resize: none;" path="ticketHeader.ticketDTraining[0].budgetDetail" value="${ticketHTraining.ticketHeader.ticketDTraining[0].budgetDetail}" />  
                                </td>
                                <td>
                                    <input type="text" id="amount0" name="ticketHeader.ticketDTraining[0].amount" class="form-control"  value="${ticketHTraining.ticketHeader.ticketDTraining[0].amount}" />
                                </td>
                                <td><button id="addRow" class="btn btn-success" style="width: 35px; float: left"  type="button" onclick="addRowBudget();">+</button> 
                                    <button id="delRow" class="btn btn-danger"  style="width: 35px;"  type="button" onclick="deleteRowBudget();">-</button> 
                                </td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td><input type="hidden" id="item1" name="ticketHeader.ticketDTraining[1].item" value="Break / Lunch"/>Break / Lunch</td>
                                <td> 
                                    <form:textarea class="form-control" id="budgetDetail1" style=" resize: none;" path="ticketHeader.ticketDTraining[1].budgetDetail" value="${ticketHTraining.ticketHeader.ticketDTraining[1].budgetDetail}" />   
                                </td>
                                <td>
                                    <input type="text" id="amount1" name="ticketHeader.ticketDTraining[1].amount" class="form-control"  value="${ticketHTraining.ticketHeader.ticketDTraining[1].amount}"/>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td><input type="hidden" id="item2" name="ticketHeader.ticketDTraining[2].item" value="Materials"/>Materials</td>
                                <td> 
                                    <form:textarea class="form-control" id="budgetDetail2" style=" resize: none;" path="ticketHeader.ticketDTraining[2].budgetDetail" value="${ticketHTraining.ticketHeader.ticketDTraining[2].budgetDetail}" />   
                                </td>
                                <td>
                                    <input type="text" id="amount2" name="ticketHeader.ticketDTraining[2].amount" class="form-control"  value="${ticketHTraining.ticketHeader.ticketDTraining[2].amount}"/>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>4</td>
                                <td><input type="hidden" id="item3" name="ticketHeader.ticketDTraining[3].item" value="Lecturer"/>Lecturer</td>
                                <td> 
                                    <form:textarea class="form-control" id="budgetDetail3" style=" resize: none;" path="ticketHeader.ticketDTraining[3].budgetDetail" value="${ticketHTraining.ticketHeader.ticketDTraining[3].budgetDetail}" />
                                </td>
                                <td>
                                    <input type="text" id="amount3" name="ticketHeader.ticketDTraining[3].amount" class="form-control"   value="${ticketHTraining.ticketHeader.ticketDTraining[3].amount}"/>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>5</td>
                                <td><input type="hidden" id="item4" name="ticketHeader.ticketDTraining[4].item" value="Transportation"/>Transportation</td>
                                <td> 
                                    <form:textarea class="form-control" id="budgetDetail4" style=" resize: none;" path="ticketHeader.ticketDTraining[4].budgetDetail" value="${ticketHTraining.ticketHeader.ticketDTraining[4].budgetDetail}" />  
                                </td>
                                <td>
                                    <input type="text" id="amount4" name="ticketHeader.ticketDTraining[4].amount" class="form-control"   value="${ticketHTraining.ticketHeader.ticketDTraining[4].amount}" />
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>6</td>
                                <td><input type="hidden" id="item5" name="ticketHeader.ticketDTraining[5].item" value="Training Fee (External Training)"/>Training Fee (External Training)</td>
                                <td> 
                                    <form:textarea class="form-control" id="budgetDetail5" style=" resize: none;" path="ticketHeader.ticketDTraining[5].budgetDetail" value="${ticketHTraining.ticketHeader.ticketDTraining[5].budgetDetail}" />  
                                </td>
                                <td>
                                    <input type="text" id="amount5" name="ticketHeader.ticketDTraining[5].amount" class="form-control"   value="${ticketHTraining.ticketHeader.ticketDTraining[5].amount}" />
                                </td>
                                <td></td>
                            </tr>

                            <c:forEach varStatus="varStatus" var="ticketDTraining" items="${ticketDTraining}">
                                <tr>
                                    <td>${varStatus.index+7}</td>
                                    <td><input type="text" class="form-control" id="item${varStatus.index+6}" name="ticketHeader.ticketDTraining[${varStatus.index+6}].item" value="${ticketDTraining.item}"/></td>
                                    <td> 
                                        <form:textarea id="budgetDetail${varStatus.index+6}" class="form-control"  style=" resize: none;" path="ticketHeader.ticketDTraining[${varStatus.index+6}].budgetDetail" value="${ticketDTraining.budgetDetail}" />
                                    </td>
                                    <td>
                                        <input type="text" id="amount${varStatus.index+6}" name="ticketHeader.ticketDTraining[${varStatus.index+6}].amount" class="form-control"   value="${ticketDTraining.amount}"/>
                                    </td>
                                    <td></td>
                                </tr>     
                            </c:forEach>
                        </tbody>

                    </table>

                </div>

                <center>
                    <!--                     <button class="btn btn-primary" onclick="goBack()">Go Back</button>-->
                    <input type="submit" class="btn btn-primary" value="SUBMIT"  style="width: 70px;" onclick="setSession();"/>
                    <button type="reset" class="btn btn-danger">CANCEL</button>
                </center>
            </form:form>
        </div>
        <%@ include file="../template/footer.jspf" %>
        <%@ include file="../template/scripts.jspf" %>
    </body>
</html>
