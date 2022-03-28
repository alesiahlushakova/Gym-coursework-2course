package by.training.gym.service;

/**
 * exceptions that are thrown from service level.
 * @author AlesyaHlushakova
 */
public class ServiceException extends Exception {


    /**
     * empty constructor.
     */
    public ServiceException() {
    }

    /**
     * constructor for a new ServiceException.
     * @param message the exception message.
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * constructor for  a new ServiceException.
     * @param message the message.
     * @param cause   the cause.
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * constructor for a new ServiceException.
     * @param cause the cause.
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * constructor for a new ServiceException.
     * @param message            the message.
     * @param cause              the cause.
     * @param enableSuppression  the enable suppression.
     * @param writableStackTrace the writable stacktrace.
     */
    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
