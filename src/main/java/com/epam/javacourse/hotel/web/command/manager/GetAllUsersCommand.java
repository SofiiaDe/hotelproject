package com.epam.javacourse.hotel.web.command.manager;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;

import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.IUserService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GetAllUsersCommand implements ICommand {

    IUserService userService = AppContext.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws DBException {

        List<User> list = userService.findAllUsers();
        HttpSession session = request.getSession();
        session.setAttribute("usersList", list);

        return Path.PAGE_GET_USERS;

    }





}
