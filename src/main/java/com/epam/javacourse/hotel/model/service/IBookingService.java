package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.AppException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Invoice;
import com.epam.javacourse.hotel.model.serviceModels.BookingDetailed;
import com.epam.javacourse.hotel.model.serviceModels.UserBookingDetailed;
import com.epam.javacourse.hotel.shared.models.BookingStatus;

import java.util.List;

public interface IBookingService {

    boolean create(Booking booking) throws AppException;

    List<Booking> getBookingsByUserId(int userId) throws AppException;

    List<Booking> getAllBookings() throws AppException;

    Booking getBookingById(int id) throws AppException;

    /**
     * Get all bookings with info about user
     * @param page
     * @param pageSize items per page
     * @return bookings with info about user
     * @throws AppException
     */
    List<BookingDetailed> getAllDetailedBookings(int page, int pageSize) throws AppException;

    /**
     * Get all bookings with info about user
     * @param page
     * @param pageSize items per page
     * @param bookingStatus sets filter for bookings
     * @return bookings with info about user
     * @throws AppException
     */
    List<BookingDetailed> getAllDetailedBookings(int page, int pageSize, BookingStatus bookingStatus) throws AppException;

    void deleteBookingById(int id) throws AppException;

    List<UserBookingDetailed> getUserDetailedBookings(int userID, int page, int pageSize) throws AppException;

    /**
     * Finds invoices which were not paid by the due date and thus their status was changed to 'cancelled'.
     * Then removes bookings related to cancelled invoices.
     * @throws AppException
     */
    void cancelUnpaidBookings() throws AppException;

    /**
     * Get number of bookings
     * @return number of bookings
     * @throws AppException
     */
    int getAllBookingsCount() throws AppException;

    /**
     * Get number of bookings that has specified booking status
     * @param bookingStatus with which status bookings should be counted
     * @return number of bookings
     * @throws AppException
     */
    int getAllBookingsCount(BookingStatus bookingStatus) throws AppException;

    /**
     * Get number of client's bookings
     * @return number of bookings
     * @throws AppException
     */
    int getUserBookingsCount(int userId) throws AppException;
}
