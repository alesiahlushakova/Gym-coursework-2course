package by.training.gym.validator;

import by.training.gym.model.Exercise;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class that checks program data during creation and edition.
 * @author AlesyaHlushakova
 */
public class ProgramValidator {

    private static final int MINIMAL_EXERCISES_COUNT_PER_DAY = 3;

    private static final int MINIMAL_DAYS_COUNT_IN_TRAINING_PROGRAM = 2;

    private static final int MAXIMUM_DAYS_PER_WEEK = 7;

    private static final String NONE_ID_VALUE = "0";

    private static String DIET_PATTERN = ".*<.*>+.*";

    /**
     * method checks exercises count per day.
     * @param daysAndExercises the Map with day number and exercises.
     * @return true if data is valid and false otherwise.
     */
    public boolean checkDaysAndExercisesCount(TreeMap<Integer, List<Exercise>> daysAndExercises) {

        int daysCount = daysAndExercises.size();

        Set<Map.Entry<Integer, List<Exercise>>> entrySet = daysAndExercises.entrySet();
        for (Map.Entry<Integer, List<Exercise>> entry : entrySet) {
            List<Exercise> exercises = entry.getValue();
            int currentExercisesCount = exercises.size();
            if (currentExercisesCount < MINIMAL_EXERCISES_COUNT_PER_DAY) {
                return false;
            }

        }

        return daysCount >= MINIMAL_DAYS_COUNT_IN_TRAINING_PROGRAM;
    }

    /**
     * method checks program data.
     * @param clientIdValue  the client id value.
     * @param startDateValue the start date value.
     * @param endDateValue   the end date value.
     * @param diet           the diet.
     * @return true if data is valid and false otherwise.
     */
    public boolean checkTrainingProgramData(String clientIdValue, String startDateValue, String endDateValue, String diet, String daysCountValue) {
        return checkClientId(clientIdValue)
                && checkTrainingProgramDate(startDateValue, endDateValue)
                && checkDiet(diet)
                && checkDaysCountBeforeCreation(daysCountValue);
    }

    /**
     * This method checks max days count in training program during add operation.
     *
     * @param daysAndExercises the Map with day number and exercises.
     * @return true if data is valid and false otherwise.
     */
    public boolean checkDaysCountForAddOperation(TreeMap<Integer, List<Exercise>> daysAndExercises) {
        int currentDaysCount = daysAndExercises.size();

        return currentDaysCount < MAXIMUM_DAYS_PER_WEEK;
    }

    /**
     * This method checks min days count in training program during delete operation.
     *
     * @param daysAndExercises the Map with day number and exercises.
     * @return true if data is valid and false otherwise.
     */
    public boolean checkDaysCountForDeleteOperation(TreeMap<Integer, List<Exercise>> daysAndExercises) {
        int currentDaysCount = daysAndExercises.size();

        return currentDaysCount > MINIMAL_DAYS_COUNT_IN_TRAINING_PROGRAM;
    }

    private boolean checkClientId(String idValue) {
        return !NONE_ID_VALUE.equals(idValue);
    }

    private boolean checkDiet(String diet) {
        if (diet == null) {
            return false;
        }
        if (diet.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile(DIET_PATTERN);
        Matcher matcher = pattern.matcher(diet);

        return !matcher.matches();

    }

    private boolean checkTrainingProgramDate(String startDateValue, String endDateValue) {
        Date currentDate = Date.valueOf(LocalDate.now());
        Date startDate = Date.valueOf(startDateValue);
        Date endDate = Date.valueOf(endDateValue);

        return startDate.getTime() >= currentDate.getTime() && startDate.getTime() < endDate.getTime();
    }

    private boolean checkDaysCountBeforeCreation(String daysCountValue) {
        int daysCount = Integer.parseInt(daysCountValue);

        return daysCount >= MINIMAL_DAYS_COUNT_IN_TRAINING_PROGRAM;
    }
}
