<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@attribute name="ideaWrapper" 
             type="com.portfolio.elkeno_jones.mindgamesmaven.model.IdeaWithFeatures" 
             required="true" %>

<link rel="stylesheet" href="<c:url value="/resources/fragments/css/ideaCardStyle.css" />">

<form action="<c:url value='/develop' />" method="POST">
    <input type="hidden" name="ideaId" value="${ideaWrapper.idea.ideaId}" />
    <div class="mdl-card mdl-shadow--2dp ideaCard">
        <div class="mdl-card__title ideaTitleDiv">
            <h4 class="mdl-card__title-text ideaTitle" title="${ideaWrapper.idea.ideaTitle}">${ideaWrapper.idea.ideaTitle}            </h4>
            <span class="rating">&#40;${ideaWrapper.idea.rating}&#41;</span>
            </div>
            <div class="statusCheckbox">
                <c:if test="${ideaWrapper.idea.dateCreated ne null}"><span><small>Created: ${ideaWrapper.idea.createdDate}</small></span></c:if>
            </div>
            <div class="statusCheckbox">
                <label><spring:message code="label.in_progress" /></label>: <input type="checkbox" class="inProgressCheckbox" <c:if test="${ideaWrapper.idea.isInProgress}">checked</c:if> />
            </div>
            <div class="statusCheckbox">
                <label><spring:message code="label.complete" /></label>: <input type="checkbox" class="completeCheckbox" <c:if test="${ideaWrapper.idea.isCompleted}">checked</c:if> />
            </div>
            <div class="mdl-card__supporting-text">
                <h5 class="ideaFeatureTitle">
                <spring:message code="label.features" />:
            </h5>
            <ul class="mdl-list">
            <c:forEach items="${ideaWrapper.features}" var="feature">
                <li class="mdl-list__item mdl-list__item--three-line">
                    <span class="mdl-list__item-primary-content">
                        <span>${feature.descriptionShort}</span>
                        <span class="mdl-list__item-text-body">${feature.descriptionLong}</span>
                    </span>
                </li>
            </c:forEach>
            </ul>
        </div>
        <div class="mdl-card__actions ideaCardActionButtons">
            <input type="submit" class="mdl-button mdl-js-button 
                   mdl-button--colored mdl-button--raised developIdeaButton" 
                   value="<spring:message code='button_label.develop_idea' />" />
        </div>
    </div>
</form>