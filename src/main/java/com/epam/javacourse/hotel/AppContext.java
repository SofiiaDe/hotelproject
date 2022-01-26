package com.epam.javacourse.hotel;

import com.epam.javacourse.hotel.db.UserDAO;
import com.epam.javacourse.hotel.model.service.IUserService;
import com.epam.javacourse.hotel.model.service.UserServiceImpl;

/**
 * Class creates all necessary DAOs and services at the app start.
 */
public class AppContext {

    private static final AppContext appContext = new AppContext();

    //DAOs
    private final UserDAO userDao = new UserDAO();
//    private final AccountDAO accountDAO = new AccountDAO();

    // services
//    private final IAccountService accountService = new AccountServiceImpl(accountDAO);
//    private final IUserService userService = new UserServiceImpl(userDao, accountService);
    private final IUserService userService = new UserServiceImpl(userDao);

    public static AppContext getInstance() {
        return appContext;
    }

    public IUserService getUserService() {
        return userService;
    }

//    public IAccountService getAccountService() {
//        return accountService;
//    }
}
