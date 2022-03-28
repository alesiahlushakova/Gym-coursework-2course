package by.training.gym.controller.command;

import by.training.gym.model.User;
import by.training.gym.service.ServiceException;
import by.training.gym.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.training.gym.util.MessageManager.LOGIN_ERROR_MESSAGE_KEY;

/**
 * log in command class.
 * @author AlesyaHlushakova
 */
public class LoginCommand implements CommandAction {

    private final static Logger LOGGER = LogManager.getLogger(LoginCommand.class);

    /**
     * method for logging in.
     * @param request HttpServletRequest object.
     * @return page.
     */
    @Override
    public CurrentJsp execute(HttpServletRequest request) {

        try {
            String login = request.getParameter(LOGIN_PARAMETER);
            String password = request.getParameter(PASSWORD_PARAMETER);
            UserService userService = new UserService();
            User user = userService.login(login, password);

            if (user == null) {
                return new CurrentJsp(CurrentJsp.LOGIN_PAGE_PATH,
                        false,  LOGIN_ERROR_MESSAGE_KEY);
            }

            HttpSession currentSession = request.getSession();
            currentSession.setAttribute(USER_ATTRIBUTE, user);

            return new CurrentJsp(CurrentJsp.MAIN_PAGE_PATH,
                    true);
        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new CurrentJsp(CurrentJsp.ERROR_PAGE_PATH, true);
        }
    }
}
