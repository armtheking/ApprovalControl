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
                <legend>View Budget</legend>
            </fieldset>
            <form:form id="form1" class="form-horizontal"
                       method="post" action="toEditBudget?${_csrf.parameterName}=${_csrf.token}" >





                <table class="table" id="sumBudget">
                    <thead>
                        <tr>
                            <th scope="col">No</th>
                            <th scope="col">DivisionName</th>
                            <th scope="col">MaxBudget</th>
                            <th scope="col">SumBudget</th>
                            <th scope="col">Balance</th>
                        </tr>
                    </thead>
                    <tbody id="mybody">
                        <c:forEach varStatus="varStatus" var="budget" items="${budget}">
                            <tr>
                                <td>${varStatus.count}</td>
                                <td>${budget.divisionName}</td>
                                <td>${budget.maxBudget}</td>                             
                                <td>${budget.sumBudget}</td> 
                                <c:if test="${(fn:contains(budget.balance, '-'))}"> 
                                    <td><font color="FF0000" class="doubleUnderline">${budget.balance}</font></td>
                                    </c:if>
                                    <c:if test="${!(fn:contains(budget.balance, '-'))}"> 
                                    <td><font color="0DDA02" class="doubleUnderline">${budget.balance}</font></td>
                                    </c:if>               
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <sec:authorize access="hasRole('DBA')">
                    <center>
                    <input type="submit" class="btn btn-info btn-lg" value="EDIT"  style="width: 70px;"/>
                    </center>
                </sec:authorize>
            </form:form>
        </div>

        <%@ include file="../template/footer.jspf" %>
        <%@ include file="../template/scripts.jspf" %>
    </body>
</html>
