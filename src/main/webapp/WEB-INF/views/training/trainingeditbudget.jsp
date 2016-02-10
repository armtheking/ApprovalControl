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
            <fieldset>
                <legend>Edit Budget</legend>
            </fieldset>
            <form:form id="form1" class="form-horizontal"  modelAttribute="divisionSumBudgetList"
                       method="post" action="editBudgetSuccess?${_csrf.parameterName}=${_csrf.token}" >

                <table class="table" id="sumBudget">
                    <thead>
                        <tr>
                            <th scope="col">No</th>
                            <th scope="col">DivisionName</th>
                            <th scope="col">MaxBudget</th>
                        </tr>
                    </thead>
                    <tbody id="mybody">
                        <c:forEach varStatus="varStatus" var="budget" items="${budget}">
                            <tr>
                                
                                <td>${varStatus.count}
                                <input type="hidden" name="divisionSumBudgets[${varStatus.count-1}].divisionCode" value="${budget.divisionCode}" />
                                </td>
                                <td>${budget.divisionName}</td>
                                <td><input type="text" class="form-control" name="divisionSumBudgets[${varStatus.count-1}].maxBudget" style="width: 150px; text-align: right;" value="${budget.maxBudget}"/></td>                                        
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <center>
                    <input type="submit" class="btn btn-success btn-lg" value="SAVE" />
                    <input type="reset" class="btn btn-danger btn-lg" value="CANCEL"  />
                </center>
            </form:form>
        </div>

        <%@ include file="../template/footer.jspf" %>
        <%@ include file="../template/scripts.jspf" %>
    </body>
</html>
