package com.epam.javacourse.hotel.web.command.client;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.service.IApplicationService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditApplicationCommand implements ICommand {
    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
        int id = Integer.parseInt(request.getParameter("id"));
        String room_seats = request.getParameter("room_seats").trim();
        String room_class = request.getParameter("room_class").trim();
        String address = Path.COMMAND_SHOW_APPLICATIONS;

        Application application = new Application();
        application.setId(id);
        application.setRoomTypeBySeats(room_seats);
        application.setRoomClass(room_class);

        IApplicationService applicationService = AppContext.getInstance().getApplicationService();
        applicationService.updateApplication(application);
        try {
            response.sendRedirect(address);
            address = Path.COMMAND_REDIRECT;
        } catch (IOException e) {
            address = Path.PAGE_ERROR;
        }
        return new AddressCommandResult(address);
    }
}
