package com.epam.javacourse.hotel.web.command;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.web.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RegisterPageCommand implements ICommand{

    private static final long serialVersionUID = -1L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
        return Path.PAGE_REGISTRATION;
    }
}
