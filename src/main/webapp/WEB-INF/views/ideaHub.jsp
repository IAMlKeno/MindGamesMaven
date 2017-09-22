<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="idea" tagdir="/WEB-INF/tags" %>
<c:set var="title" value="Home"/>
<%@include file="../jspf/fragments/head.jspf" %>
<link rel="stylesheet" href="<c:url value="/resources/ideaHub.css" />">
<script src="<c:url value="/resources/modal/modal.js" />" type="text/javascript"></script>
<body>
    <div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-header">
    <%@include file="../jspf/fragments/menu.jspf" %>
        <main class="mdl-layout__content mdl-color--grey-100" style="min-height:600px">
            <h1>${token} - ${userId}</h1>
            <div class="newIdeaButtonDiv">
                <input type="submit" id="ideaSubmitButton" 
                    class="mdl-button mdl-js-button mdl-button--colored 
                    mdl-button--raised" value="Add New Idea" />
            </div>
            <div class="mdl-grid ideaGrid">
                <!-- using card to hold idea example-->
                <c:if test="${empty ideaList}" >No ideas yet. Hit the Add new idea button to start!</c:if>
                <c:forEach items="${ideaList}" var="ideaWrapper">
                    <idea:ideaCard ideaWrapper="${ideaWrapper}" />
                </c:forEach>
            </div>
        </main>
    </div>
    <%@include file="../jspf/modal.jspf" %>
    <%@include file="../jspf/fragments/footer.jspf" %>