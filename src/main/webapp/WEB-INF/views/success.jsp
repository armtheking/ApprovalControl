<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Enrollment Detail Confirmation</title>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <link href="<c:url value='/resources/css/bootstrap.css' />" rel="stylesheet"></link>
        <link href="<c:url value='/resources/css/app.css' />" rel="stylesheet"></link>
    </head>
    <body>
        <div class="success">
            Confirmation message : ${success}
            <br>
            
        </div>
    </body>
</html>