package by.training.gym.dao;

import by.training.gym.dao.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionController implements AutoCloseable{
    private static final Logger LOGGER = LogManager.getLogger(ConnectionController.class);
    private final Connection connection;
    private ConnectionPool connectionPool;

    /**
     * constructor.
     */
    public ConnectionController() {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
    }

    /**
     * The method starts transaction.
     */
    public void startTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException exception) {
            LOGGER.error("Transaction start failed. ", exception);
        }
    }

    /**
     * The method commits transaction.
     */
    public void commitTransaction() {
        try {
            connection.commit();
        } catch (SQLException exception) {
            LOGGER.error("Transaction commit failed. ", exception);
        }
    }

    /**
     * method rollbacks transaction.
     */
    public void rollbackTransaction() {
        try {
            connection.rollback();
        } catch (SQLException exception) {
            LOGGER.error("Transaction rollback failed. ", exception);
        }
    }

    /**
     * method ends transaction.
     */
    public void endTransaction() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException exception) {
            LOGGER.error("Transaction end failed. ", exception);
        }
    }

    /**
     * Gets connection.
     *
     * @return the connection.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Implementation of AutoCloseable interface to work with try().
     */
    @Override
    public void close() {
        connectionPool.returnConnection(connection);
    }
}
