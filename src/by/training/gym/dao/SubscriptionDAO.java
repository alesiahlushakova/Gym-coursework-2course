package by.training.gym.dao;

import by.training.gym.model.Subscription;
import by.training.gym.model.SubscriptionType;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SubscriptionDAO extends AbstractDAO<Subscription> {

    /**
     * Common queries.
     */
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Subscription";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM Subscription WHERE SubscriptionID=?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM Subscription WHERE SubscriptionID=?";
    private static final String INSERT_ENTITY_QUERY = "INSERT INTO Subscription (ClientID, PurchaseDate, "
            + "ExpirationDate, Trial, Price, IBM, IsCoachNeeded, IsPayed, Feedback)VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_ENTITY_QUERY = "UPDATE orders SET ClientID=?, "
            + "PurchaseDate=?, ExpirationDate=?, Trial=?, Price=?, IBM=?, IsCoachNeeded=?,"
            + "  IsPayed=?, Feedback=? WHERE SubscriptionID=?";

    private static final String SELECT_CLIENT_ORDERS_QUERY = "SELECT * FROM Subscription WHERE ClientID=?";
    private static final String SELECT_CLIENT_ACTUAL_ORDER_QUERY = "SELECT * FROM Subscription"
            + " WHERE ClientID=? AND ExpirationDate>=CURDATE()";
    private static final String SELECT_PRICE_FOR_ORDER_QUERY = "SELECT Price FROM Pricing WHERE SubscriptionType=?";
    private static final String UPDATE_FEEDBACK_QUERY = "UPDATE Subscription SET Feedback=? WHERE SubscriptionID=?";

    public static final String ID_COLUMN_LABEL = "SubscriptionID";
    private static final String CLIENT_ID_COLUMN_LABEL = "ClientID";
    private static final String PURCHASE_DATE_COLUMN_LABEL = "PurchaseDate";
    private static final String EXPIRATION_DATE_COLUMN_LABEL = "ExpirationDate";
    private static final String TRIAL_COLUMN_LABEL = "Trial";
    private static final String IBM_COLUMN_LABEL = "IBM";
    private static final String IS_COACH_NEEDED = "IsCoachNeeded";
    private static final String PRICE_COLUMN_LABEL = "Price";
    private static final String IS_PAYED_COLUMN_LABEL = "IsPayed";
    private static final String FEEDBACK_COLUMN_LABEL = "Feedback";

    private static final int COACH_NEEDED_TRUE_INDEX = 1;

    private static final String PRICE_WITH_COACH_PARAMETER_PART = "+COACH";

    /**
     * constructor.
     * @param connection the connection to database.
     */
    public SubscriptionDAO(Connection connection) {
        super(connection);
    }

    /**
     * method updates feedback in sub.
     * @param orderId  the order's id.
     * @param feedback the feedback
     * @return true if operation was successful and false otherwise.
     * @throws DAOException object if execution of query is failed.
     */
    public boolean updateFeedback(String feedback, int orderId) throws DAOException {

        return executeQuery(UPDATE_FEEDBACK_QUERY, feedback, orderId);

    }

    /**
     * method selects all client's subs from database.
     * @param clientId the client id.
     * @return List of orders.
     * @throws DAOException object if execution of query is failed.
     */
    public List<Subscription> selectClientSubscriptions(int clientId)
            throws DAOException {
        try (PreparedStatement preparedStatement
                     = prepareStatementForQuery(SELECT_CLIENT_ORDERS_QUERY,
                clientId)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Subscription> orders = new ArrayList<>();

            while (resultSet.next()) {
                Subscription order = buildEntity(resultSet);

                orders.add(order);
            }
            return orders;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * method select price from database.
     * @param type              the sub's duration
     * @param isCoachNeeded the int value of variable isCoachNeeded.
     * @return the price.
     * @throws DAOException object if execution of query is failed.
     */
    public BigDecimal selectPriceForSubscription(SubscriptionType type, int isCoachNeeded) throws DAOException {
        String parameter = String.valueOf(type);
        if (isCoachNeeded == COACH_NEEDED_TRUE_INDEX) {
            parameter += PRICE_WITH_COACH_PARAMETER_PART;
        }
        try (PreparedStatement preparedStatement = prepareStatementForQuery(SELECT_PRICE_FOR_ORDER_QUERY, parameter)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            BigDecimal price = null;
            if (resultSet.next()) {
                price = resultSet.getBigDecimal(IBM_COLUMN_LABEL);
            }

            return price;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * method checks client for having actual sub.
     * @param clientId the client's id.
     * @return true if client has actual order and false otherwise.
     * @throws DAOException object if execution of query is failed.
     */
    public boolean isActualSubscription(int clientId) throws DAOException {
        try (PreparedStatement preparedStatement = prepareStatementForQuery(
                SELECT_CLIENT_ACTUAL_ORDER_QUERY, clientId)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
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
    protected List<String> getEntityParameters(Subscription entity) {
        List<String> parameters = new ArrayList<>();

        int clientId = entity.getClientId();
        String clientIdValue = String.valueOf(clientId);
        parameters.add(clientIdValue);

        Date purchaseDate = entity.getPurchaseDate();
        String purchaseDateValue = String.valueOf(purchaseDate);
        parameters.add(purchaseDateValue);

        Date expirationDate = entity.getExpirationDate();
        String expirationDateValue = String.valueOf(expirationDate);
        parameters.add(expirationDateValue);

        SubscriptionType trial = entity.getSubscriptionType();
        String trialValue = String.valueOf(trial);
        parameters.add(trialValue);

        BigDecimal price = entity.getPrice();
        String priceValue = String.valueOf(price);
        parameters.add(priceValue);

        int ibm = entity.getIbm();
        String ibmValue = String.valueOf(ibm);
        parameters.add(ibmValue);

        int isCoachNeeded = entity.getIsCoachNeeded();
        String isCoachNeededValue = String.valueOf(isCoachNeeded);
        parameters.add(isCoachNeededValue);

        int isPayed = entity.getIsPayed();
        String isPayedValue = String.valueOf(isPayed);
        parameters.add(isPayedValue);

        String feedback = entity.getFeedback();
        if (feedback == null) {
            parameters.add(NULL_PARAMETER);
        } else {
            parameters.add(feedback);
        }

        return parameters;
    }

    /**
     * method builds Sub  from ResultSet object.
     * @param resultSet the result set of statement.
     * @return the Order object.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    protected Subscription buildEntity(ResultSet resultSet) throws DAOException {
        try {
            Subscription order = new Subscription();

            int id = resultSet.getInt(ID_COLUMN_LABEL);
            order.setId(id);

            int clientId = resultSet.getInt(CLIENT_ID_COLUMN_LABEL);
            order.setClientId(clientId);

            Date purchaseDate = resultSet.getDate(PURCHASE_DATE_COLUMN_LABEL);
            order.setPurchaseDate(purchaseDate);

            Date endDate = resultSet.getDate(EXPIRATION_DATE_COLUMN_LABEL);
            order.setEndDate(endDate);

            String durationValue = resultSet.getString(TRIAL_COLUMN_LABEL);
            SubscriptionType duration = SubscriptionType.valueOf(durationValue);
            order.setSubscriptionType(duration);

            BigDecimal price = resultSet.getBigDecimal(PRICE_COLUMN_LABEL);
            order.setPrice(price);

            int ibm = resultSet.getInt(IBM_COLUMN_LABEL);
            order.setIbm(ibm);

            int isPersonalTrainerNeed = resultSet.getInt(IS_COACH_NEEDED);
            order.setIsPersonalTrainerNeed(isPersonalTrainerNeed);



            int isPayed = resultSet.getInt(IS_PAYED_COLUMN_LABEL);
            order.setIsPayed(isPayed);

            String feedback = resultSet.getString(FEEDBACK_COLUMN_LABEL);
            order.setFeedback(feedback);

            return order;
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
