package com.epam.javacourse.hotel.web.command;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.Security;
import com.epam.javacourse.hotel.db.UserDAO;
import com.epam.javacourse.hotel.model.Role;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.IUserService;
import com.epam.javacourse.hotel.web.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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

//        try {
//            if (user.getEmail() == null || !Security.validatePasswordByHash(password, user.getPassword())) {
            if (user.getEmail() == null || user.getPassword() == null) {
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
            }
//        }
//        catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }


        return address;
    }
}
