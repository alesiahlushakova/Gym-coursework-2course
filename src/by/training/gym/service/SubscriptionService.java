package by.training.gym.service;

import by.training.gym.dao.ConnectionController;
import by.training.gym.dao.DAOException;
import by.training.gym.dao.SubscriptionDAO;
import by.training.gym.model.Subscription;
import by.training.gym.model.SubscriptionType;
import by.training.gym.validator.SubscriptionValidator;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import static by.training.gym.service.DiscountService.NONE_DISCOUNT;

/**
 * service class for Subscription entity.
 * @author AlesyaHlushakova
 */
public class SubscriptionService {

    private static final int NOT_PAYED_ORDER_STATUS = 0;
    private static final int PAYED_ORDER_STATUS = 1;

    /**
     * method checks client for having actual sub.
     * @param clientIdValue the client's id string value.
     * @return true if client has actual order and false otherwise.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean hasClientActualSubscription(String clientIdValue) throws ServiceException {
        try (ConnectionController connectionManager = new ConnectionController()) {
            SubscriptionDAO subscriptionDAO = new SubscriptionDAO(connectionManager.getConnection());
            int clientId = Integer.parseInt(clientIdValue);

            return subscriptionDAO.isActualSubscription(clientId);
        } catch (DAOException exception) {
            throw new ServiceException("Exception during check if "
                    + "client has client actual order operation.", exception);
        }
    }

    /**
     * method adds feedback about order.
     * @param orderId  the order's id.
     * @param feedback the feedback
     * @return true if operation was successful and false otherwise.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean addFeedback(String feedback, int orderId) throws ServiceException {
        try (ConnectionController connectionManager = new ConnectionController()) {
            SubscriptionValidator orderDataValidator = new SubscriptionValidator();
            boolean isDataValid = orderDataValidator.checkFeedback(feedback);
            if (!isDataValid) {
                return false;
            }

            SubscriptionDAO subscriptionDAO = new SubscriptionDAO(connectionManager.getConnection());
            return subscriptionDAO.updateFeedback(feedback, orderId);
        } catch (DAOException exception) {
            throw new ServiceException("Exception during adding feedback operation.", exception);
        }
    }

    /**
     * methods inserts sub into database.
     * @param subscription the Subscription object.
     * @return true if operation was successful and false otherwise.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean paySubscription(Subscription subscription) throws ServiceException {
        ConnectionController connectionManager = new ConnectionController();
        try {
            connectionManager.startTransaction();

            subscription.setIsPayed(PAYED_ORDER_STATUS);

            SubscriptionDAO subscriptionDAO = new SubscriptionDAO(connectionManager.getConnection());
            boolean isOperationSuccessful = subscriptionDAO.insert(subscription);

            if (!isOperationSuccessful) {
                connectionManager.rollbackTransaction();
                return false;
            }

            connectionManager.commitTransaction();
            return true;
        } catch (DAOException exception) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during paying up sub operation.", exception);
        } finally {
            connectionManager.endTransaction();
            connectionManager.close();
        }
    }

    /**
     * method creates Subscription object from input parameters.
     * @param clientId                   the client id.
     * @param purchaseDateValue          the purchase date.
     * @param durationValue              the duration.
     * @param isPersonalTrainerNeedValue is personal trainer need int value.
     * @return Order object.
     * @throws ServiceException object if execution of method is failed.
     */
    public Subscription prepareSubscription(int clientId, String purchaseDateValue, String durationValue, String ibmValue, String isPersonalTrainerNeedValue) throws ServiceException {
        try (ConnectionController connectionManager = new ConnectionController()) {

            Date purchaseDate = Date.valueOf(purchaseDateValue);
            SubscriptionType duration = SubscriptionType.valueOf(durationValue);

            SubscriptionCalculator orderCalculator = new SubscriptionCalculator();
            Date expirationDate = orderCalculator.calculateExpirationDate(duration, purchaseDate);
            SubscriptionDAO subscriptionDAO = new SubscriptionDAO(connectionManager.getConnection());
            int isPersonalTrainerNeed = Integer.parseInt(isPersonalTrainerNeedValue);
            int ibm = Integer.parseInt(ibmValue);
            BigDecimal price = subscriptionDAO.selectPriceForSubscription(duration, isPersonalTrainerNeed);

            DiscountService discountService = new DiscountService();
            int discount = discountService.getDiscount(clientId);

            if (discount != NONE_DISCOUNT) {
                price = orderCalculator.calculatePrice(price, discount);
            }

            Subscription subscription = new Subscription();
            subscription.setClientId(clientId);
            subscription.setPurchaseDate(purchaseDate);
            subscription.setEndDate(expirationDate);
            subscription.setIbm(ibm);
            subscription.setSubscriptionType(duration);
            subscription.setIsPersonalTrainerNeed(isPersonalTrainerNeed);
            subscription.setPrice(price);
            subscription.setIsPayed(NOT_PAYED_ORDER_STATUS);
            subscription.setFeedback(null);

            return subscription;
        } catch (DAOException exception) {
            throw new ServiceException("Exception during prepare order operation.", exception);
        }
    }

    /**
     * method finds all subs of client.
     * @param clientId the client's id.
     * @return List of orders.
     * @throws ServiceException object if execution of method is failed.
     */
    public List<Subscription> findAllClientSubscriptions(int clientId) throws ServiceException {
        try (ConnectionController connectionManager = new ConnectionController()) {
            SubscriptionDAO subscriptionDAO = new SubscriptionDAO(connectionManager.getConnection());

            return subscriptionDAO.selectClientSubscriptions(clientId);
        } catch (DAOException exception) {
            throw new ServiceException("Exception during attempt to find client's subscription operation.", exception);
        }
    }

}
