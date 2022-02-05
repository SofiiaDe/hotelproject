package com.epam.javacourse.hotel.web.command.manager;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.service.IApplicationService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowAllApplicationsPageCommand implements ICommand {

    IApplicationService applicationService = AppContext.getInstance().getApplicationService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        HttpSession session = request.getSession();
        List<Application> allApplications = applicationService.getAllApplications();
        session.setAttribute("allApplications", allApplications);

        return Path.COMMAND_SHOW_APPLICATIONS;
    }
}
