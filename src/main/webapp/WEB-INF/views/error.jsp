<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="<c:url value="/resources/fragments/css/errorStyle.css" />">

<c:set var="title" ><spring:message code="title.error_page" /></c:set>
<%@include file="../jspf/fragments/head.jspf" %>
<body class="mdl-color--grey-100">
    <main>
        <div class="mdl-grid">
            <div class="mdl-cell mdl-cell--12-col mdl-cell--6-col-phone errorHeaderDiv">
                <h1><spring:message code="header.error" /></h1>
            </div>
            <hr />
            <div class="mdl-cell mdl-cell--6-col mdl-cell--4-col-phone errorMessageDiv">
                <p class="errorMessage">
                    ${errMessage}
                </p>
                <c:if test="${not empty redirectErrorUrl}">
                <p><a href="<c:url value='${redirectErrorUrl}' />"><spring:message code="phrases.click_to_login" /></a></p></c:if>
            </div>
        </div>
    </main>
</body>
</html>
