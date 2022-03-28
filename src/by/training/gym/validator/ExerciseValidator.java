package by.training.gym.validator;

import by.training.gym.model.Exercise;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.training.gym.model.ExerciseLevel.*;

/**
 * class that validates exercise's data.
 * @author Alesya Hlushakova
 */
public class ExerciseValidator {

    private static final String EXERCISE_NAME_PATTERN = "[A-Za-zА-Яа-я0-9., \\-]*";
    private static final String EXERCISE_DESCRIPTION_PATTERN = ".*<.*>+.*";

    private static final int MAXIMUM_EXERCISE_COUNT = 15;

    private static final int MIMIMUM_WEIGHT_LOSS = 1;
    private static final int MAXIMUM_WEIGHT_LOSS = 100;

    private static final int MINIMUM_SETS_COUNT = 1;
    private static final int MINIMUM_REPEATS_COUNT = 1;

    /**
     * method validates exercise's data.
     * @param name        the exercise's name.
     * @param levelValue  the level value.
     * @param description the exercise's description.
     * @return true if data is valid and false otherwise.
     */
    public boolean checkData(String name, String restrcitions, int caloriesLost, String levelValue, String description) {

        return checkName(name)
                && checkLevel(levelValue)
                && checkRestrictions(restrcitions)
                && checkCalories(caloriesLost)
                && checkDescription(description);

    }

    /**
     * method checks exercise's count during add operation.
     * @param exercises    the exercises per day.
     * @param setsCount    the exercise's sets count.
     * @param repeatsCount the exercise's repeats count.
     * @return true if count is valid and false otherwise.
     */
    public boolean checkExerciseCountDuringAddOperation(List<Exercise> exercises, int weightLoss, int setsCount, int repeatsCount) {
        return exercises.size() < MAXIMUM_EXERCISE_COUNT
                && weightLoss >= MIMIMUM_WEIGHT_LOSS
                && weightLoss <= MAXIMUM_WEIGHT_LOSS
                && setsCount >= MINIMUM_SETS_COUNT
                && repeatsCount >= MINIMUM_REPEATS_COUNT;
    }

    /**
     * method checks input exercise for uniqueness.
     * @param exerciseId the input exercise's id.
     * @param exercises  the current exercises in training program.
     * @return true if exercise is unique and false otherwise.
     */
    public boolean checkExerciseForUniqueInTrainingProgram(int exerciseId, List<Exercise> exercises) {
        for (Exercise currentExercise : exercises) {
            int currentExerciseId = currentExercise.getId();
            if (exerciseId == currentExerciseId) {
                return false;
            }
        }
        return true;
    }

    /**
     * method checks exercise's count during edit operation.
     * @param setsCount    the exercise's sets count.
     * @param repeatsCount the exercise's repeats count.
     * @return true if count is valid and false otherwise.
     */
    public boolean checkExerciseDuringEditOperation(int weightLoss, int setsCount, int repeatsCount) {
        return  weightLoss >=MIMIMUM_WEIGHT_LOSS && setsCount >= MINIMUM_SETS_COUNT
                && repeatsCount >= MINIMUM_REPEATS_COUNT;
    }

    private boolean checkLevel(String levelValue) {

        if (String.valueOf(STARTER).equals(levelValue)) {
            return true;
        }
        if (String.valueOf(CASUAL).equals(levelValue)) {
            return true;
        }
        if (String.valueOf(EXPERT).equals(levelValue)) {
            return true;
        }

        return false;
    }

    private boolean checkName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile(EXERCISE_NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);

        return matcher.matches();
    }

    private boolean checkRestrictions(String restrictions) {
        if (restrictions == null || restrictions.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean checkCalories(int calories) {
        if (calories<MINIMUM_SETS_COUNT) {
            return false;
        }
        return true;
    }

    private boolean checkDescription(String description) {
        if (description == null) {
            return false;
        }
        if (description.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile(EXERCISE_DESCRIPTION_PATTERN);
        Matcher matcher = pattern.matcher(description);

        return !matcher.matches();
    }

}