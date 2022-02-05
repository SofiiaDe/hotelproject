package com.epam.javacourse.hotel.web.command.client;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.IUserService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Controller command for User profile page.
 */
public class    ClientAccountPageCommand implements ICommand {

    private final IUserService userService = AppContext.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        HttpSession session = request.getSession();
        String forward = Path.PAGE_CLIENT_ACCOUNT;
        User authorisedUser = (User) session.getAttribute("user");
//        userService.updateAuthorisedUserToSession(request, session, authorisedUser);

        return forward;
    }
}
