package com.epam.javacourse.hotel.web;

/**
 * Container for jsp-pages and commands necessary to forward / redirect between pages
 */
public class Path {

    private Path() {
    }

    public static final String PAGE_GET_USERS = "/WEB-INF/jsp/manager/getAllUsers.jsp";
    public static final String PAGE_ERROR = "/WEB-INF/jsp/errorPage.jsp";
    public static final String PAGE_LOGIN = "/WEB-INF/jsp/login.jsp";
    public static final String PAGE_HOME = "/homePage.jsp";
    public static final String PAGE_REGISTRATION = "/WEB-INF/jsp/registerPage.jsp";
    public static final String PAGE_CLIENT_ACCOUNT = "/WEB-INF/jsp/client/clientAccount.jsp";
    public static final String PAGE_MANAGER_ACCOUNT = "/WEB-INF/jsp/manager/managerAccount.jsp";
    public static final String PAGE_SUBMIT_APPLICATION = "/WEB-INF/jsp/client/application.jsp";
    public static final String PAGE_PAY_INVOICE = "/WEB-INF/jsp/client/paymentTransaction.jsp";
    public static final String PAGE_FREE_ROOMS = "/WEB-INF/jsp/client/freeRoomsToBook.jsp";

    public static final String COMMAND_LOGIN_PAGE = "controller?command=loginPage";
    public static final String COMMAND_MANAGER_ACCOUNT = "controller?command=managerAccount";
    public static final String COMMAND_CLIENT_ACCOUNT = "controller?command=clientAccount";
    public static final String COMMAND_HOME_PAGE = "controller?command=homePage";

    // i18n
    public static final String LOCALE_NAME_UK = "uk";
    public static final String LOCALE_NAME_EN = "en";
}
