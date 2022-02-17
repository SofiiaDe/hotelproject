package com.epam.javacourse.hotel.web.command.client;

import com.epam.javacourse.hotel.model.service.interfaces.IApplicationService;
import com.epam.javacourse.hotel.model.service.interfaces.IBookingService;
import com.epam.javacourse.hotel.model.service.interfaces.IConfirmRequestService;
import com.epam.javacourse.hotel.model.service.interfaces.IInvoiceService;
import com.epam.javacourse.hotel.utils.AppContext;
import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.model.*;
import com.epam.javacourse.hotel.model.serviceModels.*;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import com.epam.javacourse.hotel.web.command.helpers.Helpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Controller command for User profile page.
 */
public class ClientAccountPageCommand implements ICommand {

    private final IApplicationService applicationService = AppContext.getInstance().getApplicationService();
    private final IBookingService bookingService = AppContext.getInstance().getBookingService();
    private final IConfirmRequestService confirmRequestService = AppContext.getInstance().getConfirmRequestService();
    private final IInvoiceService invoiceService = AppContext.getInstance().getInvoiceService();

    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        HttpSession session = request.getSession();
        User authorisedUser = (User) session.getAttribute("authorisedUser");

//        String roomType = null;
        int page = Helpers.parsePage(request);
        int pageSize = AppContext.getInstance().getDefaultPageSize();

        List<UserApplicationDetailed> userApplications = applicationService.getUserDetailedApplications(authorisedUser.getId());
        userApplications.sort(Comparator.comparing(UserApplicationDetailed::getCheckinDate).reversed());

        int userBookingsCount = bookingService.getUserBookingsCount(authorisedUser.getId());
        int pageCount = (int) Math.ceil((float)userBookingsCount / pageSize);

        boolean toGetBookings = userBookingsCount > 0 && page <= pageCount;
        List<UserBookingDetailed> userBookings = toGetBookings ?
                bookingService.getUserDetailedBookings(authorisedUser.getId(), page, pageSize) : new ArrayList<>();
        userBookings.sort(Comparator.comparing(UserBookingDetailed::getCheckinDate).reversed());

        List<UserConfirmationRequestDetailed> userConfirmRequests = confirmRequestService
                .getUserDetailedConfirmRequests(authorisedUser.getId());
        userConfirmRequests.sort(Comparator.comparing(UserConfirmationRequestDetailed::getConfirmRequestDate).reversed());

        List<UserInvoiceDetailed> userInvoices = invoiceService.getUserDetailedInvoices(authorisedUser.getId());
        userInvoices.sort(Comparator.comparing(UserInvoiceDetailed::getInvoiceDate));

        session.setAttribute("myApplications", userApplications);
        session.setAttribute("myBookings", userBookings);
        session.setAttribute("myConfirmRequests", userConfirmRequests);
        session.setAttribute("myInvoices", userInvoices);
        request.setAttribute("page", page);
        request.setAttribute("pageCount", pageCount);

        return new AddressCommandResult(Path.PAGE_CLIENT_ACCOUNT);
    }
}
