var nameInput = document.getElementById("name_id");
var description = document.getElementById("description_id");
var submit = document.getElementById("create_exercise_id");

var isNameValid = false;
var isDescriptionValid = false;

var jsRegEx = /[<>]+/;
var notFoundIndex = -1;
var validNameLength = 4;

var validColor = function (element) {
    element.classList.add("valid");
    element.classList.remove("notValid");
};

var notValidColor = function (element) {
    element.classList.add("notValid");
    element.classList.remove("valid");
};

var submitChange = function () {
    if (isNameValid && isDescriptionValid) {
        submit.disabled = false;
        submit.classList.add("active_button");
    } else {
        submit.disabled = true;
        submit.classList.remove("active_button");
    }
};

var checkName = function () {
    if (nameInput.value.search(jsRegEx) > notFoundIndex || nameInput.value.length < validNameLength) {
        notValidColor(nameInput);
        isNameValid = false;
    } else {
        validColor(nameInput);
        isNameValid = true;
    }
    submitChange();
};

var checkDescription = function () {
    if (description.value.length < 12 || description.value.search(jsRegEx) > notFoundIndex) {
        description.classList.remove("valid_text_area");
        description.classList.add("error_text_area");
        isDescriptionValid = false;
    } else {
        description.classList.remove("error_text_area");
        description.classList.add("valid_text_area");
        isDescriptionValid = true;
    }
    submitChange();
}
