<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
    <head>
        <%@ include file="../template/head.jspf" %>

    </head>
    <body>

        <%@ include file="../template/pageTitle.jspf" %>

        <div class="container">
            <fieldset>
                <legend><i class="fa fa-btc"></i> Finance (Print)</legend>
            </fieldset>
            ${search}
            <form:form id="form1" class="form-horizontal" enctype="multipart/form-data"
                       modelAttribute="ticketHeader" method="post" action="printReport?${_csrf.parameterName}=${_csrf.token}"  target="_blank">
                <div class="form-group">

                    <label for="detailHeader" class="col-sm-2 control-label"><FONT color="red">*</FONT>Ticket No:</label>
                    <div class="col-sm-3">
                        <input type="text" name="ticketNo" class="form-control">
                    </div>
                    <div class="col-sm-2">
                        <input type="submit" class="btn btn-info" value="PRINT"  style="width: 70px;" />
                    </div>
                </div>
            </form:form>

        </div>
        <%@ include file="../template/footer.jspf" %>
        <%@ include file="../template/scripts.jspf" %>

    </body>
</html>
