package by.training.gym.controller.command;

import javax.servlet.http.HttpServletRequest;

/**
 * realization of pattern Command.
 * @author AlesyaHlushakova
 */
public interface CommandAction {

    /**
     * Parameters.
     */
    String COMMAND_PARAMETER = "command";
    String LOGIN_PARAMETER = "login";
    String PASSWORD_PARAMETER = "password";
    String FIRST_NAME_PARAMETER = "first_name";
    String LAST_NAME_PARAMETER = "last_name";
    String TELEPHONE_PARAMETER = "telephone";
    String PAGE_PARAMETER = "page";
    String CLIENT_ID_PARAMETER = "client_id";
    String FEEDBACK_PARAMETER = "feedback";
    String PURCHASE_DATE_PARAMETER = "start_date";
    String DURATION_PARAMETER = "duration";
    String IS_PERSONAL_TRAINER_NEED_PARAMETER = "is_personal_trainer_need";
    String EXERCISE_ID_PARAMETER = "exercise_id";
    String DAY_NUMBER_PARAMETER = "day_number";
    String SETS_COUNT_PARAMETER = "sets_count";
    String REPEATS_COUNT_PARAMETER = "repeats_count";
    String NAME_PARAMETER = "name";
    String LEVEL_PARAMETER = "level";
    String DESCRIPTION_PARAMETER = "description";
    String DAYS_COUNT_PARAMETER = "days_count";
    String START_DATE_PARAMETER = "start_date";
    String END_DATE_PARAMETER = "end_date";
    String DIET_PARAMETER = "diet";
    String TRAINING_PROGRAM_ID_PARAMETER = "training_program_id";
    String LOCALE_PARAMETER = "locale";

    /**
     * Attributes.
     */
    String USER_ATTRIBUTE = "user";
    String MESSAGE_ATTRIBUTE = "message";
    String LIST_ATTRIBUTE = "list";
    String NUMBER_OF_PAGE_ATTRIBUTE = "numberOfPages";
    String CURRENT_PAGE_INDEX_ATTRIBUTE = "pageIndex";
    String SUBSCRIPTION_ID_ATTRIBUTE = "SubscriptionId";
    String SUBSCRIPTION_ATTRIBUTE = "Subscription";
    String PROGRAM_ATTRIBUTE = "Program";
    String DAYS_AND_EXERCISES_ATTRIBUTE = "daysAndExercises";
    String NAME_ATTRIBUTE = "name";
    String EXERCISES_ATTRIBUTE = "exercises";
    String IS_RECORD_INSERTED = "recordInserted";
    String EXERCISE_ATTRIBUTE = "exercise";

    /**
     * implemented by commands classes.
     * @param request HttpServletRequest object.
     * @return redirect page.
     * @see HttpServletRequest
     */
    CurrentJsp execute(HttpServletRequest request);
}
