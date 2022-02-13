package com.epam.javacourse.hotel.web.command;

import com.epam.javacourse.hotel.Exception.AppException;
import com.epam.javacourse.hotel.Exception.DBException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ICommand {
    ICommandResult execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException;
}

