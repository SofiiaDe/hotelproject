package com.epam.javacourse.hotel.web.command.common;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(LogoutCommand.class);

    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        logger.debug("Logout finished");

        return new AddressCommandResult(Path.PAGE_HOME);
}
}
