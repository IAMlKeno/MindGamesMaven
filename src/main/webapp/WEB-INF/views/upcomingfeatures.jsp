<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="idea" tagdir="/WEB-INF/tags" %>
<c:set var="title"><spring:message code='title.upcoming_page' /></c:set>
<%@include file="../jspf/fragments/head.jspf" %>
<link rel="stylesheet" href="<c:url value="/resources/ideaHub.css" />">
<link rel="stylesheet" href="<c:url value="/resources/fragments/css/errorStyle.css" />">
<body>
    <div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-header">
        <%@include file="../jspf/fragments/menu.jspf" %>
        <main class="mdl-layout__content mdl-color--grey-100" style="min-height:600px">
            <div class="mdl-grid">
                <div class="mdl-cell mdl-cell--12-col mdl-cell--6-col-phone errorHeaderDiv">
                    <h1><spring:message code="header.upcoming" /></h1>
                </div>
                <hr />
                <div class="mdl-cell mdl-cell--6-col mdl-cell--4-col-phone errorMessageDiv" style='padding:5px'>
                    <ul class="mdl-list" style="height:auto; width: 70%; margin:auto">
                        <li class="mdl-list__item mdl-list__item--three-line">
                            <span class="mdl-list__item-primary-content">
                                <span>
                                    Exporting idea as text file
                                </span>
                                <span class="mdl-list__item-text-body">
                                    The app will be able to export the idea and
                                    its features as a printable text document.
                                </span>
                            </span>
                        </li>
                        <li class="mdl-list__item mdl-list__item--three-line">
                            <span class="mdl-list__item-primary-content">
                                <span>
                                    Export idea to email
                                </span>
                                <span class="mdl-list__item-text-body">
                                    The app will access you local email 
                                    application and create an email with the 
                                    specifics of the idea.
                                </span>
                            </span>
                        </li>
                        <li class="mdl-list__item mdl-list__item--three-line">
                            <span class="mdl-list__item-primary-content">
                                <span>
                                    Automatic Rating System
                                </span>
                                <span class="mdl-list__item-text-body">
                                    The app will take certain criteria into 
                                    consideration and provide an automatic rating
                                    for <i>unrated</i> ideas.
                                </span>
                            </span>
                        </li>
                        <li class="mdl-list__item mdl-list__item--three-line">
                            <span class="mdl-list__item-primary-content">
                                <span>
                                    Sorting Ideas
                                </span>
                                <span class="mdl-list__item-text-body">
                                    The app's Idea Hub will allow you to sort
                                    ideas based on rating, idea title, in 
                                    progress or complete.
                                </span>
                            </span>
                        </li>
                    </ul>
                    <br />
                    <a href="<c:url value='/auth' />">
                    <c:choose><c:when test="${userId ne null}">Click here to continue</c:when><c:otherwise>Click here to login</c:otherwise></c:choose>
                    </a>
                </div>
            </div>
        </main>
    </div>
    <%@include file="../jspf/fragments/footer.jspf" %>