package com.epam.javacourse.hotel.web.command.manager;

import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.interfaces.IUserService;
import com.epam.javacourse.hotel.utils.AppContext;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GetAllUsersCommand implements ICommand {

    IUserService userService = AppContext.getInstance().getUserService();

    @Override
    public ICommandResult execute(HttpServletRequest request,
                                  HttpServletResponse response) throws AppException {

        List<User> list = userService.getAllUsers();
        HttpSession session = request.getSession();
        session.setAttribute("usersList", list);

        return new AddressCommandResult(Path.PAGE_GET_USERS);

    }





}
