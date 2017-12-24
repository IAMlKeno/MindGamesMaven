<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="idea" tagdir="/WEB-INF/tags" %>
<c:set var="title">Landing</c:set>
<%@include file="../jspf/fragments/head.jspf" %>
<link rel="stylesheet" href="<c:url value="/resources/ideaHub.css" />">
<link rel="stylesheet" href="<c:url value="/resources/fragments/css/errorStyle.css" />">
<body>
    <div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-header">
        <%@include file="../jspf/fragments/menu.jspf" %>
        <main class="mdl-layout__content mdl-color--grey-100" style="min-height:600px">
            <div style="text-align: center">
                <h4 class="landingText">Idea Organizer</h4>
                <p class="landingText">
                    Idea Organizer is a great web application intended to help you 
                    plan or decide what your next project-venture should be. 
                </p>
                <p class="landingText">
                    Use the Idea Organizer web app to develop you next next great 
                    idea!
                </p>
            </div>
            <div class="mdl-grid" style="margin-bottom: -100px">
                <div class="mdl-cell mdl-cell--3-col mdl-cell--4-col-phone">
                    <h3 class="landingText">View port for your ideas</h3>
                    <p>
                        Quickly view your stored ideas and the features that you 
                        would associate with them in the idea hub. 
                    </p>
                </div>
                <div class="mdl-cell mdl-cell--4-col mdl-cell--4-col-phone">
                    <img style="height: 80%" src="<c:url value='/resources/images/ideahub_screen.jpg' />" alt="idea hub screen" />
                </div>
            </div>  

            <div class="mdl-grid" style="margin-bottom: -100px">
                <div class="mdl-cell mdl-cell--3-col mdl-cell--4-col-phone">
                    <h3 class="landingText">Develop your new great idea</h3>
                    <p>
                        Develop your ideas by adding, deleting or editing 
                        existing features in the idea web.
                    </p>
                </div>
                <div class="mdl-cell mdl-cell--4-col mdl-cell--4-col-phone">
                    <img style="height: 80%" src="<c:url value='/resources/images/developidea_screen.jpg' />" alt="develop idea screen" />
                </div>
            </div>
            <div style="text-align: center">
                <h3 class="landingText">Ready to get started?</h3>
                <button class="_signUpButton mdl-button mdl-js-button 
                        mdl-button--colored mdl-button--raised"><spring:message code="label.sign_up" /></button>
                <br />Already have an account?<br />
                <button class="_signInButton mdl-button mdl-js-button 
                        mdl-button--colored mdl-button--raised"><spring:message code="label.sign_in" /></button>
            </div>
        </main>
    </div>
    <script>
        $(document).ready(function () {
            $("._signUpButton").click(function () {
                $("body").find("#registerFormModal").css("display", "block");
            });

            // When the user clicks on <span> (x), close the modal
            $("span").click(function () {
                $(".modal").css("display", "none");
            });

            $(".modalCancelButton").click(function () {
                $(".modal").css("display", "none");
            });

            // When the user clicks anywhere outside of the modal, close it
            $(window).click(function (event) {
                if (event.target === $(".modal")) {
                    $(".modal").css("display", "none");
                }
            });

            // When the user clicks on <span> (x), close the modal
            $("span").click(function () {
                $(".modal").css("display", "none");
            });

            // When the user clicks anywhere outside of the modal, close it
            $(window).click(function (event) {
                if (event.target === $("#registerFormModal")) {
                    $("#registerFormModal").css("display", "none");
                }
            });
        });
        $('._signInButton').click(function () {
            window.location = "<c:url value='/auth' />";
        });
    </script>
    <%@include file="../jspf/registerModal.jspf" %>            
    <%@include file="../jspf/fragments/footer.jspf" %>