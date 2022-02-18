package com.epam.javacourse.hotel.web.command;

import com.epam.javacourse.hotel.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Main interface for Command Pattern implementation
 */
public interface ICommand {
    ICommandResult execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException;
}

