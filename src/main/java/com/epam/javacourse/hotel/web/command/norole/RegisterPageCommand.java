package com.epam.javacourse.hotel.web.command.norole;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RegisterPageCommand implements ICommand {

    private static final long serialVersionUID = -1L;

    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
        return new AddressCommandResult(Path.PAGE_REGISTRATION);
    }
}