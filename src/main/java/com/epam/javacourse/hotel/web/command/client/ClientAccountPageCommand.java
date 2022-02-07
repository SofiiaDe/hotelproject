package com.epam.javacourse.hotel.web.command.client;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.*;
import com.epam.javacourse.hotel.model.service.*;
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
    private final IConfirmRequestService confirmRequestService = AppContext.getInstance().getConfirmRequestService();
    private final IInvoiceService invoiceService = AppContext.getInstance().getInvoiceService();

    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        HttpSession session = request.getSession();
        String address = Path.PAGE_CLIENT_ACCOUNT;
        User authorisedUser = (User) session.getAttribute("authorisedUser");

        List<Application> userApplications = applicationService.getApplicationsByUserId(authorisedUser.getId());
        userApplications.sort(Comparator.comparing(Application::getCheckinDate).reversed());

        List<Booking> userBookings = bookingService.getBookingsByUserId(authorisedUser.getId());
        userBookings.sort(Comparator.comparing(Booking::getCheckinDate).reversed());

        List<ConfirmationRequest> userConfirmRequests = confirmRequestService
                .getConfirmRequestsByUserId(authorisedUser.getId());
        userConfirmRequests.sort(Comparator.comparing(ConfirmationRequest::getConfirmRequestDate).reversed());

        List<Invoice> userInvoices = invoiceService.getInvoicesByUserId(authorisedUser.getId());
        userInvoices.sort(Comparator.comparing(Invoice::getInvoiceDate));

        session.setAttribute("myApplications", userApplications);
        session.setAttribute("myBookings", userBookings);
        session.setAttribute("myConfirmRequests", userConfirmRequests);
        session.setAttribute("myInvoices", userInvoices);

        return new AddressCommandResult(address);
    }
}
