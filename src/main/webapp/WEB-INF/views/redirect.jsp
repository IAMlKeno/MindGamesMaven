<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Re-direct Page</title>
        <script src="https://code.jquery.com/jquery-1.6.2.min.js" integrity="sha256-0W0HoDU0BfzslffvxQomIbx0Jfml6IlQeDlvsNxGDE8=" crossorigin="anonymous"></script>
    </head>
    <body>
        <script>
        <c:set var="redirect">
            <c:choose>
            <c:when test="${redirectUrl ne null}">${redirectUrl}</c:when>
            <c:otherwise>/home</c:otherwise>
            </c:choose>
        </c:set>
        $(function(){
            window.location.assign("<c:url value='${redirect}' />");
        });
        </script>
    </body>
</html>
