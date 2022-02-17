package com.epam.javacourse.hotel.model.service.impl;

import com.epam.javacourse.hotel.db.dao.BookingInvoiceDAO;
import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Invoice;
import com.epam.javacourse.hotel.model.Room;
import com.epam.javacourse.hotel.model.service.interfaces.IBookingInvoiceService;
import com.epam.javacourse.hotel.model.service.interfaces.IRoomService;
import com.epam.javacourse.hotel.utils.AppContext;

import java.time.LocalDate;
import java.time.Period;

public class BookingInvoiceServiceImpl implements IBookingInvoiceService {

    private final BookingInvoiceDAO bookingInvoiceDAO;

    public BookingInvoiceServiceImpl(BookingInvoiceDAO bookingInvoiceDAO) {
        this.bookingInvoiceDAO = bookingInvoiceDAO;
    }

    @Override
    public boolean createBookingAndInvoice(Booking booking, Invoice invoice) throws AppException {
        try {
            return this.bookingInvoiceDAO.createBookingAndInvoice(booking, invoice);
        } catch (DBException exception) {
            throw new AppException("Can't create new booking and invoice", exception);
        }
    }

    @Override
    public double getInvoiceAmount(Booking booking) throws AppException {
        try {
            IRoomService roomService = AppContext.getInstance().getRoomService();

            LocalDate checkinDate = booking.getCheckinDate();
            LocalDate checkoutDate = booking.getCheckoutDate();
            Period period = Period.between(checkinDate, checkoutDate);
            Room room = roomService.getRoomById(booking.getRoomId());

            return room.getPrice() * Math.abs(period.getDays());
        } catch (DBException exception) {
            throw new AppException("Can't calculate invoice amount", exception);
        }
    }

}
