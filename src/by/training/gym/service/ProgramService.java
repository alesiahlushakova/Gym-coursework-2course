package by.training.gym.service;

import by.training.gym.dao.*;
import by.training.gym.model.Exercise;
import by.training.gym.model.Program;
import by.training.gym.validator.ProgramValidator;

import java.sql.Date;
import java.util.*;

/**
 * service class for Program entity.
 * @author AlesyaHlushakova
 */
public class ProgramService {

    private static final int DAY_INCREMENT_INDEX = 1;

    /**
     * method discards program.
     * @param programId the program id.
     * @return true if operation was successful and false otherwise.
     * @throws ServiceException object if execution of query is failed.
     */
    public boolean discardProgram(int programId) throws ServiceException {

        ConnectionController connectionController = new ConnectionController();
        try {
            connectionController.startTransaction();

            ProgramDAO programDAO = new ProgramDAO(connectionController.getConnection());

            boolean isExercisesCleaned = programDAO.deleteExercisesFromProgram(programId);
            if (!isExercisesCleaned) {
                connectionController.rollbackTransaction();
                return false;
            }

            boolean isTrainingProgramDeleted = programDAO.deleteById(programId);
            if (!isTrainingProgramDeleted) {
                connectionController.rollbackTransaction();
                return false;
            }

            connectionController.commitTransaction();
            return true;
        } catch (DAOException exception) {
            connectionController.rollbackTransaction();

            throw new ServiceException("Exception during discard of program.", exception);
        } finally {
            connectionController.endTransaction();
            connectionController.close();
        }
    }

    /**
     * method saves Program object.
     * @param program the program.
     * @return training program's id.
     * @throws ServiceException object if execution of query is failed.
     */
    public int saveProgram(Program program) throws ServiceException {
        try (ConnectionController connectionController = new ConnectionController()) {
           ProgramDAO programDAO = new ProgramDAO(connectionController.getConnection());

            return programDAO.insertProgram(program);
        } catch (DAOException exception) {
            throw new ServiceException("Exception during saving program.", exception);
        }
    }

    /**
     * method creates program.
     * @param authorId author id.
     * @param clientIdValue  the client id valued.
     * @param diet           the diet.
     * @param startDateValue the start date value.
     * @param endDateValue   the end date value.
     * @return TrainingProgram object.
     * @throws ServiceException object if execution of query is failed.
     */
    public Program createProgram(int authorId, String clientIdValue, String diet, String startDateValue, String endDateValue) throws ServiceException {
        try (ConnectionController connectionManager = new ConnectionController()) {
            Program program = new Program();

            int clientId = Integer.parseInt(clientIdValue);
            program.setClientId(clientId);

            Integer personalTrainerId = null;
            ProgramDAO trainingProgramDAO = new ProgramDAO(connectionManager.getConnection());
            boolean isPersonalClientNeed = trainingProgramDAO.isClientNeedsCoach(clientId);
            if (isPersonalClientNeed) {
                personalTrainerId = authorId;
            }
            program.setCoachId(personalTrainerId);

            program.setDiet(diet);

            Date startDate = Date.valueOf(startDateValue);
            program.setStartDate(startDate);

            Date endDate = Date.valueOf(endDateValue);
            program.setEndDate(endDate);

            return program;
        } catch (DAOException exception) {
            throw new ServiceException("Exception during creation of program.", exception);
        }
    }

    /**
     * method creates days and exercises for program.
     * @param daysCountValue the days count value.
     * @return TreeMap object with day number and exercises in that day.
     */
    public TreeMap<Integer, List<Exercise>> getDaysAndExerciseFromTrainingProgram(String daysCountValue) {
        int daysCount = Integer.parseInt(daysCountValue);

        TreeMap<Integer, List<Exercise>> daysAndExercises = new TreeMap<>();
        for (int index = 1; index <= daysCount; index++) {
            List<Exercise> list = new ArrayList<>();
            daysAndExercises.put(index, list);
        }

        return daysAndExercises;
    }

    /**
     * method updates program.
     * @param program the program.
     * @return true if operation was successful and false otherwise.
     * @throws ServiceException object if execution of query is failed.
     */
    public boolean updateProgram(Program program) throws ServiceException {
        try (ConnectionController connectionController = new ConnectionController()) {
            ProgramDAO programDAO = new ProgramDAO(connectionController.getConnection());

            return programDAO.update(program);
        } catch (DAOException exception) {
            throw new ServiceException("Exception during update of the program.", exception);
        }
    }

    /**
     * method finds entity from database by client's id.
     * @param clientId the client's id.
     * @return the entity.
     * @throws ServiceException object if execution of query is failed.
     */
    public Program findTrainingProgramById(int clientId) throws ServiceException {
        try (ConnectionController connectionController = new ConnectionController()) {
            ProgramDAO trainingProgramDAO = new ProgramDAO(connectionController.getConnection());

            return trainingProgramDAO.selectClientProgram(clientId);
        } catch (DAOException exception) {
            throw new ServiceException("Exception during search of program by id.", exception);
        }
    }

    /**
     * method finds program author.
     * @param trainingProgramId the program id.
     * @return the name of author.
     * @throws ServiceException object if execution of method is failed.
     */
    public String findTrainingProgramAuthor(int trainingProgramId) throws ServiceException {
        try (ConnectionController connectionManager = new ConnectionController()) {
            UserDAO userDAO = new UserDAO(connectionManager.getConnection());

            return userDAO.selectTrainingProgramAuthor(trainingProgramId);
        } catch (DAOException exception) {
            throw new ServiceException("Exception during search of program's author.", exception);
        }
    }

    /**
     * method shows exercises from program.
     * @param programId the program id.
     * @return TreeMap of exercises.
     * @throws ServiceException object if execution of method is failed.
     */
    public TreeMap<Integer, List<Exercise>> showExercisesFromProgram(int programId) throws ServiceException {
        try (ConnectionController connectionController = new ConnectionController()) {
            ExerciseDAO exerciseDAO = new ExerciseDAO(connectionController.getConnection());

            return exerciseDAO.selectExerciseFromProgram(programId);
        } catch (DAOException exception) {
            throw new ServiceException("Exception during showing of exercises from program.", exception);
        }
    }

    /**
     * method deletes day from program.
     * @param dayNumberValue   the day number value.
     * @param daysAndExercises the days and exercise in this days.
     * @return TreeMap with new days and exercises.
     */
    public TreeMap<Integer, List<Exercise>> deleteDayFromTrainingProgram(String dayNumberValue, TreeMap<Integer, List<Exercise>> daysAndExercises) {
        int dayNumber = Integer.parseInt(dayNumberValue);
        TreeMap<Integer, List<Exercise>> newDaysAndExercises = new TreeMap<>();

        Set<Map.Entry<Integer, List<Exercise>>> mapSet = daysAndExercises.entrySet();
        for (Map.Entry<Integer, List<Exercise>> entry : mapSet) {
            int day = entry.getKey();
            List<Exercise> exercises = entry.getValue();

            if (day < dayNumber) {
                newDaysAndExercises.put(day, exercises);
            } else if (day > dayNumber) {
                int currentDay = --day;
                newDaysAndExercises.put(currentDay, exercises);
            }
        }
        return newDaysAndExercises;
    }

    /**
     * method adds day in training program.
     * @param daysAndExercises the days and exercise in this days.
     * @return true if operation was successful and false otherwise.
     */
    public boolean addDayInTrainingProgram(TreeMap<Integer, List<Exercise>> daysAndExercises) {
        ProgramValidator trainingProgramDataValidator = new ProgramValidator();
        boolean isDaysCountValid = trainingProgramDataValidator.checkDaysCountForAddOperation(daysAndExercises);
        if (!isDaysCountValid) {
            return false;
        }

        int currentDaysCount = daysAndExercises.size();
        int day = currentDaysCount + DAY_INCREMENT_INDEX;

        List<Exercise> exercises = new ArrayList<>();
        daysAndExercises.put(day, exercises);

        return true;
    }
}
