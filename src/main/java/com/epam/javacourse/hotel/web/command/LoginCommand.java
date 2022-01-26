package com.epam.javacourse.hotel.web.command;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.IUserService;
import com.epam.javacourse.hotel.web.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements ICommand {

    IUserService userService = AppContext.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // error handler
        String errorMessage;

        String address = Path.PAGE_LOGIN;

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            errorMessage = "Login and password cannot be empty";
            request.setAttribute("errorMessage", errorMessage);
            return address;
        }

//        User user = userService.findByEmail(email);
//        if (user.getEmail() == null || !BCrypt.checkpw(password, user.getPassword())) {
//            errorMessage = "Cannot find user with such email or password";
//            request.setAttribute("errorMessage", errorMessage);
//            return address;
//        } else {
//            Role userRole = Role.getRole(user);
//
//            if (userRole == Role.ADMIN) {
//                address = Path.COMMAND_SHOW_USERS;
//            }
//
//            if (userRole == Role.CLIENT) {
//                address = Path.COMMAND_ACCOUNT;
//            }
//
//            session.setAttribute("user", user);
//            session.setAttribute("userRole", userRole);
//        }
        return address;
    }
}
