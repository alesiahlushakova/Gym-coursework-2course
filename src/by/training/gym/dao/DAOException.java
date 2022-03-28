package by.training.gym.dao;

import java.sql.SQLException;

/**
 * exceptions thrown from dao level.
 */
public class DAOException extends Throwable {
    /**
     * constructor.
     */
    public DAOException() {
    }

    /**
     * DAOException.
     * @param message the message.
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * DAOException.
     * @param message the message.
     * @param cause   the cause.
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * DAOException.
     * @param cause the cause.
     */
    public DAOException(Throwable cause) {
        super(cause);
    }

    /**
     * DAOException.
     * @param message            the message.
     * @param cause              the cause.
     * @param enableSuppression  the enable suppression.
     * @param writableStackTrace the writable stack trace.
     */
    public DAOException(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
