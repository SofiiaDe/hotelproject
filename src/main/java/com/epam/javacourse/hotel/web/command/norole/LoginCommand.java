package com.epam.javacourse.hotel.web.command.norole;

import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.exception.HashPasswordException;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.interfaces.IUserService;
import com.epam.javacourse.hotel.utils.AppContext;
import com.epam.javacourse.hotel.utils.Security;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import com.epam.javacourse.hotel.web.command.RedirectCommandResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Command to log in user
 */
public class LoginCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(LoginCommand.class);

    IUserService userService = AppContext.getInstance().getUserService();

    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        
        HttpSession session = request.getSession();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // error handler
        String errorMessage;

        String address = Path.PAGE_ERROR;
        

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            errorMessage = "Login and password cannot be empty";
            request.setAttribute("errorMessage", errorMessage);
            return new AddressCommandResult(address);
        }

        User user = userService.getUserByEmail(email);

        String hashPassword = null;
        try {
            hashPassword = Security.validatePasswordByHash(password);
        } catch (HashPasswordException e) {
            logger.error("User hashing password error", e);
        }
        if (user.getEmail() == null || user.getPassword() == null || !hashPassword.equals(user.getPassword())) {
            errorMessage = "Cannot find user with such email or password";
            request.setAttribute("errorMessage", errorMessage);
            return new AddressCommandResult(address);
        }
        String role = user.getRole();
        session.setAttribute("authorisedUser", user);
        session.setAttribute("userRole", role);
        session.setAttribute("userEmail", email);

        if ("manager".equalsIgnoreCase(role)) {
            return new RedirectCommandResult(Path.COMMAND_MANAGER_ACCOUNT);
        }else if ("client".equalsIgnoreCase(role)) {
            return new RedirectCommandResult(Path.COMMAND_CLIENT_ACCOUNT);
        }

        return new AddressCommandResult(address);
    }
}
