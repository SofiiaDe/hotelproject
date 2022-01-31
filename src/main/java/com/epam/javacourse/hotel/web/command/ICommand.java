package com.epam.javacourse.hotel.web.command;

import com.epam.javacourse.hotel.Exception.DBException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface ICommand {

    String execute(HttpServletRequest request, HttpServletResponse response)
            throws DBException;
}
