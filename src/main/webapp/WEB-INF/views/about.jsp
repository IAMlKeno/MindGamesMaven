<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="idea" tagdir="/WEB-INF/tags" %>
<c:set var="title"><spring:message code='title.about_page' /></c:set>
<%@include file="../jspf/fragments/head.jspf" %>
<link rel="stylesheet" href="<c:url value="/resources/ideaHub.css" />">
<link rel="stylesheet" href="<c:url value="/resources/fragments/css/errorStyle.css" />">
<body>
    <div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-header">
        <%@include file="../jspf/fragments/menu.jspf" %>
        <main class="mdl-layout__content mdl-color--grey-100" style="min-height:600px">
            <div class="mdl-grid">
                <div class="mdl-cell mdl-cell--12-col mdl-cell--6-col-phone errorHeaderDiv">
                    <h1><spring:message code="header.about" /></h1>
                </div>
                <hr />
                <div class="mdl-cell mdl-cell--6-col mdl-cell--4-col-phone errorMessageDiv">
                    <p class="errorMessage" style="padding:5px">
                        Mind games web app is an application intended to help
                        you plan or decide what your next project-venture should 
                        be.
                        <br />
                        You can use the Mind Games web application to build and 
                        develop your next great idea!
                        <br />
                        It encompasses using a mind map to visually represent 
                        your great ideas and the features that you intend to 
                        build with them!
                        <br />
                        What are you waiting for? Get started!<br /><br />
                        <a href="<c:url value='/auth' />">
                            <c:choose>
                                <c:when test="${userId ne null}">Click here to continue</c:when>
                                <c:otherwise>Click here to login</c:otherwise>
                            </c:choose>
                        </a>
                    </p>
                </div>
            </div>  
        </main>
    </div>
    <%@include file="../jspf/fragments/footer.jspf" %>