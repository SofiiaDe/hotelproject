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
    public void create(Booking booking) throws DBException {

    }

    @Override
    public List<Booking> getBookingsByUserId(int userId) throws DBException {
        return this.bookingDAO.findBookingsByUserId(userId);
    }
}
