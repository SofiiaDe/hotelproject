package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.db.BookingDAO;
import com.epam.javacourse.hotel.db.UserDAO;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Invoice;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.serviceModels.BookingDetailed;
import com.epam.javacourse.hotel.model.serviceModels.UserBookingDetailed;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookingServiceImpl implements IBookingService{

    private final BookingDAO bookingDAO;
    private UserDAO userDao;

    public BookingServiceImpl(BookingDAO bookingDAO, UserDAO userDao) {
        this.bookingDAO = bookingDAO;
        this.userDao = userDao;
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

    @Override
    public Booking getBookingById(int id) throws DBException {
        return this.bookingDAO.getBookingById(id);
    }

    @Override
    public void deleteBookingById(int id) throws DBException {
        this.bookingDAO.deleteBookingById(id);
    }

    @Override
    public List<BookingDetailed> getAllDetailedBookings() throws DBException {
        List<Booking> allBookings = this.bookingDAO.findAllBookings();
        List<Integer> userIds = allBookings.stream().map(Booking::getUserId).distinct().collect(Collectors.toList());
        List<User> data = this.userDao.getUsersByIds(userIds);
        ArrayList<BookingDetailed> result = new ArrayList<>();

        for (Booking booking: allBookings) {
            var bookingUser = data.stream().filter(u -> u.getId() == booking.getUserId()).findFirst().get();
            result.add(
                    new BookingDetailed(booking.getId(),
                        bookingUser.getFirstName() + ' '+ bookingUser.getLastName(),
                        bookingUser.getEmail(),
                        booking.getCheckinDate(),
                        booking.getCheckoutDate(),
                        booking.getRoomId(),
                        false
                    ));
        }

        return result;
    }

    @Override
    public List<UserBookingDetailed> getUserDetailedBookings(int userID) throws DBException {
        List<Booking> allUserBookings = this.bookingDAO.findBookingsByUserId(userID);

        IRoomService roomService = AppContext.getInstance().getRoomService();

        ArrayList<UserBookingDetailed> result = new ArrayList<>();

        for (Booking booking : allUserBookings) {
            var room = roomService.getRoomById(booking.getRoomId());
            result.add(
                    new UserBookingDetailed(booking.getId(),
                            booking.getCheckinDate(),
                            booking.getCheckoutDate(),
                            room.getRoomTypeBySeats(),
                            room.getRoomClass(),
                            false
                    ));
        }
        return result;
    }

    /**
     * Finds invoices which were not paid by the due date and thus their status was changed to 'cancelled'.
     * Then removes bookings related to cancelled invoices.
     * @throws DBException
     */
    @Override
    public void cancelUnpaidBookings() throws DBException {

        IInvoiceService invoiceService = AppContext.getInstance().getInvoiceService();

        List<Invoice> unpaidInvoices = invoiceService.getInvoicesByStatus("cancelled");
        for(Invoice invoice : unpaidInvoices) {
            this.bookingDAO.deleteBookingById(invoice.getBookingId());
        }
    }
}
