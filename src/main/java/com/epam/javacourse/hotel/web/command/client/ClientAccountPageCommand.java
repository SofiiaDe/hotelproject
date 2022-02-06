package com.epam.javacourse.hotel.web.command.client;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.IApplicationService;
import com.epam.javacourse.hotel.model.service.IBookingService;
import com.epam.javacourse.hotel.model.service.IUserService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.List;

/**
 * Controller command for User profile page.
 */
public class ClientAccountPageCommand implements ICommand {

    private final IUserService userService = AppContext.getInstance().getUserService();
    private final IApplicationService applicationService = AppContext.getInstance().getApplicationService();
    private final IBookingService bookingService = AppContext.getInstance().getBookingService();

    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        HttpSession session = request.getSession();
        String address = Path.PAGE_CLIENT_ACCOUNT;
        User authorisedUser = (User) session.getAttribute("authorisedUser");

        List<Application> userApplications = applicationService.getApplicationsByUserId(authorisedUser.getId());
        userApplications.sort(Comparator.comparing(Application::getCheckinDate).reversed());

        List<Booking> userBookings = bookingService.getBookingsByUserId(authorisedUser.getId());
        userBookings.sort(Comparator.comparing(Booking::getCheckinDate).reversed());

        session.setAttribute("myApplications", userApplications);
        session.setAttribute("myBookings", userBookings);

        return new AddressCommandResult(address);
    }
}
