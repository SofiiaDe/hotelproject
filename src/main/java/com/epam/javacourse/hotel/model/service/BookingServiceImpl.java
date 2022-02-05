package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.db.BookingDAO;
import com.epam.javacourse.hotel.model.Booking;

import java.util.List;

public class BookingServiceImpl implements IBookingService{

    private final BookingDAO bookingDAO;

    public BookingServiceImpl(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    @Override
    public boolean create(Booking booking) throws DBException {
        return this.bookingDAO.createBooking(booking);
    }

    @Override
    public List<Booking> getBookingsByUserId(int userId) throws DBException {
        return this.bookingDAO.findBookingsByUserId(userId);
    }

    @Override
    public List<Booking> getAllBookings() throws DBException {
        return this.bookingDAO.findAllBookings();
    }


}
