package com.epam.javacourse.hotel.web.command.common;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.service.IApplicationService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveApplicationCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        IApplicationService applicationService = AppContext.getInstance().getApplicationService();
        int applicationId = Integer.parseInt(request.getParameter("application_id"));
        applicationService.removeApplication(applicationId);
        return Path.COMMAND_SHOW_APPLICATIONS;
    }
}
