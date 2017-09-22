<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@attribute name="ideaWrapper" 
        type="com.portfolio.elkeno_jones.mindgamesmaven.model.IdeaWithFeatures" 
        required="true" %>

<link rel="stylesheet" href="<c:url value="/resources/fragments/css/ideaCardStyle.css" />">

<form action="<c:url value='/develop' />" method="POST">
    <input type="hidden" name="ideaId" value="${ideaWrapper.idea.ideaId}" />
    <div class="mdl-card mdl-shadow--2dp ideaCard">
        <div class="mdl-card__title ideaTitleDiv">
            <h4 class="mdl-card__title-text ideaTitle">
                ${ideaWrapper.idea.ideaTitle}
            </h4>
            <span class="rating">&#40;${ideaWrapper.idea.rating}&#41;</span>
        </div>
            <div class="statusCheckbox">
                Is in progress: <input type="checkbox" <c:if test="${ideaWrapper.idea.isInProgress}">checked</c:if> />
            </div>
            <div class="statusCheckbox">
                Is Complete: <input type="checkbox" <c:if test="${ideaWrapper.idea.isCompleted}">checked</c:if> />
            </div>
        <div class="mdl-card__supporting-text">
            <h5 class="ideaFeatureTitle">
                Features:
            </h5>
            <c:forEach items="${ideaWrapper.features}" var="feature">
            <ul class="mdl-list">
                <li class="mdl-list__item mdl-list__item--two-line">
                    <span class="mdl-list__item-primary-content">
                        <i class="material-icons mdl-list__item-icon">
                            stop
                        </i>
                        <span>
                            ${feature.descriptionShort}
                        </span>
                    </span>
                </li>
            </ul>
            </c:forEach>
        </div>
        <div class="mdl-card__actions ideaCardActionButtons">
            <input type="submit" class="mdl-button mdl-js-button 
                mdl-button--colored mdl-button--raised developIdeaButton" 
                value="Develop Idea" />
        </div>
    </div>
</form>