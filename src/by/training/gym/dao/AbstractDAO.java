package by.training.gym.dao;

import by.training.gym.model.Entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DAO interface has all main methods to work with entity.
 *
 * @param <T> The entity.
 */
public abstract class AbstractDAO<T extends Entity>
        implements DAO<T> {
    public static final int EMPTY_RESULT = 0;
    public static final String NULL_PARAMETER = "null";

    public static final String SELECT_ALL_QUERY_KEY = "SELECT_ALL";
    public static final String SELECT_BY_ID_QUERY_KEY = "SELECT_BY_ID";
    public static final String DELETE_BY_ID_QUERY_KEY = "DELETE_BY_ID";
    public static final String INSERT_ENTITY_QUERY_KEY = "INSERT_ENTITY";
    public static final String UPDATE_ENTITY_QUERY_KEY = "UPDATE_ENTITY";

    private final Map<String, String> commonQueries;
    protected Connection connection;

    /**
     * constructor for a new AbstractDAO.
     * @param newConnection the connection to database.
     */
    protected AbstractDAO(final Connection newConnection) {
        this.connection = newConnection;
        this.commonQueries = initializeCommonQueries();
    }

    /**
     * This method finds all entities.
     *
     * @return List of found objects.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    public List<T> selectAll() throws DAOException {
        String sqlQuery = commonQueries.get(SELECT_ALL_QUERY_KEY);

        try (Statement statement = connection.createStatement()) {

            List<T> entities = new ArrayList<T>();

            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                T entity = buildEntity(resultSet);
                entities.add(entity);
            }

            return entities;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * This method finds entity from database by id.
     *
     * @param id the entity's id.
     * @return the entity.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    public T selectEntityById(final int id) throws DAOException {
        String sqlQuery = commonQueries.get(SELECT_BY_ID_QUERY_KEY);

        try (PreparedStatement preparedStatement
                     = prepareStatementForQuery(sqlQuery, id)) {
            T entity = null;
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                entity = buildEntity(resultSet);
            }

            return entity;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * This method deletes entity from database by id.
     *
     * @param id entity id.
     * @return true if operation was made successfully and false otherwise.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    public boolean deleteById(int id) throws DAOException {
        String sqlQuery = commonQueries.get(DELETE_BY_ID_QUERY_KEY);

        return executeQuery(sqlQuery, id);
    }

    /**
     * This method insert entity in database.
     *
     * @param entity the entity.
     * @return true if operation was made successfully and false otherwise.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    public boolean insert(final T entity) throws DAOException {
        String sqlQuery = commonQueries.get(INSERT_ENTITY_QUERY_KEY);
        List<String> parameters = getEntityParameters(entity);

        return executeQuery(sqlQuery, parameters);
    }

    /**
     * This method update entity in database.
     *
     * @param entity the entity.
     * @return true if operation was made successfully and false otherwise.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    public boolean update(final T entity) throws DAOException {
        String sqlQuery = commonQueries.get(UPDATE_ENTITY_QUERY_KEY);
        List<String> parameters = getEntityParameters(entity);

        int entityId = entity.getId();
        String entityIdValue = String.valueOf(entityId);
        parameters.add(entityIdValue);

        return executeQuery(sqlQuery, parameters);
    }

    /**
     * This method executes query.
     *
     * @param sqlQuery   the sql query.
     * @param parameters the parameters.
     * @return true if result is expected and false otherwise.
     * @throws DAOException object if execution of query is failed.
     */
    protected boolean executeQuery(String sqlQuery, Object... parameters) throws DAOException {
        try (PreparedStatement preparedStatement = prepareStatementForQuery(sqlQuery, parameters)) {
            int queryResult = preparedStatement.executeUpdate();

            return queryResult != EMPTY_RESULT;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * This method executes query.
     *
     * @param sqlQuery   the sql query.
     * @param parameters the parameters.
     * @return true if result is expected and false otherwise.
     * @throws DAOException object if execution of query is failed.
     */
    private boolean executeQuery(String sqlQuery, List<String> parameters) throws DAOException {
        try (PreparedStatement preparedStatement = preparedStatementForQuery(sqlQuery, parameters)) {
            int queryResult = preparedStatement.executeUpdate();

            return queryResult != EMPTY_RESULT;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * This method initialize PreparedStatement object and sets it's parameters.
     *
     * @param sqlQuery   the sql query.
     * @param parameters the sql getEntityParameters.
     * @return PreparedStatement object.
     * @throws DAOException object if execution of query is failed.
     */
    protected PreparedStatement prepareStatementForQuery(String sqlQuery, Object... parameters) throws DAOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            if (parameters != null) {
                int parameterIndex = 1;
                for (Object parameter : parameters) {
                    if (parameter == null) {
                        preparedStatement.setNull(parameterIndex, Types.NULL);
                    } else {
                        preparedStatement.setObject(parameterIndex, parameter);
                    }
                    parameterIndex++;
                }
            }

            return preparedStatement;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * This method initialize PreparedStatement object and sets it's parameters.
     *
     * @param sqlQuery   the sql query.
     * @param parameters the sql getEntityParameters.
     * @return PreparedStatement object.
     * @throws DAOException object if execution of query is failed.
     */
    private PreparedStatement preparedStatementForQuery(String sqlQuery, List<String> parameters) throws DAOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            if (parameters != null) {
                int parameterIndex = 1;
                for (String parameter : parameters) {
                    if (NULL_PARAMETER.equals(parameter)) {
                        preparedStatement.setNull(parameterIndex, Types.NULL);
                    } else {
                        preparedStatement.setString(parameterIndex, parameter);
                    }
                    parameterIndex++;
                }
            }

            return preparedStatement;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }

    }

    /**
     * This method gets entity's parameters.
     *
     * @param entity the entity.
     * @return List object with parameters.
     */
    protected abstract List<String> getEntityParameters(T entity);

    /**
     * This method builds entity from ResultSet object.
     *
     * @param resultSet the result set of statement.
     * @return the entity.
     * @throws DAOException object if execution of query is failed.
     */
    protected abstract T buildEntity(ResultSet resultSet) throws DAOException;

    /**
     * This method initialize queries for common operations.
     *
     * @return Map object with queries.
     */
    protected abstract Map<String, String> initializeCommonQueries();
}
