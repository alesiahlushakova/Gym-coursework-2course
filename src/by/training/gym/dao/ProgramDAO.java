package by.training.gym.dao;

import by.training.gym.model.Program;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramDAO extends AbstractDAO<Program> {
    /**
     * Common queries.
     */
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Program";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM Program WHERE ProgramID=?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM Program WHERE ProgramID=?";
    private static final String INSERT_ENTITY_QUERY = "INSERT INTO Program (CoachID, ClientID, StartDate, EndDate, Diet) VALUES(?,?,?,?,?)";
    private static final String UPDATE_ENTITY_QUERY = "UPDATE Program SET  CoachID=?, ClientID=?, StartDate=?, EndDate=?, Diet=? WHERE ProgramID=?";

    private static final String SELECT_IS_COACH_NEEDED_VALUE_QUERY = "SELECT IsCoachNeeded FROM Subscription WHERE ClientID=?";
    private static final String SELECT_CLIENT_PROGRAM_QUERY = "SELECT * FROM Program WHERE ProgramID IN (SELECT MAX(ProgramID) FROM Program WHERE ClientID=?)";
    private static final String SELECT_LAST_INSERT_ID_QUERY = "SELECT LAST_INSERT_ID()";
    private static final String DELETE_EXERCISES_FROM_PROGRAM_QUERY = "DELETE FROM Complex WHERE ProgramID=?";

    public static final String ID_COLUMN_LABEL = "ProgramID";
    private static final String COACH_ID_COLUMN_LABEL = "CoachID";
    private static final String CLIENT_ID_COLUMN_LABEL = "ClientID";
    private static final String START_DATE_COLUMN_LABEL = "StartDate";
    private static final String END_DATE_COLUMN_LABEL = "EndDate";
    private static final String DIET_COLUMN_LABEL = "Diet";
    private static final String LAST_INSERT_ID_COLUMN_LABEL = "LAST_INSERT_ID()";
    private static final String IS_COACH_NEEDED_COLUMN_LABEL = "IsCoachNeeded";

    /**
     * constructor.
     * @param connection the connection to database.
     */
    public ProgramDAO(Connection connection) {
        super(connection);
    }

    /**
     * method selects client's program.
     * @param clientId the client's id.
     * @return program object.
     * @throws DAOException object if execution of query is failed.
     */
    public Program selectClientProgram(int clientId) throws DAOException {
        try (PreparedStatement preparedStatement = prepareStatementForQuery(SELECT_CLIENT_PROGRAM_QUERY, clientId)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            Program trainingProgram = null;
            if (resultSet.next()) {
                trainingProgram = buildEntity(resultSet);
            }

            return trainingProgram;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * method inserts into Program object and selects its id.
     * @param trainingProgram the training program.
     * @return training program's id.
     * @throws DAOException object if execution of query is failed.
     */
    public int insertProgram(Program trainingProgram) throws DAOException {
        int lastId = 0;

        boolean isOperationSuccessful = insert(trainingProgram);
        if (isOperationSuccessful) {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(SELECT_LAST_INSERT_ID_QUERY);
                if (resultSet.next()) {
                    lastId = resultSet.getInt(LAST_INSERT_ID_COLUMN_LABEL);
                }
            } catch (SQLException exception) {
                throw new DAOException(exception.getMessage(), exception);
            }
        }
        return lastId;
    }

    /**
     * method deletes exercises from training program.
     * @param programId the training program id.
     * @return true if operation was successful and false otherwise.
     * @throws DAOException object if execution of query is failed.
     */
    public boolean deleteExercisesFromProgram(int programId) throws DAOException {
        return executeQuery(DELETE_EXERCISES_FROM_PROGRAM_QUERY, programId);
    }

    /**
     * method selects isCoachNeeded  for client.
     * @param clientId the client's id.
     * @return true if client needs personal trainer and false otherwise.
     * @throws DAOException object if execution of query is failed.
     */
    public boolean isClientNeedsCoach(int clientId) throws DAOException {
        try (PreparedStatement preparedStatement = prepareStatementForQuery(SELECT_IS_COACH_NEEDED_VALUE_QUERY, clientId)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int value = resultSet.getInt(IS_COACH_NEEDED_COLUMN_LABEL);

                if (value == 1) {
                    return true;
                } else if (value == 0) {
                    return false;
                } else {
                    throw new DAOException(String.format("Unexpected result from query - %s.", SELECT_IS_COACH_NEEDED_VALUE_QUERY));
                }
            } else {
                throw new DAOException(String.format("Unexpected result from query - %s.", SELECT_IS_COACH_NEEDED_VALUE_QUERY));
            }

        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * method gets entity's parameters.
     * @param entity the entity.
     * @return List object with parameters.
     */
    @Override
    protected List<String> getEntityParameters(Program entity) {
        List<String> parameters = new ArrayList<>();



        Integer coachId = entity.getCoachId();
        if (coachId == null) {
            parameters.add(NULL_PARAMETER);
        } else {
            String personalTrainerIdValue = String.valueOf(coachId);
            parameters.add(personalTrainerIdValue);
        }

        int clientId = entity.getClientId();
        String clientIdValue = String.valueOf(clientId);
        parameters.add(clientIdValue);

        Date startDate = (Date) entity.getStartDate();
        String startDateValue = String.valueOf(startDate);
        parameters.add(startDateValue);

        Date endDate = (Date) entity.getEndDate();
        String endDateValue = String.valueOf(endDate);
        parameters.add(endDateValue);

        String diet = entity.getDiet();
        parameters.add(diet);

        return parameters;
    }

    /**
     * method builds Program object from ResultSet object.
     * @param resultSet the result set of statement.
     * @return The TrainingProgram object.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    protected Program buildEntity(ResultSet resultSet) throws DAOException {
        try {
            Program trainingProgram = new Program();

            int id = resultSet.getInt(ID_COLUMN_LABEL);
            trainingProgram.setId(id);


            Integer personalTrainerId = resultSet.getInt(COACH_ID_COLUMN_LABEL);
            if (personalTrainerId == 0) {
                personalTrainerId = null;
            }
            trainingProgram.setCoachId(personalTrainerId);

            int clientId = resultSet.getInt(CLIENT_ID_COLUMN_LABEL);
            trainingProgram.setClientId(clientId);

            Date startDate = resultSet.getDate(START_DATE_COLUMN_LABEL);
            trainingProgram.setStartDate(startDate);

            Date endDate = resultSet.getDate(END_DATE_COLUMN_LABEL);
            trainingProgram.setEndDate(endDate);

            String diet = resultSet.getString(DIET_COLUMN_LABEL);
            trainingProgram.setDiet(diet);

            return trainingProgram;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * method initializes queries for common operations.
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
