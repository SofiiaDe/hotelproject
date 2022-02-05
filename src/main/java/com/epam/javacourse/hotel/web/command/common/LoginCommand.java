package com.epam.javacourse.hotel.web.command.common;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Role;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.IUserService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.ICommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(LoginCommand.class);

    IUserService userService = AppContext.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws DBException {


        HttpSession session = request.getSession();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // error handler
        String errorMessage;

        String address = Path.PAGE_ERROR;

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            errorMessage = "Login and password cannot be empty";
            request.setAttribute("errorMessage", errorMessage);
            return address;
        }

        User user = userService.findByEmail(email);

        String hashPassword;
//        try {
//            hashPassword = Security.validatePasswordByHash(password);
//            if (user.getEmail() == null || !hashPassword.equals(user.getPassword())) {
            if (user.getEmail() == null || user.getPassword() == null || !password.equals(user.getPassword())) {
                errorMessage = "Cannot find user with such email or password";
                request.setAttribute("errorMessage", errorMessage);
                return address;
            } else {
                Role role = Role.getRoleByUser(user);

                if (role == Role.MANAGER) {
                    address = Path.PAGE_MANAGER_ACCOUNT;
                }

                if (role == Role.CLIENT) {
                    address = Path.PAGE_CLIENT_ACCOUNT;
                }

                session.setAttribute("authorisedUser", user);
                session.setAttribute("userRole", role);
                session.setAttribute("userEmail", email);
            }
//        }
//        catch (HashPasswordException e) {
//            logger.log(Level.ERROR, "User hashing password error", e);
//        }


        return address;
    }
}
