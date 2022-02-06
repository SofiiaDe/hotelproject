package com.epam.javacourse.hotel.web.command.manager;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.service.IApplicationService;
import com.epam.javacourse.hotel.model.service.IBookingService;
import com.epam.javacourse.hotel.model.serviceModels.BookingDetailed;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ManagerAccountPageCommand implements ICommand {

    IApplicationService applicationService = AppContext.getInstance().getApplicationService();
    IBookingService bookingService = AppContext.getInstance().getBookingService();

    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        HttpSession session = request.getSession();
        List<Application> allApplications = applicationService.getAllApplications();
        session.setAttribute("allApplications", allApplications);

        List<BookingDetailed> allBookings = bookingService.getAllDetailedBookings();
        session.setAttribute("allBookings", allBookings);

        return new AddressCommandResult(Path.PAGE_MANAGER_ACCOUNT);
    }
}
