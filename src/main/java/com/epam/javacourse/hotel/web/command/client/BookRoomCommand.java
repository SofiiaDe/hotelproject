package com.epam.javacourse.hotel.web.command.client;

import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Invoice;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.interfaces.IBookingInvoiceService;
import com.epam.javacourse.hotel.utils.AppContext;
import com.epam.javacourse.hotel.utils.Validator;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import com.epam.javacourse.hotel.web.command.RedirectCommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;


public class BookRoomCommand implements ICommand {

    IBookingInvoiceService bookingInvoiceService = AppContext.getInstance().getBookingInvoiceService();

    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        HttpSession session = request.getSession();
        User authorisedUser = (User) session.getAttribute("authorisedUser");

        String address = Path.PAGE_ERROR;

        String checkinDate = request.getParameter("checkin_date");
        String checkoutDate = request.getParameter("checkout_date");

        LocalDate checkin = Validator.dateParameterToLocalDate(checkinDate, request);
        LocalDate checkout = Validator.dateParameterToLocalDate(checkoutDate, request);

        if (!Validator.isCorrectDate(checkin, checkout, request)) {
            return new AddressCommandResult(address);
        }

        int roomId = Integer.parseInt(request.getParameter("room_id"));

        // add new booking and new invoice to DB
        Booking newBooking = new Booking();
        newBooking.setUserId(authorisedUser.getId());
        newBooking.setCheckinDate(checkin);
        newBooking.setCheckoutDate(checkout);
        newBooking.setRoomId(roomId);
        newBooking.setApplicationId(0);

        Invoice newInvoice = new Invoice();
        newInvoice.setUserId(newBooking.getUserId());
        newInvoice.setAmount(bookingInvoiceService.getInvoiceAmount(newBooking));
        newInvoice.setBookingId(newBooking.getId());
        newInvoice.setInvoiceDate(LocalDate.now());
        newInvoice.setInvoiceStatus("new");

        bookingInvoiceService.createBookingAndInvoice(newBooking, newInvoice);

        // "Thank you! The room was successfully booked.
        // Please check the invoice in your personal account."


        return new RedirectCommandResult(Path.COMMAND_CLIENT_ACCOUNT);

    }
}
