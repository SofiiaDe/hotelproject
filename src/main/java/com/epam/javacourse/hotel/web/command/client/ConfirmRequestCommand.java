package com.epam.javacourse.hotel.web.command.client;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.ConfirmationRequest;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ConfirmRequestCommand implements ICommand {
    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        HttpSession session = request.getSession();
        User authorisedUser = (User) session.getAttribute("authorisedUser");

//        List<ConfirmationRequest> userConfirmRequests = session.getAttribute("");
        boolean isConfirmed = false;

        if (isConfirmed) {

        }
        return null;
    }
}
