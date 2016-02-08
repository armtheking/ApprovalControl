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
                       modelAttribute="ticketHTraining" method="post" action="training/createticket?${_csrf.parameterName}=${_csrf.token}" >

                <fieldset>
                    <legend><i class="fa fa-book"></i> Training</legend>
                </fieldset>
                <input type="hidden" name="ticketHeader.ticketType" value="TRN"/>
                <div class="form-group form-group-sm">
                    <label for="ticketHTraining.typeID" class="col-sm-2 control-label"><FONT color="red">*</FONT>Type of Training: </label>
                    <div class="col-sm-2">
                        <select id="typeID" name="trainingType.typeID" class="form-control" onchange="selectType();">
                            <option value="1">External</option>
                            <option value="2">In-house</option>
                            <option value="3">Internal</option>
                        </select>
                    </div>
                    <div class="col-sm-3"></div>
                    <div class="col-sm-4">

                        <select id="planID" name="trainingPlan.planID" class="form-control">
                            <option value="1">As to Training Plan</option>
                            <option value="2">Out of Training Plan</option>
                        </select>
                    </div>     
                </div>
                <div class="form-group form-group-sm">
                    <label for="detailHeader" class="col-sm-2 control-label"><FONT color="red">*</FONT>Training Title: </label>
                    <div class="col-sm-4">
                        <input type="text" id="detailHeader" name="ticketHeader.detailHeader" class="form-control" placeholder="Training Title" required/>
                    </div>               
                    <label class="col-sm-2 control-label"><FONT color="red">*</FONT>Content of Training:</label>
                    <div class="col-sm-3">
                        <input type="file" class="filestyle" id="mainfile"  name="mainfile" accept="application/pdf" required/>
                    </div> 
                </div>
                <div class="form-group form-group-sm">
                    <label for="remarkHeader" class="col-sm-2 control-label"><FONT color="red">*</FONT>Objective: </label>
                    <div class="col-sm-4">
                        <input type="text"  id="remarkHeader" name="ticketHeader.remarkHeader" class="form-control" placeholder="Objective" required/>
                    </div>
                </div>

                <div class="form-group form-group-sm">
                    <label for="ticketHTraining.startdate" class="col-sm-2 control-label">Start Date: </label> 
                    <div class="col-sm-4">
                        <div class="input-group">  
                            <input id="startDate" name="startDate" class="form-control" value="" placeholder="Start Date"  required/>
                            <span class="input-group-addon">
                                <span class="fa fa-calendar" aria-hidden="true"></span>
                            </span>
                        </div>
                    </div>  
                    <label for="ticketHTraining.enddate" class="col-sm-2 control-label">End Date: </label> 
                    <div class="col-sm-4">
                        <div class="input-group">  
                            <input id="endDate" name="endDate" class="form-control" value=""  placeholder="End Date"  required/>
                            <span class="input-group-addon">
                                <span class="fa fa-calendar" aria-hidden="true"></span>
                            </span>
                        </div>
                    </div>  
                </div>    

                <div class="form-group form-group-sm">
                    <label for="ticketHTraining.place" class="col-sm-2 control-label">Place: </label> 
                    <div class="col-sm-4">
                        <input type="text"  id="place" name="place" class="form-control" placeholder="Place" />
                    </div>  
                    <label for="ticketHTraining.organizeby" class="col-sm-2 control-label">Organized by: </label> 
                    <div class="col-sm-4">
                        <input type="text"  id="organizeBy" name="organizeBy" class="form-control" placeholder="Organized By"/>
                    </div>   
                </div>    

                <div class="form-group form-group-sm">
                    <label for="ticketHTraining.paymentid" class="col-sm-2 control-label"><FONT color="red">*</FONT>Payment: </label> 
                    <div class="col-sm-4">

                        <select id="paymentID" name="trainingPayment.paymentID" class="form-control">
                            <option value="1">Cheque</option>
                            <option value="2">Bank Transfer</option>
                            <option value="3">Credit</option>
                            <option value="4">Cash</option>
                        </select>
                    </div>  
                    <label for="ticketHTraining.paymentDate" class="col-sm-2 control-label">Date of Payment: </label> 
                    <div class="col-sm-4">
                        <div class="input-group">  
                            <input id="paymentDate" name="paymentDate" class="form-control" value=""  placeholder="Date of Payment" required/>
                            <span class="input-group-addon">
                                <span class="fa fa-calendar" aria-hidden="true"></span>
                            </span>
                        </div> </div>
                </div>    

                <div class="form-group form-group-sm">
                     <label class="col-sm-2 control-label"><FONT color="red">*</FONT>Total Person:</label>
                    <div class="col-sm-4">
                        <input type="text"  id="totalPerson" name="totalPerson" class="form-control" placeholder="Total Persons" required/>
                    </div>
                     
                      <label class="col-sm-2 control-label">List of Participant:</label>
                    <div class="col-sm-3">
                        <input type="file" class="filestyle" id="listfile" name="listfile" accept="application/pdf" />
                    </div> 
                     
                </div>
                
                <div class="form-group form-group-sm">
                    <label class="col-sm-2 control-label">List of Participant: </label>  
                    <div class="col-sm-24">
                        <table id="myTbl" border="0" >  
                            <tbody id="mybody">
                                <tr id="tr_participant">  
                                    <td>1.&nbsp;</td>
                                    <td  style="width:200px">
                                        <input type="text"  id="empid0" name="ticketHeader.trainingParticipant[0].empID" class="form-control" placeholder="EmployeeID" />
                                    </td>
                                    <td  style="width:200px">
                                        <input type="text"  id="firstname0" name="ticketHeader.trainingParticipant[0].firstName" class="form-control" placeholder="Firstname" />
                                    </td>
                                    <td style="width:200px">
                                        <input type="text"  id="lastname0" name="ticketHeader.trainingParticipant[0].lastName" class="form-control" placeholder="Lastname" />
                                    </td>
                                    <td style="width:200px">
                                        <input type="text"  id="section0" name="ticketHeader.trainingParticipant[0].section" class="form-control" placeholder="Section" />
                                    </td>
                                    <td> &nbsp;<button id="addRow" class="btn btn-success" style="width: 35px;"  type="button" onclick="addRowParticipant();">+</button> </td>
                                    <td><button id="delRow" class="btn btn-danger"  style="width: 35px;"  type="button" onclick="deleteRowParticipant();">-</button> </td>
                                </tr>  
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
                                <th scope="col">Details</th>
                                <th scope="col">Amount</th>
                                <th scope="col"></th>
                            </tr>
                        </thead>
                        <tbody id="mybody2">

                            <tr>
                                <td>1</td>
                                <td><input type="hidden" id="item0" name="ticketHeader.ticketDTraining[0].item" value="Hotel"/>Hotel</td>
                                <td> 
                                    <textarea class="form-control" id="budgetDetail0" name="ticketHeader.ticketDTraining[0].budgetDetail" style=" resize: none;">  </textarea> 
                                </td>
                                <td>
                                    <input type="text" id="amount0" name="ticketHeader.ticketDTraining[0].amount" class="form-control"  value="0" required/>
                                </td>
                                <td><button id="addRow" class="btn btn-success" style="width: 35px; float: left"  type="button" onclick="addRowBudget();">+</button> 
                                    <button id="delRow" class="btn btn-danger"  style="width: 35px;"  type="button" onclick="deleteRowBudget();">-</button> 
                                </td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td><input type="hidden" id="item1" name="ticketHeader.ticketDTraining[1].item" value="Break / Lunch"/>Break / Lunch</td>
                                <td> 
                                    <textarea class="form-control" id="budgetDetail1" name="ticketHeader.ticketDTraining[1].budgetDetail" style=" resize: none;">  </textarea> 
                                </td>
                                <td>
                                    <input type="text" id="amount1" name="ticketHeader.ticketDTraining[1].amount" class="form-control"  value="0" required/>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td><input type="hidden" id="item2" name="ticketHeader.ticketDTraining[2].item" value="Materials"/>Materials</td>
                                <td> 
                                    <textarea class="form-control" id="budgetDetail2" name="ticketHeader.ticketDTraining[2].budgetDetail" style=" resize: none;">  </textarea> 
                                </td>
                                <td>
                                    <input type="text" id="amount2" name="ticketHeader.ticketDTraining[2].amount" class="form-control"   value="0" required/>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>4</td>
                                <td><input type="hidden" id="item3" name="ticketHeader.ticketDTraining[3].item" value="Lecturer"/>Lecturer</td>
                                <td> 
                                    <textarea class="form-control" id="budgetDetail3" name="ticketHeader.ticketDTraining[3].budgetDetail" style=" resize: none;">  </textarea> 
                                </td>
                                <td>
                                    <input type="text" id="amount3" name="ticketHeader.ticketDTraining[3].amount" class="form-control"   value="0" required/>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>5</td>
                                <td><input type="hidden" id="item4" name="ticketHeader.ticketDTraining[4].item" value="Transportation"/>Transportation</td>
                                <td> 
                                    <textarea class="form-control" id="budgetDetail4" name="ticketHeader.ticketDTraining[4].budgetDetail" style=" resize: none;">  </textarea> 
                                </td>
                                <td>
                                    <input type="text" id="amount4" name="ticketHeader.ticketDTraining[4].amount" class="form-control"   value="0" required/>
                                </td>
                                <td></td>
                            </tr>
                               <tr>
                                <td>6</td>
                                <td><input type="hidden" id="item5" name="ticketHeader.ticketDTraining[5].item" value="Training Fee ( External Training )"/>Training Fee ( External Training )</td>
                                <td> 
                                    <textarea class="form-control" id="budgetDetail5" name="ticketHeader.ticketDTraining[5].budgetDetail" style=" resize: none;">  </textarea> 
                                </td>
                                <td>
                                    <input type="text" id="amount5" name="ticketHeader.ticketDTraining[5].amount" class="form-control"   value="0" required/>
                                </td>
                                <td></td>
                            </tr>

                        </tbody>

                    </table>

                </div>

                <center>
                    <input type="submit" class="btn btn-primary" value="NEXT"  style="width: 70px;"/>
                    <button type="reset" class="btn btn-danger">CANCEL</button>
                </center>
            </form:form>
        </div>
        <%@ include file="../template/footer.jspf" %>
        <%@ include file="../template/scripts.jspf" %>
    </body>
</html>
