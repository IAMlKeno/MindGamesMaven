<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="title" value="Error Page"/>
<%@include file="../jspf/fragments/head.jspf" %>

<body class="mdl-color--grey-100">
    <main>
        <div class="mdl-grid">
            <div class="mdl-cell mdl-cell--12-col" style="text-align: center">
                <h1>Uh Oh....</h1>
            </div>
            <hr />
            <div class="mdl-cell mdl-cell--8-col" 
                 style="margin: auto; border:2px lightgray groove; text-align: center">
                <p class="errorMessage">
                    ${errMessage}
                </p>
                <c:if test="${not empty redirectErrorUrl}">
                    <p><a href="<c:url value='${redirectErrorUrl}' />">Click here to login in!</a></p>
                </c:if>
            </div>
        </div>
    </main>
</body>
</html>
