<%@ page pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:set var="title" value="Develop Idea" />
<!DocType html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="description" content="A front-end template that helps you build fast, modern mobile web apps.">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
        <title>${title}</title>

        <!-- Add to homescreen for Chrome on Android -->
        <meta name="mobile-web-app-capable" content="yes">
        <!--    <link rel="icon" sizes="192x192" href="assets/images/android-desktop.png">-->

        <!-- Add to homescreen for Safari on iOS -->
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta name="apple-mobile-web-app-title" content="Material Design Lite">
        <!--    <link rel="apple-touch-icon-precomposed" href="assets/images/ios-desktop.png">-->

        <!-- Tile icon for Win8 (144x144 + tile color) -->
        <meta name="msapplication-TileImage" content="images/touch/ms-touch-icon-144x144-precomposed.png">
        <meta name="msapplication-TileColor" content="#3372DF">

        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.cyan-light_blue.min.css">
        <link rel="stylesheet" href="<c:url value="/resources/main.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/developIdea.css" />">
        <link href="<c:url value="/resources/main.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/modal/modalStyle.css" />" rel="stylesheet">

        <!-- Kenneth Kufluk 2008/09/10 -->
        <link href="<c:url value="/resources/js-mindmap-master/js-mindmap.css" />" rel="stylesheet" />
        <link href="<c:url value="/resources/js-mindmap-master/style.css" />" type="text/css" rel="stylesheet"/>

        <!-- jQuery -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js" type="text/javascript"></script>
        <!-- UI, for draggable nodes -->
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.15/jquery-ui.min.js"></script>

        <!-- Raphael for SVG support (won't work on android) -->
        <script type="text/javascript" src="<c:url value="/resources/js-mindmap-master/raphael-min.js" />"></script>

        <!-- Mindmap -->
        <script type="text/javascript" src="<c:url value="/resources/js-mindmap-master/js-mindmap.js" />"></script>

        <!-- Kick everything off -->
        <script src="<c:url value="/resources/js-mindmap-master/script.js" />" type="text/javascript"></script>

        <script src="<c:url value="/resources/modal/modal.js" />" type="text/javascript"></script>
        <style>
            #view-source {
                position: fixed;
                display: block;
                right: 0;
                bottom: 0;
                margin-right: 40px;
                margin-bottom: 40px;
                z-index: 900;
            }
        </style>
    </head>
    <body>
        <ul style="margin:auto">
            <li>
                <a href="" id="idea${ideaWrapper.idea.ideaId}">
                    ${ideaWrapper.idea.ideaTitle}
                </a>
                <ul class="features">
                    <c:forEach items="${ideaWrapper.features}" var="feature">
                        <li>
                            <a href="" title="${feature.descriptionShort}" id="${feature.featureId}">
                                ${feature.descriptionShort}
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </li>
        </ul>
        <div class="developIdeaDiv">
            <input type="button" id="newFeatureButton" class="mdl-button mdl-js-button 
                   mdl-button--colored mdl-button--raised developIdeaButton" 
                   value="New Feature"/>

            <button id="saveIdea" class="mdl-button mdl-js-button 
                    mdl-button--colored mdl-button--raised developIdeaButton">
                Done
            </button>

            <button id="deleteIdea" class="mdl-button mdl-js-button 
                    mdl-button--colored mdl-button--raised developIdeaButton">
                <spring:message code="button_label.delete" />
            </button>

            <button id="cancelEdit" class="mdl-button mdl-js-button 
                    mdl-button--colored mdl-button--raised developIdeaButton">
                Cancel
            </button>
        </div>
        <%@include file="../jspf/featureModal.jspf" %>
        <%@include file="../jspf/editFeatureModal.jspf" %>
        <%@include file="../jspf/editIdeaModal.jspf" %>

        <div class="featureEdit" style="border: 2px solid; border-radius: 5px; width: 30%; float:right; display:none">
            <div>Edit Feature</div>
            <div class="featureTitle">
                <input type="text" name="" class="featureTitleInput" value=""/>
            </div>
            <div class="featureDescript">
                <textarea class="textareaField">test</textarea>
            </div>
        </div>

        <script>
            var featJson = '${featMap}';
            function editIdeaTitle(idea) {
                var ideaTitle = $(idea).text().trim();
                var ideaModal = $("body").find("#editIdeaFormModal");
                ideaModal.find("#theIdeaTitle").val(ideaTitle);

                ideaModal.show();
            }

            function editFeature(feat) {
                var featId = $(feat).attr("id");
                var url = '<c:url value="/develop/feature" />';
                getDescriptionFromJSON(feat, featId);
//                $.get(
//                        url,
//                        {featureId: featId}
//                )
//                        .done(function (data) {
////                            populateForm(data);
//                            getDescriptionFromJSON(feat, featId);
//                        })
//                        .fail(function () {
//                            getDescriptionFromJSON(feat, featId);
//                        });
            }

            function getDescriptionFromJSON(feat, id) {
                var text = $(feat).text().trim();
                var json = JSON.parse(featJson);
                var featModal = $("body").find("#editFeatureFormModal");
                featModal.find("#descriptionShort").val(text);
                featModal.find("#descriptionLong").val(json[id]);
                featModal.find("#updateFeatureId").val(id);

                featModal.show();
            }

            $("#saveIdea").click(function () {
                var url = "<c:url value="/save" />";
                window.location.assign(url);
            });

            $("#deleteIdea").click(function () {
                var proceed = confirm("Are you sure that you want to delete this idea?");
                var deleteUrl = '<c:url value="/develop/idea/remove" />';
                if (proceed) {
                    window.location.assign(deleteUrl);
                }
            });

            $("#cancelEdit").click(function () {
                window.location.assign("/MindGamesMaven/ideaHub");
            });

            function validate(formButton) {
                var isValid = true;
                var errMessage = "";
                var regex = /[<>=\/\\'"]/;
                var theForm = $(formButton).parents("form");
                $(theForm).find(".notEmpty").each(function () {
                    var inputValue = $(this).val();
                    var inputLabel = $(this).next("label").text().trim();
                    if (inputValue === "") {
                        isValid = false;
                        errMessage += inputLabel + " is mandatory \n";
                    } else if (regex.exec(inputValue)) {
                        isValid = false;
                        errMessage += inputLabel + " contains invalid characters \n";
                    }
                });

                if (!isValid) {
                    alert(errMessage);
                } else {
                    submitForm(theForm);
                }
                return isValid;
            }

            function submitForm(form) {
                form.submit();
            }

            function populateForm(data) {
                var theData = JSON.parse(data);
                var featModal = $("body").find("#editFeatureFormModal");
                featModal.find("#descriptionShort").val(theData.descriptionShort);
                featModal.find("#descriptionLong").val(theData.descriptionLong);
                featModal.find("#updateFeatureId").val(theData.featureId);

                featModal.show();
            }
        </script>
        <script src="https://code.getmdl.io/1.3.0/material.min.js"></script>
    </body>
</html>