package com.epam.javacourse.hotel.web.command.client;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.AppException;
import com.epam.javacourse.hotel.model.service.IConfirmRequestService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import com.epam.javacourse.hotel.web.command.RedirectCommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfirmRequestCommand implements ICommand {
    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        IConfirmRequestService confirmRequestService = AppContext.getInstance().getConfirmRequestService();
        int confirmRequestId = Integer.parseInt(request.getParameter("confirmRequest_id"));
        confirmRequestService.confirmRequestByClient(confirmRequestId);

        return new RedirectCommandResult(Path.COMMAND_CLIENT_ACCOUNT);

    }
}
