package by.training.gym.service;

import by.training.gym.dao.ConnectionController;
import by.training.gym.dao.DAOException;
import by.training.gym.dao.ExerciseDAO;
import by.training.gym.dao.ProgramDAO;
import by.training.gym.model.Exercise;
import by.training.gym.model.ExerciseLevel;
import by.training.gym.validator.ExerciseValidator;

import java.util.*;

/**
 * service class for Exercise entity.
 * @author AlesyaHlushakova
 */
public class ExerciseService {

    private static final int INCREMENT_INDEX = 1;

    /**
     * method adds exercise to training program.
     * @param trainingProgramId the training program id.
     * @param daysAndExercises  the Map with day numbers and exercises.
     * @return true if operation was made successfully and false otherwise.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean addExercisesToTrainingProgram(int trainingProgramId,
                                                 Map<Integer, List<Exercise>> daysAndExercises,
                                                 boolean isCleanNeed) throws ServiceException {

        ConnectionController connectionController = new ConnectionController();
        try {
            connectionController.startTransaction();

            if (isCleanNeed) {
                ProgramDAO trainingProgramDAO = new ProgramDAO(connectionController.getConnection());
                boolean isCleanOperationSuccessful = trainingProgramDAO.deleteExercisesFromProgram(trainingProgramId);

                if (!isCleanOperationSuccessful) {
                    return false;
                }
            }

            ExerciseDAO exerciseDAO = new ExerciseDAO(connectionController.getConnection());

            Set<Map.Entry<Integer, List<Exercise>>> entrySet = daysAndExercises.entrySet();
            for (Map.Entry<Integer, List<Exercise>> entry : entrySet) {
                int dayNumber = entry.getKey();
                List<Exercise> exercises = entry.getValue();
                for (Exercise exercise : exercises) {
                    int weightLoss = exercise.getWeightLoss();
                    int setsCount = exercise.getSetsCount();
                    int repeatsCount = exercise.getRepeatsCount();
                    int exerciseId = exercise.getId();

                    boolean isResultSuccessful = exerciseDAO.insertExerciseIntoProgram(trainingProgramId, exerciseId, dayNumber,
                            weightLoss, setsCount, repeatsCount);
                    if (!isResultSuccessful) {
                        connectionController.rollbackTransaction();
                        return false;
                    }
                }
            }

            connectionController.commitTransaction();
            return true;
        } catch (DAOException exception) {
            connectionController.rollbackTransaction();
            throw new ServiceException("Exception during adding "
                    + "exercises to program.", exception);
        } finally {
            connectionController.endTransaction();
            connectionController.close();
        }
    }

    /**
     * method finds all exercises id and name.
     * @return List with exercises.
     * @throws ServiceException object if execution of method is failed.
     */
    public List<Exercise> findAllExercisesIdAndName() throws ServiceException {
        try (ConnectionController connectionController = new ConnectionController()) {
            ExerciseDAO exerciseDAO = new ExerciseDAO(connectionController.getConnection());
            List<Exercise> exercises = exerciseDAO.selectAll();

            return exercises;
        } catch (DAOException exception) {
            throw new ServiceException("Exception during attempt"
                    + " to find all exercises id and name operation.", exception);
        }
    }

    /**
     * method adds exercise in database.
     * @param exercise the exercise.
     * @return true if operation successful and false otherwise.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean saveExercise(Exercise exercise) throws ServiceException {
        try (ConnectionController connectionController = new ConnectionController()) {
            ExerciseDAO exerciseDAO = new ExerciseDAO(connectionController.getConnection());

            return exerciseDAO.insert(exercise);
        } catch (DAOException exception) {
            throw new ServiceException("Exception during save exercise operation.", exception);
        }
    }

    /**
     * method creates Exercise object.
     * @param name        the exercise's name.
     * @param levelValue  the exercise's difficulty level value.
     * @param description the exercise's description.
     * @return Exercise object.
     */
    public Exercise createExercise(String name, String restrictions, int caloriesLost, String levelValue, String description) {
        Exercise exercise = new Exercise();
        exercise.setName(name);

        exercise.setRestrictions(restrictions);
        exercise.setCaloriesLost(caloriesLost);
        ExerciseLevel level = ExerciseLevel.valueOf(levelValue);
        exercise.setLevel(level);
        exercise.setDescription(description);

        return exercise;
    }

    /**
     * method edits exercise in program.
     * @param exerciseIdValue   the exercise's id value.
     * @param dayNumberValue    the exercise's day number value.
     * @param weightLossValue   the exercise's weight loss value.
     * @param setsCountValue    the exercise's sets count value.
     * @param repeatsCountValue the exercise's repeats count value.
     * @param daysAndExercises  the days and exercises in this day.
     * @return true if operation was successful and false otherwise.
     */
    public boolean editExercise(String exerciseIdValue, String dayNumberValue,
                                String weightLossValue, String setsCountValue, String repeatsCountValue, Map<Integer, List<Exercise>> daysAndExercises) {
        int weightLoss = Integer.parseInt(weightLossValue);
        int setsCount = Integer.parseInt(setsCountValue);
        int repeatsCount = Integer.parseInt(repeatsCountValue);
        ExerciseValidator exerciseDataValidator = new ExerciseValidator();
        boolean isDataValid = exerciseDataValidator.checkExerciseDuringEditOperation(weightLoss, setsCount, repeatsCount);
        if (!isDataValid) {
            return false;
        }

        int exerciseId = Integer.parseInt(exerciseIdValue);
        int dayNumber = Integer.parseInt(dayNumberValue);
        Set<Map.Entry<Integer, List<Exercise>>> entrySet = daysAndExercises.entrySet();
        for (Map.Entry<Integer, List<Exercise>> entry : entrySet) {
            int day = entry.getKey();
            if (day == dayNumber) {
                List<Exercise> exercises = entry.getValue();
                for (Exercise exercise : exercises) {
                    int currentExerciseId = exercise.getId();

                    if (exerciseId == currentExerciseId) {
                        exercise.setWeightLoss(weightLoss);
                        exercise.setSets(setsCount);
                        exercise.setRepeats(repeatsCount);
                    }
                }
            }
        }

        return true;
    }

    /**
     * This method deletes exercise from training program.
     *
     * @param exerciseIdValue  the exercise's id value.
     * @param dayNumberValue   the exercise's day number value.
     * @param daysAndExercises the days and exercises in this day.
     */
    public void deleteExerciseFromTrainingProgram(String exerciseIdValue, String dayNumberValue, TreeMap<Integer, List<Exercise>> daysAndExercises) {
        int exerciseId = Integer.parseInt(exerciseIdValue);
        int dayNumber = Integer.parseInt(dayNumberValue);

        Set<Map.Entry<Integer, List<Exercise>>> entrySet = daysAndExercises.entrySet();
        for (Map.Entry<Integer, List<Exercise>> entry : entrySet) {
            int day = entry.getKey();
            if (day == dayNumber) {
                List<Exercise> exercises = entry.getValue();
                Iterator<Exercise> iterator = exercises.iterator();
                while (iterator.hasNext()) {
                    Exercise exercise = iterator.next();
                    int currentExerciseId = exercise.getId();

                    if (exerciseId == currentExerciseId) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    /**
     * This method adds exercise in training program.
     *
     * @param exerciseIdValue   the exercise's id value.
     * @param dayNumberValue    the exercise's day number value.
     * @param setsCountValue    the exercise's sets count value.
     * @param repeatsCountValue the exercise's repeats count value.
     * @param daysAndExercises  the days and exercises in this day.
     * @return true if operation was successful and false otherwise.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean addExerciseInTrainingProgram(String exerciseIdValue, String dayNumberValue,
                                                String weightLossValue, String setsCountValue,
                                                String repeatsCountValue,
                                                TreeMap<Integer, List<Exercise>> daysAndExercises)
            throws ServiceException {
        try (ConnectionController connectionController = new ConnectionController()) {
            int dayNumber = Integer.parseInt(dayNumberValue);
            List<Exercise> exercises = daysAndExercises.get(dayNumber);
            int exerciseId = Integer.parseInt(exerciseIdValue);
            ExerciseValidator exerciseDataValidator = new ExerciseValidator();
            boolean isExerciseUnique = exerciseDataValidator.checkExerciseForUniqueInTrainingProgram(exerciseId, exercises);
            if (!isExerciseUnique) {
                return false;
            }

            int weightLoss = Integer.parseInt(weightLossValue);
            int setsCount = Integer.parseInt(setsCountValue);
            int repeatsCount = Integer.parseInt(repeatsCountValue);
            boolean isDataValid = exerciseDataValidator.checkExerciseCountDuringAddOperation(exercises, weightLoss, setsCount, repeatsCount);
            if (!isDataValid) {
                return false;
            }

            ExerciseDAO exerciseDAO = new ExerciseDAO(connectionController.getConnection());
            Exercise exercise = exerciseDAO.selectEntityById(exerciseId);
            exercise.setWeightLoss(weightLoss);
            exercise.setSets(setsCount);
            exercise.setRepeats(repeatsCount);
            exercises.add(exercise);

            return true;
        } catch (DAOException e) {
            throw new ServiceException("Exception during add exercise in training program operation.", e);
        }
    }
}