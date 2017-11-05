<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable">
    <label class="mdl-button mdl-js-button mdl-button--icon" for="search">
        <i class="material-icons">search</i>
    </label>
    <div class="mdl-textfield__expandable-holder">
        <input class="mdl-textfield__input" type="text" id="search" onkeyup="callSearch(this)" onkeydown="stopSearch()" onblur="clearSearch(this)" onfocus="callSearch(this)">
        <label class="mdl-textfield__label" for="search">Enter your query...</label>
    </div>
    <ul id="myUL" style="display:none"></ul>
</div>
<script>
    var ideaTitleSearchTimeout;
    function callSearch(input) {
        ideaTitleSearchTimeout = setTimeout(searchIdeaTitle(input), 1000);
    }

    function stopSearch() {
        clearTimeout(ideaTitleSearchTimeout);
    }

    function searchIdeaTitle(input) {
        var srchStr = $(input).val().trim();
        var list = $('#myUL');
        if (srchStr !== "") {
            showHideLoader(true);
            var url = '<c:url value="/search" />';
        
            $.get(url, {srchStr: srchStr})
                    .done(function (data) {
                        showHideLoader(false);
                        listSearchResults(data, list);
                    })
                    .fail();
            list.show();
        } else {
            list.empty();
            list.hide();
        }
    }

    function listSearchResults(data, list) {
        var jsonData = JSON.parse(data);
        list.empty();
        for (var key in jsonData) {
            var li = '<li class="searchResults"><a href="#_" onclick="listItemAction(' + key + ')">' + jsonData[key] + '</a></li>';
            list.append(li);
        }
    }

    function listItemAction(id) {
        var url = '<c:url value="/develop" />';
        window.location.assign(url + "?ideaId=" + id);
    }
    
    function showHideLoader(showLoader){
        var divForLoader = $('#myUL').prev('div').prev('label');
        if (showLoader) {
            if($(divForLoader).prev('img').length <= 0) {
                var imageTag = $("<img />")
                        .prop('src', '<c:url value="/resources/images/loader.gif" />')
                        .css("width", "30px");
                divForLoader.before(imageTag);
            }
        } else {
            var loaderImage = $(divForLoader).prev('img');
            $(loaderImage).remove();
        }
    }
    
    function clearSearch(input) {
//        $('#myUL').hide();
    }
</script>