<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="idea" tagdir="/WEB-INF/tags" %>
<c:set var="title">My Account</c:set>
<%@include file="../jspf/fragments/head.jspf" %>
<link rel="stylesheet" href="<c:url value="/resources/ideaHub.css" />">
<link rel="stylesheet" href="<c:url value="/resources/fragments/css/errorStyle.css" />">
<c:url value="/account" var="formUrl" />
<body>
    <div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-header">
        <%@include file="../jspf/fragments/menu.jspf" %>
        <main class="mdl-layout__content mdl-color--grey-100" style="min-height:600px">
            <div class="mdl-grid">
                <div class="mdl-cell mdl-cell--12-col mdl-cell--6-col-phone errorHeaderDiv">
                    <h1>My Account</h1>
                </div>
                <hr />
                <div class="mdl-cell mdl-cell--6-col mdl-cell--4-col-phone errorMessageDiv">
                    <p class="errorMessage" style="padding:5px">
                    <c:if test="${isAccountUpdated}"><div class="accountUpdated">Account updated!</div></c:if>
                    <form class="_accountUpdateForm" action="${formUrl}" method="POST">
                        <input type="hidden" name="userId" value="${userAccount.userId}" />
                        <div>
                            <label for="firstName">First Name:</label>
                            <input type="text" id="firstName" name="firstName" class="notEmpty" value="${userAccount.firstName}" />

                            <label for="lastName">Last Name:</label>
                            <input type="text" id="lastName" name="lastName" class="notEmpty" value="${userAccount.lastName}"/>
                        </div>
                        <br />
                        <div>
                            <label for="username">Username:</label>
                            <input type="text" id="username" name="username" class="notEmpty" value="${userAccount.username}" />

                            <label for="emailAddress">Email Address:</label>
                            <input type="text" id="emailAddress" name="emailAddress" class="notEmpty _emailInput" value="${userAccount.emailAddress}"/>
                        </div>
                        <br />
                        <div>
                            <label for="password">Password:</label>
                            <input type="password" id="password" name="password" class="notEmpty" value="${userAccount.password}" />

                            <label for="confirmPassword">Confirm Password</label>
                            <input type="password" id="confirmPassword" name="confirmPassword" class="notEmpty" value="${userAccount.password}"/>
                        </div>

                        <hr />
                        <input type="button" value="Update" onclick="validate(this)"
                               class="mdl-button mdl-js-button mdl-button--accent mdl-button--raised"/>
                    </form>
                    </p>
                </div>
            </div>  
        </main>
    </div>
    <%@include file="../jspf/fragments/footer.jspf" %>