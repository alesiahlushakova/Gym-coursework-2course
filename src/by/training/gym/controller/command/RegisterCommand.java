package by.training.gym.controller.command;

import by.training.gym.service.ServiceException;
import by.training.gym.service.UserService;
import by.training.gym.validator.UserValidator;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.training.gym.util.MessageManager.*;

public class RegisterCommand implements CommandAction {

    private static final Logger LOGGER = LogManager.getLogger(RegisterCommand.class);

    /**
     * command that registers user.
     * @param request HttpServletRequest object.
     * @return page.
     */
    @Override
    public CurrentJsp execute(HttpServletRequest request) {

        try {
            String login = request.getParameter(LOGIN_PARAMETER);
            String password = request.getParameter(PASSWORD_PARAMETER);
            String firstName = request.getParameter(FIRST_NAME_PARAMETER);
            String lastName = request.getParameter(LAST_NAME_PARAMETER);
            String telephone = request.getParameter(TELEPHONE_PARAMETER);

            UserService userService = new UserService();
            boolean isLoginNotUnique = userService.checkUserLoginForUniqueness(login);
            if (isLoginNotUnique) {
                return new CurrentJsp(CurrentJsp.REGISTER_PAGE_PATH,
                        false, LOGIN_NOT_AVAILABLE_MESSAGE_KEY);
            }

            UserValidator userDataValidator = new UserValidator();
            boolean isUserDataValid = userDataValidator.checkData(login, password,
                    firstName, lastName, telephone);
            if (!isUserDataValid) {
                return new CurrentJsp(CurrentJsp.REGISTER_PAGE_PATH,
                        false, INVALID_INPUT_DATA_MESSAGE_KEY);
            }

            password = DigestUtils.shaHex(password);
            boolean isOperationSuccessful = userService.register(login, password,
                    firstName, lastName, telephone);
            if (!isOperationSuccessful) {
                return new CurrentJsp(CurrentJsp.REGISTER_PAGE_PATH,
                        false, REGISTRATION_FAILED_MESSAGE_KEY);
            }

            HttpSession session = request.getSession();
            session.setAttribute(IS_RECORD_INSERTED, true);

            return new CurrentJsp(CurrentJsp.LOGIN_PAGE_PATH, false,
                    REGISTRATION_SUCCESSFUL_MESSAGE_KEY);

        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new CurrentJsp(CurrentJsp.ERROR_PAGE_PATH, true);
        }
    }
}
