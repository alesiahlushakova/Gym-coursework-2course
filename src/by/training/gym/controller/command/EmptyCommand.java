package by.training.gym.controller.command;

import javax.servlet.http.HttpServletRequest;

/**
 * empty command class.
 * @author AlesyaHlushakova
 */
public class EmptyCommand implements CommandAction {

    /**
     * redirects to main page.
     * @param request HttpServletRequest object.
     * @return page.
     */
    public CurrentJsp execute(HttpServletRequest request) {
        return new CurrentJsp(CurrentJsp.MAIN_PAGE_PATH, false);
    }
}
