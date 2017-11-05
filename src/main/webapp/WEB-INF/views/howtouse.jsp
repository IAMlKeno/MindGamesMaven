<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="idea" tagdir="/WEB-INF/tags" %>
<c:set var="title"><spring:message code='title.how_to_page' /></c:set>
<%@include file="../jspf/fragments/head.jspf" %>
<link rel="stylesheet" href="<c:url value="/resources/ideaHub.css" />">
<link rel="stylesheet" href="<c:url value="/resources/fragments/css/errorStyle.css" />">
<body>
    <div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-header">
        <%@include file="../jspf/fragments/menu.jspf" %>
        <main class="mdl-layout__content mdl-color--grey-100" style="min-height:600px">
            <div class="mdl-grid">
                <div class="mdl-cell mdl-cell--12-col mdl-cell--6-col-phone errorHeaderDiv">
                    <h1><spring:message code="header.how_to" /></h1>
                </div>
                <hr />
                <div class="mdl-cell mdl-cell--6-col mdl-cell--4-col-phone errorMessageDiv">
                    <p class="errorMessage" style="padding:5px">
                        Click on one of the labels below to get more details!
                        <br />
                        <a href="<c:url value='/auth.html' />">
                        <c:choose><c:when test="${userId ne null}">Or click here to continue</c:when><c:otherwise>Or click here to login</c:otherwise></c:choose>
                        </a>
                    </p>
                    <div class="howToFeature">
                        <div class="parent">Idea Hub</div>
                        <div class="child">
                            The Idea Hub is exactly that, a hub. The hub 
                            contains several boxes (let's call them <i>idea cards</i>) 
                            which have the ideas  you created and a list of the 
                            associated features.
                            <br/>
                            At the bottom of these idea cards, you will find a 
                            <i>Develop</i> button. When you click on this button, 
                            you will be taken to the develop screen which has an
                            idea web to represent your ideas.
                        </div>
                        <div class="parent">Develop Idea</div>
                        <div class="child">
                            In this develop screen you can add, delete and edit 
                            features or ideas.<br />
                            Anything that you would like to edit, first click it
                            to highlight and focus on it. Then, click again to 
                            reveal options in a modal window.
                        </div>
                        <div class="parent">Adding Features</div>
                        <div class="child">
                            While in the idea web, there is a button to the 
                            bottom of the screen called <i>Add Feature</i>. Click
                            this button to reveal a modal window for the feature's
                            title and description.
                        </div>
                        <div class="parent">Adding Ideas</div>
                        <div class="child">
                            While in the Idea Hub, you can click the <i>Add New Idea</i>
                            button. A modal window will appear with fields for
                            the idea's title and a rating - this rating can be
                            updated in the idea web screen, as well.
                        </div>
                        <div class="parent">Deleting Features</div>
                        <div class="child">
                            From the Idea Hub, click the develop button at the 
                            bottom of the card that you would like to delete
                            a feature from. In the idea web click the feature in
                            question to focus on it, then click it again. A modal
                            window will appear where you can edit the feature.
                            There is a delete button here. Click this to delete
                            the feature.
                        </div>
                        <div class="parent">Deleting Ideas</div>
                        <div class="child">
                            From the Idea Hub, click the develop button at the
                            bottom of the card belonging to the idea you want to
                            delete. At the bottom of the screen, you will see a
                            <i>Delete</i> button. Click this to delete the idea.
                        </div>
                        <div class="parent">Mark Idea as In Progress</div>
                        <div class="child">
                            From the Idea Hub, you will notice a check box and
                            the words <i>In Progress</i> in the idea cards. Check
                            the check box to mark it as in progress. <br />
                            Note that the idea cannot be both in progress and
                            complete.
                        </div>
                        <div class="parent">Mark Idea as Complete</div>
                        <div class="child">
                            From the Idea Hub, you will notice a check box and
                            the words <i>Complete</i> in the idea cards. Check
                            the check box to mark it as completed. <br />
                            Note that the idea cannot be both in progress and
                            complete.
                        </div>
                        
                        <div class="parent">Sorting</div>
                        <div class="child">
                            From the Idea Hub, you will notice a check box and
                            the words <i>Complete</i> in the idea cards. Check
                            the check box to mark it as completed. <br />
                            Note that the idea cannot be both in progress and
                            complete.
                        </div>
                        <div class="parent">Email an Idea</div>
                        <div class="child">
                            In each idea card, you can find a drop box with 
                            <i>Actions...</i> selected. You can click this for 
                            more options. Among the options is <i>Email Idea</i>.
                            <br />
                            If you select this option and click the Go button, a
                            <i>Send To </i> modal window will appear with fields
                            for Email To, Subject, Email body. You can fill these
                            options in and click send. The idea will be sent as
                            a text file via email.
                            <b>Note:</b> if all of these options are left blank,
                            the idea will be sent to the email that you 
                            registered with.
                        </div>
                        <div class="parent">Exporting Ideas</div>
                        <div class="child">
                            In each idea card, you can find a drop box with
                            <i>Actions...</i> selected. You can click this for 
                            more options. Among the options is <i>Export as Text</i>.
                            <br />
                            If you select this option and click the Go button, 
                            the browser will download a text file with your idea
                            and features.
                        </div>
                    </div>
                </div> 
            </div>
        </main>
    </div>
    <script>
        $(function () {
            $('.parent:odd').css("background-color", "darkgray");
            $('.parent').each(function () {
                $(this).next('div').hide();
            });

            $('.parent').click(function () {
                if (!$(this).next('div').is(':visible')) {
                    $('.parent').each(function () {
                        $(this).next('div').slideUp();
                    });
                    $(this).next('div').slideDown();
                }
            });
        });
    </script>
    <style>
        .parent {
            font-weight: bold;
            cursor: pointer;
            font-size: larger;
            padding-top:5px;
        }
        .child{
            padding: 0 40px;
            background: lightgray;
            margin-bottom: 5px;
            border-bottom: 1px solid;
            border-bottom-right-radius: 10px;
            border-bottom-left-radius: 10px;
        }
        .howToFeature {
            padding: 0 10px 10px 10px;
        }
    </style>
    <%@include file="../jspf/fragments/footer.jspf" %>