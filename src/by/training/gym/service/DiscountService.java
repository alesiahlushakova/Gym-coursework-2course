package by.training.gym.service;

import by.training.gym.dao.ConnectionController;
import by.training.gym.dao.DAOException;
import by.training.gym.dao.SubscriptionDAO;
import by.training.gym.model.Subscription;

import java.util.List;

/**
 * service class to get discount for client.
 * @author AlesyaHlushakova
 */
public class DiscountService {

    public static final int NONE_DISCOUNT = 0;

    private static final int MINIMAL_ORDERS_COUNT_FOR_DISCOUNT = 5;
    private static final int MIDDLE_ORDERS_COUNT_FOR_DISCOUNT = 10;
    private static final int MAX_ORDERS_COUNT_FOR_DISCOUNT = 15;

    private static final int MINIMAL_DISCOUNT_PERCENT = 5;
    private static final int MIDDLE_DISCOUNT_PERCENT = 20;
    private static final int MAX_DISCOUNT_PERCENT = 35;

    /**
     * method returns discount.
     * @param clientId the client's id.
     * @return the discount.
     */
    public int getDiscount(int clientId) throws ServiceException {
        try (ConnectionController connectionController = new ConnectionController()) {
            SubscriptionDAO orderDAO = new SubscriptionDAO(connectionController.getConnection());
            List<Subscription> clientOrders = orderDAO.selectClientSubscriptions(clientId);
            int ordersCount = clientOrders.size();

            if (ordersCount == MINIMAL_ORDERS_COUNT_FOR_DISCOUNT || (ordersCount > MINIMAL_ORDERS_COUNT_FOR_DISCOUNT && ordersCount < MIDDLE_ORDERS_COUNT_FOR_DISCOUNT)) {
                return MINIMAL_DISCOUNT_PERCENT;
            }

            if (ordersCount == MIDDLE_ORDERS_COUNT_FOR_DISCOUNT || (ordersCount > MIDDLE_ORDERS_COUNT_FOR_DISCOUNT && ordersCount < MAX_ORDERS_COUNT_FOR_DISCOUNT)) {
                return MIDDLE_DISCOUNT_PERCENT;
            }

            if (ordersCount >= MAX_ORDERS_COUNT_FOR_DISCOUNT) {
                return MAX_DISCOUNT_PERCENT;
            }

            return NONE_DISCOUNT;
        } catch (DAOException exception) {
            throw new ServiceException("Exception during getting discount operation.", exception);
        }
    }
}