// Get the modal
var modal = document.getElementById('ideaFormModal');

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal 
$(document).ready(function(){
    $("#ideaSubmitButton").click(function() {
        $("#ideaFormModal").show();
    });
    
    $("#newFeatureButton").click(function() {
        $("#featureFormModal").show();
    });
    
    // When the user clicks on <span> (x), close the modal
    $('span[class="close"]').click(function() {
        $(this).parents(".modal").css("display", "none");
    });
    
    $(".modalCancelButton").click(function() {
        $(this).parents(".modal").css("display", "none");
    });

    $('.mdl-textfield input').each(function () {
        var inputElem = $(this);
        var userInput = inputElem.val();
        if (userInput) {
            var isParentDirty = inputElem.parent().hasClass('is-dirty');
            if (!isParentDirty) {
                inputElem.parent().toggleClass('is-dirty');
            }
        }
    });
});