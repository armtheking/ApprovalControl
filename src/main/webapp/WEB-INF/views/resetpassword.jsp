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
                <legend>Reset Password</legend>
            </fieldset>
            <form action="resetPassword_success?${_csrf.parameterName}=${_csrf.token}"  method="post" class="form col-md-12 center-block">
                
                <c:if test="${message eq 'Password was incorrect!'}">
                    <div class="alert alert-danger">
                        <p>${message}</p>
                    </div>
                </c:if>
                <c:if test="${message eq 'Your new password and confirm password do not match!'}">
                    <div class="alert alert-danger">
                        <p>${message}</p>
                    </div>
                </c:if>
                 <c:if test="${message eq 'Your password has been reset successfully!'}">
                    <div class="alert alert-success">
                        <p>${message}</p>
                    </div>
                </c:if>



                Old Password
                <div class="form-group">
                    <input type="password" name="oldPassword" class="form-control input-lg" placeholder="Old Password">
                </div>
                New Password
                <div class="form-group">
                    <input type="password" name="newPassword" class="form-control input-lg" placeholder="New Password">
                </div>
                Confirm New Password
                <div class="form-group">
                    <input type="password" name="confirm_newPassword" class="form-control input-lg" placeholder="Confirm New Password">
                </div>
                <div class="form-group">
                    <center>
                        <button type="submit" class="btn btn-success btn-lg">Reset Password</button>
                        <button type="reset" class="btn btn-danger btn-lg">Cancel</button>
                    </center>
                </div>
            </form>
        </div>

        <%@ include file="template/footer.jspf" %>
        <%@ include file="template/scripts.jspf" %>
    </body>
</html>
