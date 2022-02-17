package com.epam.javacourse.hotel.web.command.common;

import com.epam.javacourse.hotel.utils.AppContext;
import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.model.service.interfaces.IApplicationService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import com.epam.javacourse.hotel.web.command.RedirectCommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveApplicationCommand implements ICommand {

    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        IApplicationService applicationService = AppContext.getInstance().getApplicationService();
        int applicationId = Integer.parseInt(request.getParameter("application_id"));
        applicationService.removeApplication(applicationId);
        return new RedirectCommandResult(Path.COMMAND_MANAGER_ACCOUNT);
    }
}
