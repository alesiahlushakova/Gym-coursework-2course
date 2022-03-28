package by.training.gym.controller.command;

import static by.training.gym.util.MessageManager.NONE_MESSAGE_KEY;

/**
 * class describes page url.
 * @author Eugene Makarenko
 */
public class CurrentJsp {

    /**
     * Common pages.
     */
    public static final String LOGIN_PAGE_PATH = "/jsp/login.jsp";
    public static final String MAIN_PAGE_PATH = "/jsp/main.jsp";
    public static final String ERROR_PAGE_PATH = "/jsp/error.jsp";
    public static final String REGISTER_PAGE_PATH = "/jsp/register.jsp";



    private String pageUrl;
    private boolean isRedirect;
    private String messageKey;

    /**
     * constructor.
     * @param pageUrl    the page's url.
     * @param isRedirect boolean value of variable isRedirect;
     */
    public CurrentJsp(String pageUrl, boolean isRedirect) {
        this.pageUrl = pageUrl;
        this.isRedirect = isRedirect;
        this.messageKey = NONE_MESSAGE_KEY;
    }

    /**
     * empty constructor.
     */
    public CurrentJsp() {
    }

    /**
     * constructor.
     *
     * @param pageUrl    the page's url.
     * @param isRedirect boolean value of variable isRedirect;
     * @param messageKey the message key.
     */
    public CurrentJsp(String pageUrl, boolean isRedirect, String messageKey) {
        this.pageUrl = pageUrl;
        this.isRedirect = isRedirect;
        this.messageKey = messageKey;
    }

    /**
     * getter for page url.
     * @return the page's url.
     */
    public String getPageUrl() {
        return pageUrl;
    }

    /**
     * Setter for page url.
     * @param pageUrl the page's url.
     */
    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    /**
     * getter for isRedirect value.
     * @return boolean value of isRedirect variable.
     */
    public boolean isRedirect() {
        return isRedirect;
    }

    /**
     * setter for isRedirect value.
     * @param redirect the boolean value.
     */
    public void setRedirect(boolean redirect) {
        isRedirect = redirect;
    }

    /**
     * getter for message key.
     * @return the message key.
     */
    public String getMessageKey() {
        return messageKey;
    }

    /**
     * setter for the message key.
     * @param messageKey the message key.
     */
    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }
}