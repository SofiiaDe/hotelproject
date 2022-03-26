package com.epam.javacourse.hotel.db.interfaces;

import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.shared.models.BookingStatus;

import java.util.List;

public interface IBookingDAO {

    boolean createBooking(Booking booking) throws DBException;

    /**
     * Get client's bookings
     *
     * @return Client's bookings
     * @throws DBException
     */
    List<Booking> findBookingsByUserId(int userId) throws DBException;

    /**
     * Get client's bookings limited by page and pageSize
     *
     * @param page     result's page. Set -1 to not limit by page/size
     * @param pageSize number of items to select
     * @return bookings from specified page and given page size
     * @throws DBException
     */
    List<Booking> findBookingsByUserIdForPage(int userId, int page, int pageSize) throws DBException;

    /**
     * Get all bookings
     *
     * @return All bookings
     * @throws DBException
     */
    List<Booking> findAllBookings() throws DBException;

    /**
     * Get all bookings limited by page and pageSize
     *
     * @param page     result's page. Set -1 to not limit by page/size
     * @param pageSize number of items to select
     * @param bookingStatus witch which booking status retrieve bookings
     * @return bookings from specified page and given page size
     * @throws DBException
     */
    List<Booking> findAllBookingsForPage(int page, int pageSize, BookingStatus bookingStatus) throws DBException;

    Booking findBookingById(int id) throws DBException;

    void deleteBookingById(int id) throws DBException;

    /**
     * Retrieve number of all bookings
     *
     * @return number of all bookings
     * @throws DBException
     * @param bookingStatus
     */
    int findAllBookingsCount(BookingStatus bookingStatus) throws DBException;

    /**
     * Retrieve number of client's bookings
     *
     * @return number of client's bookings
     * @throws DBException
     */
    int findUserBookingsCount(int userId) throws DBException;


    /**
     * Build a sql query to select bookings
     * @param baseQuery base select on top of which we add filters by status
     * @param page page of results. Set -1, to get all results
     * @param pageSize number of items. Set -1, to get all results
     * @param bookingStatus status of a bookings, with which we want to select
     * @return sql query
     */
    String createGetAllBookingsSql(String baseQuery, int page, int pageSize, BookingStatus bookingStatus);


    boolean updateBookingStatus(Booking booking) throws DBException;

}
