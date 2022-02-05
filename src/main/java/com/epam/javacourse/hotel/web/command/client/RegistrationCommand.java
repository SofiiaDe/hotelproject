package com.epam.javacourse.hotel.web.command.client;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.Exception.HashPasswordException;
import com.epam.javacourse.hotel.Security;
import com.epam.javacourse.hotel.Validator;
import com.epam.javacourse.hotel.model.Role;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.IUserService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.ICommand;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class RegistrationCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);

    IUserService userService = AppContext.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        HttpSession session = request.getSession();

        List<User> registeredUsers = userService.findAllUsers();
        List<String> emails = new ArrayList<>(registeredUsers.size());

        for(User user : registeredUsers) {
            emails.add(user.getEmail());
        }

        String firstName = request.getParameter("firstName").trim();
        String lastName = request.getParameter("lastName").trim();

        String email = request.getParameter("email").trim();

        if(emails.contains(email)) {
            throw new DBException("Email already exists.");
        }
        if (Validator.validateEmail(email, 50) != null) {
            throw new DBException(Validator.validateEmail(email, 50));
        }

        String password = request.getParameter("password");

        if (Validator.validatePassword(password, 8, 20) != null) {
            throw new DBException(Validator.validatePassword(password, 8, 20));
        }

        String confirmPassword = request.getParameter("confirmPassword").trim();

        Validator.validatePassword(confirmPassword, 8, 20);

        if(Validator.validatePassword(confirmPassword, 8, 20) != null) {
            throw new DBException(Validator.validatePassword(confirmPassword, 8, 20));
        }
        if (!password.equals(confirmPassword)) {
            throw new DBException("Password does not match");
        }

        String country = request.getParameter("country").trim();
        String role = request.getParameter("role").trim();

        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);

        String hashedPassword = "";
        try {
            hashedPassword = Security.validatePasswordByHash(password);
        } catch (HashPasswordException ex) {
            logger.log(Level.ERROR, "New user hashing password error", ex);

        }
        newUser.setPassword(hashedPassword);

        newUser.setCountry(country);
        newUser.setRole(role);

        userService.create(newUser);

        session.setAttribute("newUser", newUser);

        return Path.COMMAND_LOGIN_PAGE; //succesfull registration ==>

    }

}