package com.epam.javacourse.hotel.web.command.manager;

import com.epam.javacourse.hotel.utils.AppContext;
import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.model.service.interfaces.IApplicationService;
import com.epam.javacourse.hotel.model.service.interfaces.IBookingService;
import com.epam.javacourse.hotel.model.service.interfaces.IConfirmRequestService;
import com.epam.javacourse.hotel.model.service.interfaces.IInvoiceService;
import com.epam.javacourse.hotel.model.serviceModels.ApplicationDetailed;
import com.epam.javacourse.hotel.model.serviceModels.BookingDetailed;
import com.epam.javacourse.hotel.model.serviceModels.ConfirmationRequestDetailed;
import com.epam.javacourse.hotel.model.serviceModels.InvoiceDetailed;
import com.epam.javacourse.hotel.shared.models.BookingStatus;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import com.epam.javacourse.hotel.web.command.helpers.Helpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class ManagerAccountPageCommand implements ICommand {

    IApplicationService applicationService = AppContext.getInstance().getApplicationService();
    IBookingService bookingService = AppContext.getInstance().getBookingService();
    IInvoiceService invoiceService = AppContext.getInstance().getInvoiceService();
    IConfirmRequestService confirmRequestService = AppContext.getInstance().getConfirmRequestService();

    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        HttpSession session = request.getSession();
        int page = Helpers.parsePage(request);
        int pageSize = AppContext.getInstance().getDefaultPageSize();

        List<ApplicationDetailed> allApplications = applicationService.getAllDetailedApplications();
        session.setAttribute("allApplications", allApplications);

        BookingStatus bookingStatus = BookingStatus.fromString(request.getParameter("bookingStatus"));
        int allBookingsCount = bookingService.getAllBookingsCount(bookingStatus);
        int pageCount = (int) Math.ceil((float)allBookingsCount / pageSize);

        boolean toGetBookings = allBookingsCount > 0 && page <= pageCount;
        List<BookingDetailed> allBookings = toGetBookings ?
                bookingService.getAllDetailedBookings(page, pageSize, bookingStatus) : new ArrayList<>();
        session.setAttribute("allBookings", allBookings);

        List<InvoiceDetailed> allInvoices = invoiceService.getAllDetailedInvoices();
        session.setAttribute("allInvoices", allInvoices);

        List<ConfirmationRequestDetailed> allConfirmRequests = confirmRequestService.getAllDetailedConfirmRequests();
        session.setAttribute("allConfirmRequests", allConfirmRequests);

        request.setAttribute("page", page);
        request.setAttribute("pageCount", pageCount);

        return new AddressCommandResult(Path.PAGE_MANAGER_ACCOUNT);
    }
}
