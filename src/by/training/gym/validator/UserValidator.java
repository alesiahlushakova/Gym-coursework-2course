package by.training.gym.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class for user validation.
 * @author AlesyaHlushakova
 */
public class UserValidator {

    private static final String LOGIN_PATTERN = "([a-zA-Z0-9_]+){6,}";
    private static final String PASSWORD_PATTERN = "([a-zA-Z0-9_]+){4,}";
    private static final String NAME_PATTERN = "[A-Za-zА-Яа-я]+";
    private static final String PHONE_PATTERN = "^\\+375 \\((17|29|33|44)\\) [0-9]{3}-[0-9]{2}-[0-9]{2}$";
    private static final String SPACE_PATTERN = "\\s";

    /**
     * method checks user registration.
     * @param login     the user's login.
     * @param password  the user's password.
     * @param firstName the user's first name.
     * @param lastName  the user's last name.
     * @return result of validation.
     */
    public boolean checkData(String login, String password, String firstName,
                             String lastName, String telephone) {
        if (login == null || login.isEmpty()) {
            return false;
        }
        if (password == null || password.isEmpty()) {
            return false;
        }
        if (firstName == null || firstName.isEmpty()) {
            return false;
        }
        if (lastName == null || lastName.isEmpty()) {
            return false;
        }
        if (telephone == null || telephone.isEmpty()) {
            return false;
        }

        boolean isLoginValid = matchPattern(login, LOGIN_PATTERN);
        boolean isPasswordValid = matchPattern(password, PASSWORD_PATTERN);
        boolean isFirstNameValid = matchPattern(firstName, NAME_PATTERN);
        boolean isLastNameValid = matchPattern(lastName, NAME_PATTERN);
        boolean isTelephoneValid = matchPattern(telephone,PHONE_PATTERN);
        return isLoginValid && isFirstNameValid && isPasswordValid
                && isLastNameValid && isTelephoneValid;
    }

    /**
     * method checks name to include first name and last name.
     * @param name the name.
     * @return true if data is valid and false otherwise.
     */
    public boolean isNameFull(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }

        String pattern = NAME_PATTERN + SPACE_PATTERN + NAME_PATTERN;

        return matchPattern(name, pattern);
    }

    private boolean matchPattern(String data, String currentPattern) {
        Pattern pattern = Pattern.compile(currentPattern);
        Matcher matcher = pattern.matcher(data);

        return matcher.matches();
    }

}
