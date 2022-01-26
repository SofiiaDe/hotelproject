package com.epam.javacourse.hotel.web.command;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.IUserService;
import com.epam.javacourse.hotel.web.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Controller command for User profile page.
 */
public class UserProfileCommand implements ICommand{

    private final IUserService userService = AppContext.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        HttpSession session = request.getSession();
        String forward = Path.PAGE_USER_PROFILE;
        User authorisedUser = (User) session.getAttribute("user");
//        userService.updateAuthorisedUserToSession(request, session, authorisedUser);

        return forward;
    }
}
