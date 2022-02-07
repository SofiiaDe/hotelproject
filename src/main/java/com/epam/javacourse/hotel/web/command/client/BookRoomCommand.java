package com.epam.javacourse.hotel.web.command.client;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Room;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.IBookingService;
import com.epam.javacourse.hotel.model.service.IRoomService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class BookRoomCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(BookRoomCommand.class);

    IRoomService roomService = AppContext.getInstance().getRoomService();
    IBookingService bookingService = AppContext.getInstance().getBookingService();

    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        HttpSession session = request.getSession();
        User authorisedUser = (User) session.getAttribute("authorisedUser");

        // get and validate check-in and check-out dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String checkinDate = request.getParameter("checkin_date");
        String checkoutDate = request.getParameter("checkout_date");

        LocalDate ldCheckin = null;
        LocalDate ldCheckout = null;
        try {
            ldCheckin = LocalDate.parse(checkinDate, formatter);
            ldCheckout = LocalDate.parse(checkoutDate, formatter);
        } catch (DateTimeParseException e) {
            logger.error("Cannot get date type", e);
        }

        String address = Path.PAGE_ERROR;
        String errorMessage;

        if (checkinDate == null || checkoutDate == null || checkinDate.isEmpty() || checkoutDate.isEmpty()
        || ldCheckin == null || ldCheckout == null) {
            errorMessage = "Please choose check-in and check-out dates.";
            request.setAttribute("errorMessage", errorMessage);
            return new AddressCommandResult(address);
        }

        LocalDateTime checkinDateLocal = LocalDateTime.of(ldCheckin, LocalDateTime.now().toLocalTime());
        LocalDateTime checkoutDateLocal = LocalDateTime.of(ldCheckout, LocalDateTime.now().toLocalTime());

        if(checkinDateLocal.isAfter(checkoutDateLocal)) {
            errorMessage = "Check-out date cannot be later than check-in date.\n " +
                    "Please enter correct dates.";
            request.setAttribute("errorMessage", errorMessage);
            return new AddressCommandResult(address);
        }

        // update room's status to "booked"
        int roomId = Integer.parseInt(request.getParameter("room_id"));

        Room bookedRoom = roomService.getRoomById(roomId);
        bookedRoom.setRoomStatus("booked");

        roomService.updateRoom(bookedRoom);

        // add new booking to DB
        Booking newBooking = new Booking();

        newBooking.setUserId(authorisedUser.getId());
        newBooking.setCheckinDate(checkinDateLocal);
        newBooking.setCheckoutDate(checkoutDateLocal);

        newBooking.setRoomId(roomId);
        newBooking.setApplicationId(0);
        bookingService.create(newBooking);

        // "Thank you! The room was successfully booked.
        // Please check the invoice in your personal account."

        return new AddressCommandResult(Path.PAGE_CLIENT_ACCOUNT);
    }
}
