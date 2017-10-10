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
