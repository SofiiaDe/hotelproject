package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.serviceModels.BookingDetailed;

import java.util.List;

public interface IBookingService {

    boolean create(Booking booking) throws DBException;

    List<Booking> getBookingsByUserId(int userId) throws DBException;

    List<Booking> getAllBookings() throws DBException;

    Booking getBookingById(int id) throws DBException;

    List<BookingDetailed> getAllDetailedBookings() throws DBException;
}
