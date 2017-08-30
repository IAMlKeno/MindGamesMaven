// Get the modal
var modal = document.getElementById('ideaFormModal');

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal 
$(document).ready(function(){
    $("#ideaSubmitButton").click(function() {
        $("body").find("#ideaFormModal").css("display", "block");
//        $("body").find("#ideaForm").css("display", "none");
    });
    
    // When the user clicks on <span> (x), close the modal
    $("span").click(function() {
        $(".modal").css("display", "none");
    });
    
    $("#modalCancelButton").click(function() {
        $(".modal").css("display", "none");
    });
    
    // When the user clicks anywhere outside of the modal, close it
    $(window).click(function(event) {
        if (event.target === $(".modal")) {
            $(".modal").css("display", "none");
        }
    });
    
    $("#newFeatureButton").click(function() {
        $("body").find("#featureFormModal").css("display", "block");
    });
    
    // When the user clicks on <span> (x), close the modal
    $("span").click(function() {
        $(".modal").css("display", "none");
    });
    
//    // When the user clicks anywhere outside of the modal, close it
//    $(window).click(function(event) {
//        if (event.target === $("#ideaFormModal")) {
//            $("#ideaFormModal").css("display", "none");
//        }
//    });
});