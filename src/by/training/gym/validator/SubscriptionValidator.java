package by.training.gym.validator;


import java.sql.Date;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.training.gym.model.SubscriptionType.*;

/**
 * class that validates sub's data.
 * @author AlesyaHlushakova
 */
public class SubscriptionValidator {

    private static final String NOT_NEEDED_COACH_VALUE = "0";
    private static final String NEED_IN_COACH_VALUE = "1";

    private static final String FEEDBACK_PATTERN = ".*<.*>+.*";

    /**
     * method checks input SUB's data.
     * @param dateValue                  the purchase date value.
     * @param durationValue              the order's duration value.
     * @param isPersonalTrainerNeedValue the isPersonalTrainerNeed string value.
     * @return true if data is valid otherwise false.
     */
    public boolean checkSubcriptionData(String dateValue, String durationValue, String isPersonalTrainerNeedValue) {

        return checkOrderPurchaseDate(dateValue) && checkOrderDurationValue(durationValue) && checkIsPersonalTrainerNeedValue(isPersonalTrainerNeedValue);

    }

    /**
     * method checks feedback.
     * @param feedback the feedback
     * @return true if feedback is valid and false otherwise.
     */
    public boolean checkFeedback(String feedback) {
        if (feedback == null) {
            return false;
        }
        if (feedback.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile(FEEDBACK_PATTERN);
        Matcher matcher = pattern.matcher(feedback);

        return !matcher.matches();
    }

    private boolean checkOrderPurchaseDate(String dateValue) {
        if (dateValue == null || dateValue.isEmpty()) {
            return false;
        }

        Date currentDate = Date.valueOf(LocalDate.now());
        Date purchaseDate = Date.valueOf(dateValue);

        return purchaseDate.getTime() >= currentDate.getTime();
    }

    private boolean checkOrderDurationValue(String durationValue) {

        if (String.valueOf(WEEK).equals(durationValue)) {
            return true;
        }

        if (String.valueOf(MONTH).equals(durationValue)) {
            return true;
        }

        if (String.valueOf(YEAR).equals(durationValue)) {
            return true;
        }



        return false;
    }

    private boolean checkIsPersonalTrainerNeedValue(String isPersonalTrainerNeedValue) {

        return NOT_NEEDED_COACH_VALUE.equals(isPersonalTrainerNeedValue) || NEED_IN_COACH_VALUE.equals(isPersonalTrainerNeedValue);

    }
}