function validate(formButton) {
    var isValid = true;
    var errMessage = "";
    var regex = /[<>=\/\\]/;
    var emailRegex = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/;
    var theForm = $(formButton).parents("form");
    $(theForm).find(".notEmpty").each(function () {
        var inputValue = $(this).val().trim();
        var inputLabel = $(this).next("label").text().trim();
        if (inputValue === "") {
            isValid = false;
            errMessage += inputLabel + " is mandatory \n";
        } else if (regex.exec(inputValue)) {
            isValid = false;
            errMessage += inputLabel + " contains invalid characters \n";
        }
    });
    
    $(theForm).find("._emailInput:enabled").each(function() {
        var inputValue = $(this).val().trim();
        var inputLabel = $(this).next("label").text().trim();
        if (!emailRegex.exec(inputValue)) {
            isValid = false;
            errMessage += inputLabel + " contains invalid characters \n";
        }
    });
    
    $(theForm).find("input").each(function () {
        var inputValue = $(this).val().trim();
        var inputLabel = $(this).next("label").text().trim();
        if (regex.exec(inputValue)) {
            isValid = false;
            errMessage += inputLabel + " contains invalid characters \n";
        }
    });
    $(theForm).find("textarea").each(function () {
        var inputValue = $(this).val().trim();
        var inputLabel = $(this).next("label").text().trim();
        if (regex.exec(inputValue)) {
            isValid = false;
            errMessage += inputLabel + " contains invalid characters \n";
        }
    });
    
    if($(theForm).find('#confirmPassword').length > 0) {
        var password1 = $(theForm).find('#password').val();
        var password2 = $(theForm).find('#confirmPassword').val();
        if (password1 !== password2) {
            errMessage += "Passwords must match \n";
            isValid = false;
        }
    }

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

/*
 * Cookie functions
 * 
 */
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) === ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) === 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function checkCookie(cname) {
    var cookie = getCookie(cname);
    if (cookie !== "") {
        return cookie;
    } else {
        return "";
    }
}