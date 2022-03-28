package by.training.gym.controller.command;

import by.training.gym.util.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;

import static by.training.gym.util.MessageManager.DEFAULT_LOCALE;

/**
 * command that changes language.
 * @author AlesyaHlushakova
 */
public class ChangeLanguageCommand implements CommandAction {

    private static final String RU_LANGUAGE = "ru";
    private static final String US_LANGUAGE = "en";
    private static final String BY_LANGUAGE = "by";

    private static final String RU_COUNTRY = "RU";
    private static final String US_COUNTRY = "US";
    private static final String BY_COUNTRY = "BY";

    /**
     * Implementation of commands to change language.
     *
     * @param request HttpServletRequest object.
     * @return jsp page.
     */
    @Override
    public CurrentJsp execute(HttpServletRequest request) {

        String localeValue = request.getParameter(LOCALE_PARAMETER);
        Locale locale;
        switch (localeValue) {
            case RU_LANGUAGE: {
                locale = new Locale(RU_LANGUAGE, RU_COUNTRY);
                break;
            }
            case US_LANGUAGE: {
                locale = new Locale(US_LANGUAGE, US_COUNTRY);
                break;
            }
            case BY_LANGUAGE: {
                locale = new Locale(BY_LANGUAGE, BY_COUNTRY);
                break;
            }
            default: {
                locale = DEFAULT_LOCALE;
                break;
            }
        }
        Config.set(request.getSession(), Config.FMT_LOCALE, locale);
        MessageManager.changeLocale(locale);

        return new CurrentJsp(CurrentJsp.MAIN_PAGE_PATH, true);
    }
}

