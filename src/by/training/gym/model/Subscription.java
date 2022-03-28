package by.training.gym.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * class - entity for clients' subs.
 * @author AlesyaHlushakova
 */
public class Subscription extends Entity{
    private int clientId;
    private Date purchaseDate;
    private Date expirationDate;
    private SubscriptionType subscriptionType;
    private int isPersonalTrainerNeed;
    private int ibm;
    private BigDecimal price;
    private int isPayed;
    private String feedback;

    /**
     * constructor for new sub.
     */
    public Subscription() {
    }

    /**
     * getter for client id.
     *
     * @return the client id.
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * setter for client id.
     *
     * @param clientId the client id.
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * getter for order's purchase date.
     *
     * @return the purchase date.
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * setter for order's purchase date.
     *
     * @param purchaseDate the purchase date.
     */
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * getter for order's expiration date.
     *
     * @return the expiration date.
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * setter for order's expiration date.
     *
     * @param expDate the expiration date.
     */
    public void setEndDate(Date expDate) {
        this.expirationDate = expDate;
    }

    /**
     * getter for order sub type.
     *
     * @return the sub type.
     */
    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    /**
     * setter for order's sub type.
     *
     * @param subType the sub type.
     */
    public void setSubscriptionType(SubscriptionType subType) {
        this.subscriptionType = subType;
    }

    /**
     * getter for int value of variable isPersonalTrainerNeed.
     *
     * @return the int value.
     */
    public int getIsCoachNeeded() {
        return isPersonalTrainerNeed;
    }

    /**
     * setter for int value of variable isPersonalTrainerNeed.
     *
     * @param isPersonalTrainerNeed the int value.
     */
    public void setIsPersonalTrainerNeed(int isPersonalTrainerNeed) {
        this.isPersonalTrainerNeed = isPersonalTrainerNeed;
    }

    /**
     * getter for order's price.
     *
     * @return the price.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * setter for order's price.
     *
     * @param price the price.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * gettter for int value of variable isPayed.
     *
     * @return the int value.
     */
    public int getIsPayed() {
        return isPayed;
    }

    /**
     * setter for int value of variable isPayed.
     *
     * @param isPayed the int value.
     */
    public void setIsPayed(int isPayed) {
        this.isPayed = isPayed;
    }

    /**
     * gettter for IBM.
     *
     * @return the IBM.
     */
    public int getIbm() {
        return ibm;
    }

    /**
     * setter for IBM.
     *
     * @param iBM the IBM.
     */
    public void setIbm(int iBM) {
        this.ibm = iBM;
    }

    /**
     * getter for order's feedback.
     *
     * @return the feedback.
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * setter for order's feedback. Can be null.
     *
     * @param feedback the feedback.
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }


    /**
     * This method calculate object's hashcode.
     *
     * @return hashcode of object.
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + clientId;
        result = 31 * result + (purchaseDate != null
                ? purchaseDate.hashCode() : 0);
        result = 31 * result + (expirationDate != null
                ? expirationDate.hashCode() : 0);
        result = 31 * result + (subscriptionType != null
                ? subscriptionType.hashCode() : 0);
        result = 31 * result + isPersonalTrainerNeed;
        result = 31 * result + (price != null
                ? price.hashCode() : 0);
        result = 31 * result + isPayed;
        result = 31 * result + (feedback != null
                ? feedback.hashCode() : 0);
        return result;
    }

    /**
     * method overrides equals.
     *
     * @param o the object.
     * @return true if objects are equal and false otherwise.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscription)) return false;
        Subscription that = (Subscription) o;
        return getClientId() == that.getClientId() &&
                getIsCoachNeeded() == that.
                        getIsCoachNeeded() &&
                getIbm() == that.getIbm() &&
                getIsPayed() == that.getIsPayed() &&
                getPurchaseDate().equals(that.getPurchaseDate()) &&
                getExpirationDate().equals(that.getExpirationDate()) &&
                getSubscriptionType() == that.getSubscriptionType() &&
                getPrice().equals(that.getPrice()) &&
                getFeedback().equals(that.getFeedback());
    }

    /**
     * method overrides to string.
     *
     * @return string information about object.
     */
    @Override
    public String toString() {
        return "Order{" +
                "clientId=" + clientId +
                ", purchaseDate=" + purchaseDate +
                ", endDate=" + expirationDate +
                ", duration=" + subscriptionType +
                ", isPersonalTrainerNeed=" + isPersonalTrainerNeed +
                ", price=" + price +
                ", ibm=" + ibm +
                ", isPayed=" + isPayed +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
