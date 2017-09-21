<%-- 
    Document   : redirect
    Created on : 22-Aug-2017, 9:33:33 PM
    Author     : Elkeno
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Re-direct Page</title>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js" type="text/javascript"></script>
    </head>
    <body>
        <script>
            <c:set var="redirect" >
                <c:choose>
                    <c:when test="${redirectUrl ne null}">
                        ${redirectUrl}
                    </c:when>
                    <c:otherwise>
                       /auth
                    </c:otherwise>
                </c:choose>
            </c:set>
            $(function(){
                window.location.assign("<c:url value='${redirect}' />");
            });
        </script>
    </body>
</html>
