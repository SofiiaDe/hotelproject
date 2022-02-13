package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.AppException;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.serviceModels.BookingDetailed;
import com.epam.javacourse.hotel.model.serviceModels.UserBookingDetailed;

import java.util.List;

public interface IBookingService {

    boolean create(Booking booking) throws DBException;

    List<Booking> getBookingsByUserId(int userId) throws DBException;

    List<Booking> getAllBookings() throws DBException;

    Booking getBookingById(int id) throws DBException;

    /**
     * Get all bookings with info about user
     * @param page
     * @param pageSize items per page
     * @return bookings with info about user
     * @throws AppException
     */
    List<BookingDetailed> getAllDetailedBookings(int page, int pageSize) throws AppException;

    void deleteBookingById(int id) throws DBException;

    List<UserBookingDetailed> getUserDetailedBookings(int userID) throws DBException;

    /**
     * Finds invoices which were not paid by the due date and thus their status was changed to 'cancelled'.
     * Then removes bookings related to cancelled invoices.
     * @throws DBException
     */
    void cancelUnpaidBookings() throws DBException;

    /**
     * Get number of bookings
     * @return number of bookings
     * @throws AppException
     */
    int getAllBookingsCount() throws AppException;
}
