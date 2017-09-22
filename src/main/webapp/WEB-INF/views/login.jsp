<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="idea" tagdir="/WEB-INF/tags" %>
<c:set var="title">
    <spring:message code='header.login' />
</c:set>
<%@include file="../jspf/fragments/head.jspf" %>
<script src="<c:url value="/resources/modal/modal.js" />" type="text/javascript"></script>

<link rel="stylesheet" href="<c:url value="/resources/fragments/css/loginStyle.css" />">

<body>
    <div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-header">
        <%@include file="../jspf/fragments/menu.jspf" %>
        <main class="mdl-layout__content mdl-color--grey-100" style="min-height:600px">
            <div class="mdl-grid">
                <div class="mdl-cell mdl-cell--6-col loginHeader">
                    <h1><spring:message code="header.login_header" /></h1>
                </div>
            </div>
            <div class="mdl-grid">
                <div class="mdl-card mdl-shadow--2dp loginDiv">
                    <form:form action="/MindGamesMaven/login" modelAttribute="user" method="POST">
                        <div class="mdl-card__title ideaTitleDiv">
                            <h4 class="mdl-card__title-text ideaTitle">
                                <spring:message code="phrases.login_message" />
                            </h4>
                        </div>
                        <div class="mdl-card__supporting-text">
                            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                <form:input path="username" class="mdl-textfield__input" />
                                <form:label path="username" class="mdl-textfield__label">
                                    <spring:message code="label.username" />
                                </form:label>
                            </div>

                            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                <form:input path="password" class="mdl-textfield__input" type="password" />
                                <form:label path="password" class="mdl-textfield__label">
                                    <spring:message code="label.password" />
                                </form:label>
                            </div>
                        </div>
                        <div class="mdl-card__actions loginActionButtons">
                            <input type="submit" class="mdl-button mdl-js-button 
                                   mdl-button--colored mdl-button--raised developIdeaButton" 
                                   value="<spring:message code='label.sign_in' />" />
                            <input type="button" id="signupButton" class="mdl-button mdl-js-button 
                                   mdl-button--colored mdl-button--raised" 
                                   value="<spring:message code='label.sign_up' />" />
                        </div>
                    </form:form>
                </div>
            </div>
        </main>
        <script>
            $(document).ready(function () {
                $("#signupButton").click(function () {
                    $("body").find("#registerFormModal").css("display", "block");
//        $("body").find("#ideaForm").css("display", "none");
                });

                // When the user clicks on <span> (x), close the modal
                $("span").click(function () {
                    $(".modal").css("display", "none");
                });

                $("#modalCancelButton").click(function () {
                    $(".modal").css("display", "none");
                });

                // When the user clicks anywhere outside of the modal, close it
                $(window).click(function (event) {
                    if (event.target === $(".modal")) {
                        $(".modal").css("display", "none");
                    }
                });

                $("#newFeatureButton").click(function () {
                    $("body").find("#featureFormModal").css("display", "block");
                });

                // When the user clicks on <span> (x), close the modal
                $("span").click(function () {
                    $(".modal").css("display", "none");
                });

                // When the user clicks anywhere outside of the modal, close it
                $(window).click(function(event) {
                    if (event.target === $("#registerFormModal")) {
                        $("#registerFormModal").css("display", "none");
                    }
                });  
            });

        </script>
        <%@include file="../jspf/registerModal.jspf" %>
        <%@include file="../jspf/fragments/footer.jspf" %>