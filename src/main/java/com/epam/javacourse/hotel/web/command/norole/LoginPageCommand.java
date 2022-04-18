package com.epam.javacourse.hotel.web.command.norole;

import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  Command for login page
 */
public class LoginPageCommand implements ICommand {
    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        return new AddressCommandResult(Path.PAGE_LOGIN);
    }
}
