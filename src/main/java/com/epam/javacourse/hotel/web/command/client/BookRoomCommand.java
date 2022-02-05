package com.epam.javacourse.hotel.web.command.client;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Room;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.IBookingService;
import com.epam.javacourse.hotel.model.service.IRoomService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.ICommand;
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

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        IRoomService roomService = AppContext.getInstance().getRoomService();
        IBookingService bookingService = AppContext.getInstance().getBookingService();

        HttpSession session = request.getSession();
        User authorisedUser = (User) session.getAttribute("authorisedUser");

        // updating room's status to "booked"
        int id = Integer.parseInt(request.getParameter("id"));
        double price = Double.parseDouble(request.getParameter("price"));
        int roomNumber = Integer.parseInt(request.getParameter("room_number"));
        String roomSeats = request.getParameter("room_seats");
        String roomClass = request.getParameter("room_class");

        Room bookedRoom = new Room();
        bookedRoom.setId(id);
        bookedRoom.setPrice(price);
        bookedRoom.setRoomNumber(roomNumber);
        bookedRoom.setRoomTypeBySeats(roomSeats);
        bookedRoom.setRoomClass(roomClass);
        bookedRoom.setRoomStatus("booked");
        roomService.updateRoom(bookedRoom);

        // adding new booking to DB
        Booking newBooking = new Booking();

        newBooking.setUser(authorisedUser);

        LocalDateTime checkInDate = null;
        LocalDateTime checkOutDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate ldCheckin = LocalDate.parse(request.getParameter("checkin_date"), formatter);
            checkInDate = LocalDateTime.of(ldCheckin, LocalDateTime.now().toLocalTime());

            LocalDate ldCheckout = LocalDate.parse(request.getParameter("checkout_date"), formatter);
            checkOutDate = LocalDateTime.of(ldCheckout, LocalDateTime.now().toLocalTime());

        } catch (DateTimeParseException e) {
            logger.error("Cannot get date type");
        }
        newBooking.setCheckInDate(checkInDate);
        newBooking.setCheckOutDate(checkOutDate);

        newBooking.setRoom(bookedRoom);
        newBooking.setApplication(null);
        bookingService.create(newBooking);

        // "Thank you! The room was successfully booked.
        // Please check the invoice in your personal account."

        return Path.PAGE_CLIENT_ACCOUNT;
    }
}
