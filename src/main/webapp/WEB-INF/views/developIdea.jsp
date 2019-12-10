<%@ page pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<c:set var="title" value="Develop Idea" />
<%@include file="../jspf/fragments/develop_head.jspf" %>
<body>
    <ul style="margin:auto">
        <li>
            <a href="" id="idea${ideaWrapper.idea.ideaId}">${ideaWrapper.idea.ideaTitle}</a>
            <ul class="features">
                <c:forEach items="${ideaWrapper.features}" var="feature">
                    <li>
                        <a href="" title="${feature.descriptionShort}" id="${feature.featureId}">${feature.descriptionShort}</a>
                    </li>
                </c:forEach>
            </ul>
        </li>
    </ul>
    <div class="developIdeaDiv">
        <input type="button" id="newFeatureButton" class="mdl-button mdl-js-button 
               mdl-button--colored mdl-button--raised developIdeaButton" 
               value="<spring:message code='button_label.new_feature' />"/>

        <button id="saveIdea" class="mdl-button mdl-js-button 
                mdl-button--colored mdl-button--raised developIdeaButton">
            <spring:message code="button_label.done" />
        </button>

        <button id="deleteIdea" class="mdl-button mdl-js-button 
                mdl-button--colored mdl-button--raised developIdeaButton">
            <spring:message code="button_label.delete" />
        </button>

        <button id="cancelEdit" class="mdl-button mdl-js-button 
                mdl-button--colored mdl-button--raised developIdeaButton">
            <spring:message code="button_label.cancel" />
        </button>
    </div>
    <%@include file="../jspf/featureModal.jspf" %>
    <%@include file="../jspf/editFeatureModal.jspf" %>
    <%@include file="../jspf/editIdeaModal.jspf" %>

    <script>
        var featJson = '${fn:replace(featMap, "\\", "\\\\")}';
        function editIdeaTitle(idea) {
            var ideaModal = $("body").find("#editIdeaFormModal");
            ideaModal.show();
        }

        function editFeature(feat) {
            var featId = $(feat).attr("id");
            getDescriptionFromJSON(feat, featId);
        }

        function getDescriptionFromJSON(feat, id) {
            if (featJson !== "") {
                var text = $(feat).text().trim();
                var json = JSON.parse(featJson);
                var featModal = $("body").find("#editFeatureFormModal");
                featModal.find("#descriptionShort").val(text);
                featModal.find("#descriptionLong").val(json[id]);
                featModal.find("#updateFeatureId").val(id);

                // Let's add is-dirty to update MDL's floating label.
                if (!featModal.find("#descriptionShort").parent().hasClass('is-dirty')) {
                    featModal.find("#descriptionShort").parent().toggleClass('is-dirty');
                }
                if (!featModal.find("#descriptionLong").parent().hasClass('is-dirty')) {
                    featModal.find("#descriptionLong").parent().toggleClass('is-dirty');
                }

                featModal.show();
            }
        }

        $("#saveIdea").click(function () {
            var url = "<c:url value="/save" />";
            window.location.assign(url);
        });

        $("#deleteIdea").click(function () {
            var proceed = confirm("<spring:message code='phrases.confirm_delete_idea' />");
            var deleteUrl = '<c:url value="/develop/idea/remove" />';
            if (proceed) {
                window.location.assign(deleteUrl);
            }
        });

        $("#cancelEdit").click(function () {
            window.location.assign("<c:url value='/ideaHub' />");
        });

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