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
            <div class="newIdeaButtonDiv">
                <button id="ideaSubmitButton" 
                        class="mdl-button mdl-js-button mdl-button--colored 
                        mdl-button--raised">
                    <spring:message code="button_label.add_new_idea" />
                </button>
            </div>
            <div class="sortDiv">
                <h4 style="margin:0">Sort By</h4>
                <c:set var="formUrl"><c:url value="/ideaHub" /></c:set>
                <form:form method="get" modelAttribute="sortForm" action="${formUrl}">
                    <form:select path="sortBy" class="_sortBy"> <%--TODO: move this to the database somehow --%>
                        <form:option value="">Sort By...</form:option>
                        <form:option value="1">Created</form:option>
                        <form:option value="7">Rating</form:option>
                        <form:option value="3">Title</form:option>
                        <form:option value="5">In Progress</form:option>
                    </form:select>
                    <form:select path="sortDir" class="_sortDir" style="margin-left: 2%;">
                        <form:option value="ASC">Ascending</form:option>
                        <form:option value="DESC">Descending</form:option>
                    </form:select>
                    <input type="submit" value="Refresh" />
                </form:form>
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
    <%@include file="../jspf/sendToEmail.jspf" %>
    <script>
        $(function () {
            var sortDirCookieVal = getCookie("sortDir");
            var sortByCookieVal = getCookie("sortBy");
            if (sortByCookieVal !== "" && sortDirCookieVal !== 'undefined') {
                var sortBySelect = $('._sortBy');
                var sortDirSelect = $('._sortDir');
                if (sortBySelect.val() !== sortByCookieVal && sortDirSelect.val() !== sortDirCookieVal) {
                    sortBySelect.val(sortByCookieVal);
                    sortDirSelect.val(sortDirCookieVal);

                    $('._sortDir').parents('form').submit();
                }
            }
        });

        $('._sortDir').parents('form').submit(function () {
            setupSortCookies();
        });
        function setupSortCookies() {
            var sortDirVar = "sortDir";
            var sortByVar = "sortBy";
            var sortDirValue = $('._sortDir').val();
            ;
            var sortByValue = $('._sortBy').val();
            var days = 7;

            setCookie(sortByVar, sortByValue, days);
            setCookie(sortDirVar, sortDirValue, days);
        }
        $(".inProgressCheckbox").click(function () {
            updateProgress(this, true);
        });

        $(".completeCheckbox").click(function () {
            updateStatus(this, true);
        });

        function updateProgress(elem, changeOther) {
            var url = "<c:url value="/ideaHub/progress" />";
            var checkbox = $(elem);
            var ideaId = checkbox.parents("form").find("[name='ideaId']").val();
            var completeCheckbox = $(checkbox.parents("div")
                    .siblings(".statusCheckbox").find(".completeCheckbox"));
            var proceed = true;
            if (changeOther === true && $(completeCheckbox).is(":checked")) {
                var confirmChange = confirm("<spring:message code='phrases.confirm_progress_change' />");
                if (confirmChange) {
                    $(completeCheckbox).prop("checked", false);
                } else {
                    checkbox.prop("checked", false);
                }
            }
            var checkboxValue = checkbox.prop("checked");
            var otherAction = $(completeCheckbox).prop("checked");

            if (proceed) {
                $.get(url,
                        {ideaId: ideaId, action: checkboxValue, otherAction: otherAction}
                )
                        .done()
                        .fail(function () {
                            alert("<spring:message code='phrases.update_progress_failed' />");
                            if (checkbox.is(":checked")) {
                                checkbox.prop("checked", false);
                            } else {
                                checkbox.prop("checked", true);
                            }
                        });
            }
            return completeCheckbox;
        }

        function updateStatus(elem, changeOther) {
            var url = "<c:url value="/ideaHub/status" />";
            var checkbox = $(elem);
            var ideaId = checkbox.parents("form").find("[name='ideaId']").val();
            var progressCheckbox = $(checkbox.parents("div")
                    .siblings(".statusCheckbox").find(".inProgressCheckbox"));
            var proceed = true;
            if (changeOther === true && $(progressCheckbox).is(":checked")) {
                if (confirm("<spring:message code='phrases.confirm_status_change' />")) {
                    $(progressCheckbox).prop("checked", false);
                } else {
                    checkbox.prop("checked", false);
                }
            }
            var checkboxValue = checkbox.prop("checked");
            var otherAction = $(progressCheckbox).prop("checked");

            if (proceed) {
                $.get(url,
                        {ideaId: ideaId, action: checkboxValue, otherAction: otherAction}
                )
                        .done()
                        .fail(function () {
                            alert("<spring:message code='phrases.update_status_failed' />");
                            if (checkbox.is(":checked")) {
                                checkbox.prop("checked", false);
                            } else {
                                checkbox.prop("checked", true);
                            }
                        });
            }
            return progressCheckbox;
        }

        $("._export").click(function () {
            var url = "<c:url value='/file' />";
            var ideaIdVar = $(this).parents('form').find('input[name="ideaId"]').val();
            var action = $(this).prev('._exportActionSelect').val();
            switch (action) {
                case "text":
                    window.location.assign(url + "?ideaId=" + ideaIdVar);
                    break;
                case "email":
                    openSendToForm(ideaIdVar);
                    break;
            }
        });

        function openSendToForm(ideaId) {
            var sendToModal = $("#emailToFormModal");
            sendToModal.find("input[name='ideaId']").val(ideaId);
            sendToModal.show();
        }
    </script>
    <%@include file="../jspf/fragments/footer.jspf" %>