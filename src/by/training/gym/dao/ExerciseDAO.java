package by.training.gym.dao;

import by.training.gym.model.Exercise;
import by.training.gym.model.ExerciseLevel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ExerciseDAO extends AbstractDAO<Exercise> {

    /**
     * Common queries.
     */
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Exercises";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM Exercises WHERE ExerciseID=?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM Exercises WHERE ExerciseID=?";
    private static final String INSERT_ENTITY_QUERY = "INSERT INTO Exercises (Name, Restrictions, CaloriesLost, FitnessLevel, Description) VALUES(?,?,?,?,?)";
    private static final String UPDATE_ENTITY_QUERY = "UPDATE Exercises SET Name=?, Restrictions=?, CaloriesLost=?, FitnessLevel=?, Description=? WHERE ExerciseID=?";

    private static final String SELECT_EXERCISE_FROM_PROGRAM_QUERY = "SELECT ExerciseID, Name,  Restrictions, CaloriesLost, FitnessLevel, Description, Days, WeightLoss, Sets, Repeats" +
            " FROM Exercises LEFT OUTER JOIN Complex  " +
            "ON Exercises.ExerciseID = Complex.ExerciseID WHERE ProgramID=? ORDER BY Days ASC";

    private static final String INSERT_EXERCISE_INTO_PROGRAM = "INSERT INTO Complex " +
            "(ProgramID, ExerciseID, Days, WeightLoss, Sets, Repeats) VALUES (?,?,?,?,?,?)";
    public static final String ID_COLUMN_LABEL = "ExerciseID";
    private static final String NAME_COLUMN_LABEL = "Name";
    private static final String LEVEL_COLUMN_LABEL = "FitnessLevel";
    private static final String RESTRICTIONS_COLUMN_LABEL = "Restrictions";
    private static final String CALORIES_LOST_COLUMN_LABEL = "CaloriesLost";
    private static final String DESCRIPTION_COLUMN_LABEL = "Description";
    private static final String SETS_COLUMN_LABEL = "Sets";
    private static final String REPEATS_COLUMN_LABEL = "Repeats";
    private static final String DAYS_COLUMN_LABEL = "Days";
    private static final String WEIGHT_LOSS_COLUMN_LABEL = "WeightLoss";

    /**
     * constructor for new AbstractDAO.
     * @param connection the connection to database.
     */
    public ExerciseDAO(Connection connection) {
        super(connection);
    }

    /**
     * method select exercises from program.
     * @param ProgramId the training program id.
     * @return TreeMap with the day number and exercises.
     * @throws DAOException object if execution of query is failed.
     */
    public TreeMap<Integer, List<Exercise>> selectExerciseFromProgram(int ProgramId) throws DAOException {
        try (PreparedStatement preparedStatement = prepareStatementForQuery(SELECT_EXERCISE_FROM_PROGRAM_QUERY, ProgramId)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            TreeMap<Integer, List<Exercise>> exercisesByDays = new TreeMap<>();
            List<Exercise> exercisesByDay = new ArrayList<>();
            int dayIndex = 1;

            while (resultSet.next()) {
                int days = resultSet.getInt(DAYS_COLUMN_LABEL);
                int sets = resultSet.getInt(SETS_COLUMN_LABEL);
                int weightLoss = resultSet.getInt(WEIGHT_LOSS_COLUMN_LABEL);
                int repeats = resultSet.getInt(REPEATS_COLUMN_LABEL);


                if (dayIndex != days) {
                    exercisesByDays.put(dayIndex, exercisesByDay);
                    exercisesByDay = new ArrayList<>();
                    dayIndex = days;
                }

                Exercise exercise = buildEntity(resultSet);
                exercise.setDays(days);
                exercise.setRepeats(repeats);
                exercise.setSets(sets);
                exercise.setWeightLoss(weightLoss);

                exercisesByDay.add(exercise);
            }

            exercisesByDays.put(dayIndex, exercisesByDay);

            return exercisesByDays;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * method insert exercise to  program.
     * @param programId the program id.
     * @param exerciseId        the exercise id.
     * @param days         the day number.
     * @param sets         the sets count.
     * @param repeats      the repeats count.
     * @param weightLoss weight loss.
     * @return true if operation was made successfully and false otherwise.
     * @throws DAOException object if execution of query is failed.
     */
    public boolean insertExerciseIntoProgram(int programId, int exerciseId, int days, int weightLoss, int sets, int repeats) throws DAOException {
        return executeQuery(INSERT_EXERCISE_INTO_PROGRAM, programId, exerciseId, days, weightLoss, sets, repeats);
    }

    /**
     * method gets entity's parameters.
     * @param entity the entity.
     * @return List object with parameters.
     */
    @Override
    protected List<String> getEntityParameters(Exercise entity) {
        List<String> parameters = new ArrayList<>();

        String name = entity.getName();
        parameters.add(name);

        ExerciseLevel level = entity.getLevel();
        String levelValue = String.valueOf(level);
        parameters.add(levelValue);

        String description = entity.getDescription();
        parameters.add(description);

        return parameters;
    }

    /**
     * This method builds Exercise object from ResultSet object.
     *
     * @param resultSet the result set of statement.
     * @return The Exercise object.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    protected Exercise buildEntity(ResultSet resultSet) throws DAOException {
        try {
            Exercise exercise = new Exercise();

            int id = resultSet.getInt(ID_COLUMN_LABEL);
            exercise.setId(id);

            String name = resultSet.getString(NAME_COLUMN_LABEL);
            exercise.setName(name);

            String restrictions = resultSet.getString(RESTRICTIONS_COLUMN_LABEL);
            exercise.setRestrictions(restrictions);

            int calories = resultSet.getInt(CALORIES_LOST_COLUMN_LABEL);
            exercise.setCaloriesLost(calories);

            String levelValue = resultSet.getString(LEVEL_COLUMN_LABEL);
            ExerciseLevel level = ExerciseLevel.valueOf(levelValue);
            exercise.setLevel(level);

            String description = resultSet.getString(DESCRIPTION_COLUMN_LABEL);
            exercise.setDescription(description);

            return exercise;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * method initialize queries for common operations.
     * @return Map object with queries.
     */
    @Override
    protected Map<String, String> initializeCommonQueries() {
        Map<String, String> commonQueries = new HashMap<>();

        commonQueries.put(SELECT_ALL_QUERY_KEY, SELECT_ALL_QUERY);
        commonQueries.put(SELECT_BY_ID_QUERY_KEY, SELECT_BY_ID_QUERY);
        commonQueries.put(DELETE_BY_ID_QUERY_KEY, DELETE_BY_ID_QUERY);
        commonQueries.put(INSERT_ENTITY_QUERY_KEY, INSERT_ENTITY_QUERY);
        commonQueries.put(UPDATE_ENTITY_QUERY_KEY, UPDATE_ENTITY_QUERY);

        return commonQueries;
    }
}