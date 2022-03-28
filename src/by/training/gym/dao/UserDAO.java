package by.training.gym.dao;

import by.training.gym.model.User;
import by.training.gym.model.UserRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO extends AbstractDAO<User>{
    /**
     * Common queries.
     */
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Users";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM Users WHERE UserID=?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM Users WHERE UserID=?";
    private static final String INSERT_ENTITY_QUERY = "INSERT INTO Users (Login, Password, Role, "
           + "Firstname, Lastname, Telephone) VALUES(?,?,?,?,?,?)";
    private static final String UPDATE_ENTITY_QUERY = "UPDATE Users SET Login=?, Password=?,"
            + " Role=?, Firstname=?, Lastname=? telephone=? WHERE UserID=?";

    private static final String SELECT_USER_BY_LOGIN_AND_PASSWORD_QUERY = "SELECT * FROM Users WHERE Login=? AND Password=?";
    private static final String SELECT_USERS_BY_LOGIN_QUERY = "SELECT * FROM Users WHERE Login=?";
    private static final String SELECT_CLIENTS_BY_FULL_NAME_QUERY = "SELECT * FROM Users WHERE Firstname=? AND Lastname=? AND Role='CLIENT'";
    private static final String SELECT_CLIENTS_BY_NAME_PART_QUERY = "SELECT * FROM Users WHERE Role='CLIENT' AND Firstname LIKE ? OR Lastname LIKE ?";
    private static final String SELECT_USERS_BY_FOUND_ROWS_QUERY = "SELECT SQL_CALC_FOUND_ROWS * FROM Users WHERE Role='CLIENT' LIMIT %d, %d";
    private static final String SELECT_FOUND_ROWS_QUERY = "SELECT FOUND_ROWS()";
    private static final String SELECT_PERSONAL_CLIENTS = "SELECT * FROM Users WHERE UserID IN " +
            "(SELECT ClientID FROM Program WHERE CoachID=? AND EndDate > CURDATE())";

    private static final String SELECT_PROGRAM_AUTHOR_NAME_QUERY = "SELECT Firstname, Lastname FROM Users WHERE userID IN" +
            " (SELECT CoachID FROM Program WHERE ProgramID=?)";

    private static final String SELECT_CLIENT_ID_AND_NAME_FOR_PROGRAM_CREATION_QUERY = "call SELECT_ACTUAL_CLIENT()";


    public static final String ID_COLUMN_LABEL = "UserID";
    private static final String LOGIN_COLUMN_LABEL = "Login";
    private static final String PASSWORD_COLUMN_LABEL = "Password";
    private static final String ROLE_COLUMN_LABEL = "Role";
    private static final String FIRST_NAME_COLUMN_LABEL = "Firstname";
    private static final String LAST_NAME_COLUMN_LABEL = "Lastname";
    private static final String TELEPHONE_COLUMN_LABEL = "Telephone";
    private static final int FIRST_COLUMN_INDEX = 1;

    private static final String EMPTY_NAME = "";

    private int numberOfRecords;

    /**
     * constructor.
     * @param connection the connection to database.
     */
    public UserDAO(Connection connection) {
        super(connection);
    }

    /**
     * Gets number of records.
     * @return the number of records.
     */
    public int getNumberOfRecords() {
        return numberOfRecords;
    }

    /**
     * method selects user in database by it's login and password.
     * @param login    the user's login.
     * @param password the user's password.
     * @return the User object.
     * @throws DAOException object if execution of query is failed.
     */
    public User selectUserByLoginAndPassword(String login, String password) throws DAOException {
        try (PreparedStatement preparedStatement = prepareStatementForQuery(
                SELECT_USER_BY_LOGIN_AND_PASSWORD_QUERY, login, password)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            User user = null;
            if (resultSet.next()) {
                user = buildEntity(resultSet);
            }

            return user;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * method checks user's login for uniqueness.
     * @param login the user's login.
     * @return true if login is unique, else returns false.
     * @throws DAOException object if execution of query is failed.
     */
    public boolean checkLoginForUniqueness(String login) throws DAOException {
        try (PreparedStatement preparedStatement
                     = prepareStatementForQuery(SELECT_USERS_BY_LOGIN_QUERY, login)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * method selects users by full name.
     * @param firstName the user's name.
     * @param lastName  the user's name.
     * @return List of found users.
     * @throws DAOException object if execution of query is failed.
     */
    public List<User> selectClientByFullName(String firstName,
                                             String lastName) throws DAOException {
        try (PreparedStatement preparedStatement
                     = prepareStatementForQuery(SELECT_CLIENTS_BY_FULL_NAME_QUERY,
                firstName, lastName)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<User> findUsers = new ArrayList<>();
            while (resultSet.next()) {
                User user = buildEntity(resultSet);

                findUsers.add(user);
            }

            return findUsers;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * method selects client by name part.
     * @param name the user's name.
     * @return List of found users.
     * @throws DAOException object if execution of query is failed.
     */
    public List<User> selectClientByNamePart(String name) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENTS_BY_NAME_PART_QUERY)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<User> findUsers = new ArrayList<>();
            while (resultSet.next()) {
                User user = buildEntity(resultSet);

                findUsers.add(user);
            }

            return findUsers;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * method selects all clients in database.
     * @return List of clients.
     * @throws DAOException object if execution of query is failed.
     */
    public List<User> selectAllClients(int offSet, int numberOfRecords) throws DAOException {
        try (Statement statement = connection.createStatement()) {
            String sqlQuery = String.format(SELECT_USERS_BY_FOUND_ROWS_QUERY,
                    offSet, numberOfRecords);
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            List<User> findUsers = new ArrayList<>();
            while (resultSet.next()) {
                User user = buildEntity(resultSet);

                findUsers.add(user);
            }

            resultSet = statement.executeQuery(SELECT_FOUND_ROWS_QUERY);
            if (resultSet.next()) {
                this.numberOfRecords = resultSet.getInt(FIRST_COLUMN_INDEX);
            }

            return findUsers;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * method selects all clients of current coach.
     * @param coachId the trainer id.
     * @return List with clients.
     * @throws DAOException object if execution of query is failed.
     */
    public List<User> selectPersonalClients(int coachId) throws DAOException {
        try (PreparedStatement findClientsStatement
                     = prepareStatementForQuery(SELECT_PERSONAL_CLIENTS,
                coachId)) {
            List<User> clients = new ArrayList<>();
            ResultSet resultSet = findClientsStatement.executeQuery();

            while (resultSet.next()) {
                User user = buildEntity(resultSet);
                clients.add(user);
            }

            return clients;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * method selects program name.
     * @param ProgramId the training program id.
     * @return the name of author.
     * @throws DAOException object if execution of query is failed.
     */
    public String selectTrainingProgramAuthor(int ProgramId) throws DAOException {
        try (PreparedStatement preparedStatement
                     = prepareStatementForQuery(SELECT_PROGRAM_AUTHOR_NAME_QUERY,
                ProgramId)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString(FIRST_NAME_COLUMN_LABEL);
                String lastName = resultSet.getString(LAST_NAME_COLUMN_LABEL);

                return String.format("%s %s", firstName, lastName);
            } else {
                return EMPTY_NAME;
            }
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * method selects client's id and name for program creation operation.
     * @return Map with id and name.
     * @throws DAOException object if execution of query is failed.
     */
    public Map<Integer, String> selectClientIdAndNameForProgramCreation() throws DAOException {
        try (CallableStatement statement = connection.
                prepareCall(SELECT_CLIENT_ID_AND_NAME_FOR_PROGRAM_CREATION_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            Map<Integer, String> clientsIdAndName = new HashMap<>();

            while (resultSet.next()) {

                int id = resultSet.getInt(ID_COLUMN_LABEL);
                String firstName = resultSet.getString(FIRST_NAME_COLUMN_LABEL);
                String lastName = resultSet.getString(LAST_NAME_COLUMN_LABEL);
                String fullName = String.format("%s %s", firstName, lastName);

                clientsIdAndName.put(id, fullName);
            }

            return clientsIdAndName;
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
    protected List<String> getEntityParameters(User entity) {
        List<String> parameters = new ArrayList<>();

        String login = entity.getLogin();
        parameters.add(login);

        String password = entity.getPassword();
        parameters.add(password);

        UserRole role = entity.getUserRole();
        String roleValue = String.valueOf(role);
        parameters.add(roleValue);

        String firstName = entity.getFirstName();
        parameters.add(firstName);

        String lastName = entity.getLastName();
        parameters.add(lastName);

        String telephone = entity.getTelephone();
        parameters.add(telephone);

        return parameters;
    }

    /**
     * method builds Users from ResultSet object.
     * @param resultSet the result set of statement.
     * @return The User object.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    protected User buildEntity(ResultSet resultSet) throws DAOException {
        try {
            User user = new User();

            int id = resultSet.getInt(ID_COLUMN_LABEL);
            user.setId(id);

            String login = resultSet.getString(LOGIN_COLUMN_LABEL);
            user.setLogin(login);

            String password = resultSet.getString(PASSWORD_COLUMN_LABEL);
            user.setPassword(password);

            String userRoleValue = resultSet.getString(ROLE_COLUMN_LABEL);
            UserRole userRole = UserRole.valueOf(userRoleValue);
            user.setUserRole(userRole);

            String firstName = resultSet.getString(FIRST_NAME_COLUMN_LABEL);
            user.setFirstName(firstName);

            String lastName = resultSet.getString(LAST_NAME_COLUMN_LABEL);
            user.setLastName(lastName);

            String telephone = resultSet.getString(TELEPHONE_COLUMN_LABEL);
            user.setTelephone(telephone);

            return user;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * This method initialize queries for common operations.
     *
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
