<%-- 
    Document   : redirect
    Created on : 22-Aug-2017, 9:33:33 PM
    Author     : Elkeno
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Re-direct Page</title>
    </head>
    <body>
        <script>
            window.location.assign("${redirectUrl}");
        </script>
    </body>
</html>
