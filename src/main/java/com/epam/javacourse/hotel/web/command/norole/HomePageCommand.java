package com.epam.javacourse.hotel.web.command.norole;

import com.epam.javacourse.hotel.Exception.AppException;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomePageCommand implements ICommand {
    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        return new AddressCommandResult(Path.PAGE_HOME);
    }
}
