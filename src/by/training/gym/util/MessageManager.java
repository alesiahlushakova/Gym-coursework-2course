package by.training.gym.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Util class for changing JSP page's language.
 *
 * @author Eugene Makarenko
 * @see ResourceBundle
 * @see Locale
 */
public class MessageManager {

    public static final Locale DEFAULT_LOCALE = new Locale("", "");

    public static final String NONE_MESSAGE_KEY = "NONE";
    public static final String COMMAND_ERROR_MESSAGE_KEY = "message.command_error";
    public static final String LOGIN_ERROR_MESSAGE_KEY = "message.login_error";
    public static final String LOGIN_NOT_AVAILABLE_MESSAGE_KEY = "message.login_not_available";
    public static final String INVALID_INPUT_DATA_MESSAGE_KEY = "message.invalid_input_data";
    public static final String REGISTRATION_FAILED_MESSAGE_KEY = "message.registration_failed";
    public static final String REGISTRATION_SUCCESSFUL_MESSAGE_KEY = "message.registration_success";
    public static final String FEEDBACK_WAS_ADDED_MESSAGE_KEY = "message.feedback_was_added";
    public static final String FEEDBACK_WAS_NOT_ADDED_MESSAGE_KEY = "message.feedback_was_not_added";
    public static final String ORDER_WAS_PAYED_MESSAGE_KEY = "message.order_was_payed";
    public static final String ORDER_WAS_NOT_PAYED_MESSAGE_KEY = "message.order_was_not_payed";
    public static final String REFUSE_TRAINING_PROGRAM_SUCCESS_MESSAGE_KEY = "message.refuse_success";
    public static final String REFUSE_TRAINING_PROGRAM_FAILED_MESSAGE_KEY = "message.refuse_error";
    public static final String INFORMATION_NOT_FOUND_MESSAGE_KEY = "message.information_not_found";
    public static final String TRAINING_PROGRAM_SAVED_SUCCESSFUL_MESSAGE_KEY = "message.training_program_saved";
    public static final String TRAINING_PROGRAM_NOT_SAVED_MESSAGE_KEY = "message.training_program_not_saved";
    public static final String TRAINING_PROGRAM_DAYS_AND_EXERCISES_NOT_VALID_MESSAGE_KEY = "message.day_and_exercises_not_valid";
    public static final String EXERCISE_CREATION_FAILED_MESSAGE_KEY = "message.exercise_creating_failed";
    public static final String DAY_CAN_NOT_BE_DELETED_MESSAGE_KEY = "message.day_delete_failed";
    public static final String DAY_ADD_FAILED_MESSAGE_KEY = "message.day_add_failed";
    public static final String CLIENT_HAS_ALREADY_ORDER_MESSAGE_KEY = "message.client_has_order";
    public static final String NO_CLIENT_FOR_TRAINING_PROGRAM_CREATION_MESSAGE_KEY = "message.no_clients";

    private static final String RESOURCE_FILE_NAME = "messages";

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE_NAME, DEFAULT_LOCALE);

    private MessageManager() {
    }

    /**
     * gets property from resource file.
     *
     * @param key the key of property.
     * @return the property.
     */
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }

    /**
     * changes language of jsp page.
     *
     * @param locale the locale.
     */
    public static void changeLocale(Locale locale) {
        resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE_NAME, locale);
    }
}