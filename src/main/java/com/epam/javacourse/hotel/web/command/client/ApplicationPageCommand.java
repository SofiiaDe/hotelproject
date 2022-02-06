package com.epam.javacourse.hotel.web.command.client;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApplicationPageCommand implements ICommand {

    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
        return new AddressCommandResult(Path.PAGE_SUBMIT_APPLICATION);
    }
}
