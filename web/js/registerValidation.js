var passChecked = false;
var firstNameChecked = false;
var lastNameChecked = false;
var loginChecked = false;
var telephoneChecked = false;

var logRegEx = /\W/;
var passwordRegEx = /\d\W/;
var punct = /[.,!?()\\|\[\]`@$^*-+=:;№#"'_\s></%&*]+/;
var digit = /[0-9]+/;

var minPhoneLength;
var minLoginLength = 5;
var minPasswordLength = 4;
var notFoundIndex = -1;

var password = document.getElementById("password");
var confirmPassword = document.getElementById("confirm_password");
var submit = document.getElementById("submit");
var firstName = document.getElementById("first_name");
var lastName = document.getElementById("last_name");
var login = document.getElementById("login");
var telephone = document.getElementById("telephone");

var submitChange = function () {
    if (firstNameChecked && lastNameChecked && passChecked && loginChecked ) {
        submit.disabled = false;
        submit.classList.add("active");
    } else {
        submit.disabled = true;
        submit.classList.remove("active");
    }
};

var validColor = function (element) {
    element.classList.add("valid");
    element.classList.remove("notValid");
}

var notValidColor = function (element) {
    element.classList.add("notValid");
    element.classList.remove("valid");
}

var checkPassword = function () {
    if (password.value.search(passwordRegEx) > notFoundIndex ||
        password.value.length < minPasswordLength) {
        notValidColor(password);
    } else {
        validColor(password);
    }
    if (password.value == confirmPassword.value) {
        validColor(confirmPassword);
        passChecked = true;
    } else {
        notValidColor(confirmPassword);
        passChecked = false;
    }
    submitChange();
};

var checkName = function () {
    if (firstName.value.search(punct) > notFoundIndex ||
        firstName.value.search(digit) > notFoundIndex ||
        firstName.value.length < 1) {
        notValidColor(firstName);
        firstNameChecked = false;
    } else {
        validColor(firstName);
        firstNameChecked = true;
    }
    if (lastName.value.search(punct) > notFoundIndex ||
        lastName.value.search(digit) > notFoundIndex ||
        lastName.value.length < 1) {
        notValidColor(lastName);
        lastNameChecked = false;
    } else {
        validColor(lastName);
        lastNameChecked = true;
    }
    submitChange();
};

var checkLogin = function () {
    if (login.value.search(logRegEx) > notFoundIndex ||
        login.value.length <= minLoginLength) {
        notValidColor(login);
        loginChecked = false;
    } else {
        validColor(login);
        loginChecked = true;
    }
    submitChange();
};
var checkPhone = function () {
   if( telephone.value.length < minPhoneLength) {
       notValidColor(telephone);
       telephoneChecked = false;
   } else {
       validColor(telephone);
       telephoneChecked = true;
   }
}
