package com.epam.javacourse.hotel.web.command.client;

import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.model.*;
import com.epam.javacourse.hotel.model.service.interfaces.IApplicationService;
import com.epam.javacourse.hotel.model.service.interfaces.IBookingInvoiceService;
import com.epam.javacourse.hotel.model.service.interfaces.IConfirmRequestService;
import com.epam.javacourse.hotel.utils.AppContext;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import com.epam.javacourse.hotel.web.command.RedirectCommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

public class ConfirmRequestCommand implements ICommand {
    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        HttpSession session = request.getSession();

        IConfirmRequestService confirmRequestService = AppContext.getInstance().getConfirmRequestService();
        IApplicationService applicationService = AppContext.getInstance().getApplicationService();
        IBookingInvoiceService bookingInvoiceService = AppContext.getInstance().getBookingInvoiceService();

        User authorisedUser = (User) session.getAttribute("authorisedUser");

        int confirmRequestId = Integer.parseInt(request.getParameter("confirmRequest_id"));
        confirmRequestService.confirmRequestByClient(confirmRequestId);

        ConfirmationRequest confirmRequestToBook = confirmRequestService.getConfirmRequestById(confirmRequestId);
        Application applicationOfConfirmRequest = applicationService.getApplicationById(confirmRequestToBook.getApplicationId());

        // add new booking and new invoice to DB
        Booking newBooking = new Booking();
        newBooking.setUserId(authorisedUser.getId());
        newBooking.setCheckinDate(applicationOfConfirmRequest.getCheckinDate());
        newBooking.setCheckoutDate(applicationOfConfirmRequest.getCheckoutDate());
        newBooking.setRoomId(confirmRequestToBook.getRoomId());
        newBooking.setApplicationId(confirmRequestToBook.getApplicationId());

        Invoice newInvoice = new Invoice();
        newInvoice.setUserId(newBooking.getUserId());
        newInvoice.setAmount(bookingInvoiceService.getInvoiceAmount(newBooking));
        newInvoice.setBookingId(newBooking.getId());
        newInvoice.setInvoiceDate(LocalDate.now());
        newInvoice.setInvoiceStatus("new");

        bookingInvoiceService.createBookingAndInvoice(newBooking, newInvoice);

        return new RedirectCommandResult(Path.COMMAND_CLIENT_ACCOUNT);

    }
}
