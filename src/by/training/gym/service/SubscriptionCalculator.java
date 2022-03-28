package by.training.gym.service;

import by.training.gym.model.SubscriptionType;

import java.math.BigDecimal;
import java.sql.Date;

/**
        * class that calculates sub's data.
        * @author AlesyaHlushakova
        */
public class SubscriptionCalculator {

    private static final long MONTH_MILLI_SECONDS_COUNT = 2_592_000_000L;
    private static final long WEEK_MILLI_SECONDS_COUNT = 604_800_000L;
    private static final long YEAR_MILLI_SECONDS_COUNT = 31_536_000_000L;

    private static final int PERCENT_INDEX = 100;
    private static final int ROUND_INDEX = 2;

    /**
     * method calculates order's end date.
     * @param subscriptionType     the sub's type.
     * @param purchaseDate the purchase date.
     * @return the end date.
     */
    public Date calculateExpirationDate(SubscriptionType subscriptionType, Date purchaseDate) {
        long time = purchaseDate.getTime();
        Date endDate = null;

        switch (subscriptionType) {
            case YEAR: {
                long result = time + YEAR_MILLI_SECONDS_COUNT;
                endDate = new Date(result);
                break;
            }
            case WEEK: {
                long result = time + WEEK_MILLI_SECONDS_COUNT;
                endDate = new Date(result);
                break;
            }
            case MONTH: {
                long result = time + MONTH_MILLI_SECONDS_COUNT;
                endDate = new Date(result);
                break;
            }
        }

        return endDate;
    }

    /**
     * This method calculate sub's price with discount.
     *
     * @param price    the price.
     * @param discount the discount.
     * @return the calculated price.
     */
    public BigDecimal calculatePrice(BigDecimal price, int discount) {

        BigDecimal discountValue = price.multiply(new BigDecimal(discount)).divide(new BigDecimal(PERCENT_INDEX), ROUND_INDEX);

        return price.subtract(discountValue);
    }
}
