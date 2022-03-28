package by.training.gym.service;

import by.training.gym.dao.ConnectionController;
import by.training.gym.dao.DAOException;
import by.training.gym.dao.UserDAO;
import by.training.gym.model.User;
import by.training.gym.model.UserRole;
import by.training.gym.validator.UserValidator;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Service class for User entity.
 * @author AlesyaHlushakova
 */
public class UserService {

    private static final String SPLIT_SYMBOL = " ";

    private static final int FIRST_NAME_INDEX = 0;
    private static final int LAST_NAME_INDEX = 1;

    /**
     * method gets authorized user.
     * @param login    the user's login.
     * @param password the user's password.
     * @return the User.
     * @throws ServiceException object if execution of method is failed.
     */
    public User login(String login, String password) throws ServiceException {
        try (ConnectionController connectionController = new ConnectionController()) {
            UserDAO userDAO = new UserDAO(connectionController.getConnection());
            password = DigestUtils.shaHex(password);

            return userDAO.selectUserByLoginAndPassword(login, password);
        } catch (DAOException exception) {
            throw new ServiceException("Exception during logging in.", exception);
        }
    }

    /**
     * The method registers user into data base.
     *
     * @param login     the user's login.
     * @param password  the user's password.
     * @param firstName the user's first name.
     * @param lastName  the user's last name.
     * @return true if operation was made successful and false otherwise.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean register(String login, String password, String firstName,
                            String lastName, String telephone) throws ServiceException {
        try (ConnectionController connectionController = new ConnectionController()) {
            UserDAO userDAO = new UserDAO(connectionController.getConnection());

            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            UserRole userRole = UserRole.CLIENT;
            user.setUserRole(userRole);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setTelephone(telephone);

            return userDAO.insert(user);
        } catch (DAOException exception) {
            throw new ServiceException("Exception during register operation.", exception);
        }
    }

    /**
     * method checks user login for uniqueness.
     * @param login the user's login.
     * @return true if login is unique and false if not.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean checkUserLoginForUniqueness(String login) throws ServiceException {
        try (ConnectionController connectionController = new ConnectionController()) {
            UserDAO userDAO = new UserDAO(connectionController.getConnection());

            return userDAO.checkLoginForUniqueness(login);
        } catch (DAOException exception) {
            throw new ServiceException("Exception during check user login for unique operation.", exception);
        }
    }

    /**
     * method finds user by full name.
     * @param name the user's first name.
     * @return List of users.
     * @throws ServiceException object if execution of method is failed.
     */
    public List<User> findClientByName(String name) throws ServiceException {
        try (ConnectionController connectionController = new ConnectionController()) {
            UserValidator userDataValidator = new UserValidator();
            UserDAO userDAO = new UserDAO(connectionController.getConnection());
            boolean isNameFull = userDataValidator.isNameFull(name);
            if (isNameFull) {
                String[] names = name.split(SPLIT_SYMBOL);
                String firstName = names[FIRST_NAME_INDEX];
                String lastName = names[LAST_NAME_INDEX];

                return userDAO.selectClientByFullName(firstName, lastName);
            } else {
                return userDAO.selectClientByNamePart(name);
            }
        } catch (DAOException exception) {
            throw new ServiceException("Exception during find client by name operation.", exception);
        }
    }

    /**
     * method finds all clients.
     * @return Map of clients and number of records.
     * @throws ServiceException object if execution of method is failed.
     */
    public Map<List<User>, Integer> findAllClientsByPages(int offSet, int numberOfRecords) throws ServiceException {
        try (ConnectionController connectionController = new ConnectionController()) {
            UserDAO userDAO = new UserDAO(connectionController.getConnection());
            ;
            Map<List<User>, Integer> clients = new HashMap<>();

            List<User> findClient = userDAO.selectAllClients(offSet, numberOfRecords);
            Integer countOfRecords = userDAO.getNumberOfRecords();

            clients.put(findClient, countOfRecords);

            return clients;
        } catch (DAOException exception) {
            throw new ServiceException("Exception during find all clients by pages operation.", exception);
        }
    }

    /**
     * method finds all clients of the coach and programs id .
     * @param trainerId the trainer id.
     * @return List with results.
     * @throws ServiceException object if execution of method is failed.
     */
    public List<User> findPersonalClients(int trainerId) throws ServiceException {
        try (ConnectionController connectionController = new ConnectionController()) {
            UserDAO userDAO = new UserDAO(connectionController.getConnection());

            return userDAO.selectPersonalClients(trainerId);
        } catch (DAOException exception) {
            throw new ServiceException("Exception during find personal clients operation.", exception);
        }
    }

    /**
     * method finds clients for program creation.
     * @return Map with id and name.
     * @throws ServiceException object if execution of method is failed.
     */
    public Map<Integer, String> findClientsForProgramCreation() throws ServiceException {
        try (ConnectionController connectionController = new ConnectionController()) {
            UserDAO userDAO = new UserDAO(connectionController.getConnection());

            return userDAO.selectClientIdAndNameForProgramCreation();
        } catch (DAOException exception) {
            throw new ServiceException("Exception during find clients id and name operation.", exception);
        }
    }

}