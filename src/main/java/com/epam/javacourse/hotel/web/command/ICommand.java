package com.epam.javacourse.hotel.web.command;

import com.epam.javacourse.hotel.Exception.DBException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ICommand {

    String execute(HttpServletRequest request, HttpServletResponse response) throws DBException;
}
