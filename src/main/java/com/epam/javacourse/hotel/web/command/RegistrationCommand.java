package com.epam.javacourse.hotel.web.command;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.Validator;
import com.epam.javacourse.hotel.model.Role;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.IUserService;
import com.epam.javacourse.hotel.web.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationCommand implements ICommand {

    IUserService userService = AppContext.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        List<User> registeredUsers = userService.findAllUsers();
        List<String> emails = new ArrayList<>(registeredUsers.size());

        for(User user : registeredUsers) {
            emails.add(user.getEmail());
        }

        String firstName = request.getParameter("name").trim();
        String lastName = request.getParameter("name").trim();

        String email = request.getParameter("email").trim();
        if(emails.contains(email)) {
            throw new DBException("Email already exists.");
        }
        if (Validator.validateEmail(email, 50) != null) {
            throw new DBException(Validator.validateEmail(email, 50));
        }

        String password = request.getParameter("password").trim();
        if (Validator.validatePassword(password, 8, 20) != null) {
            throw new DBException(Validator.validatePassword(password, 8, 20));
        }

        String confirmPassword = request.getParameter("confirmPassword");

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
        newUser.setPassword(password);
        newUser.setCountry(country);
        newUser.setRole(Role.valueOf(role));
        userService.create(newUser);

        HttpSession session = request.getSession();
        session.setAttribute("newUser", newUser);

        String address = Path.COMMAND_PROFILE;
        try {
            response.sendRedirect(address);
            address = Path.COMMAND_REDIRECT;
        } catch (IOException e) {
            address = Path.PAGE_ERROR;
        }
        return address;

    }

}
