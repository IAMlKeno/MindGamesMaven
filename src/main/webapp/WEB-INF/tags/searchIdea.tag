<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable">

    <label class="mdl-button mdl-js-button mdl-button--icon" for="search">
        <i class="material-icons">search</i>
    </label>
    <div class="mdl-textfield__expandable-holder">
        <input class="mdl-textfield__input" type="text" id="search" onkeyup="myFunction(this)">
        <label class="mdl-textfield__label" for="search">Enter your query...</label>
    </div>

    <ul id="myUL" style="display:none">

    </ul>
</div>
<script>
    function myFunction(input) {
        var url = '<c:url value="/search" />';
        var srchStr = $(input).val().trim();
        var list = $('#myUL');
        if (srchStr !== "") {
            $.get(url, {srchStr: srchStr})
                    .done(function (data) {
                        listSearchResults(data, list);
                    })
                    .fail();
            list.empty();
            list.show();
        } else {
            list.empty();
            list.hide();
        }
    }

    function listSearchResults(data, list) {
        var jsonData = JSON.parse(data);
        for (var key in jsonData) {
            var li = '<li class="searchResults"><a href="#_" onclick="listItemAction(' + key + ')">' + jsonData[key] + '</a></li>';
            list.append(li);
        }
    }

    function listItemAction(id) {
        var url = '<c:url value="/develop" />';
        window.location.assign(url + "?ideaId=" + id);
//        var input=$('input[name="ideaId"]')'<input type="hidden" name="ideaId" value="'+id+'"/>';
//        var theForm = $(input).parents('form');
//        theForm.submit();
    }
</script>