package com.epam.javacourse.hotel.model.service.impl;

import com.epam.javacourse.hotel.db.dao.BookingDAO;
import com.epam.javacourse.hotel.db.dao.InvoiceDAO;
import com.epam.javacourse.hotel.db.dao.RoomDAO;
import com.epam.javacourse.hotel.db.dao.UserDAO;
import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Invoice;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.interfaces.IBookingService;
import com.epam.javacourse.hotel.model.service.interfaces.IInvoiceService;
import com.epam.javacourse.hotel.model.service.interfaces.IRoomService;
import com.epam.javacourse.hotel.model.serviceModels.BookingDetailed;
import com.epam.javacourse.hotel.model.serviceModels.UserBookingDetailed;
import com.epam.javacourse.hotel.shared.models.BookingStatus;
import com.epam.javacourse.hotel.utils.AppContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookingServiceImpl implements IBookingService {

    private final BookingDAO bookingDAO;
    private final UserDAO userDao;
    private final InvoiceDAO invoiceDAO;
    private final RoomDAO roomDAO;

    public BookingServiceImpl(BookingDAO bookingDAO, UserDAO userDao, InvoiceDAO invoiceDAO, RoomDAO roomDAO) {
        this.bookingDAO = bookingDAO;
        this.userDao = userDao;
        this.invoiceDAO = invoiceDAO;
        this.roomDAO = roomDAO;
    }

    @Override
    public boolean create(Booking booking) throws AppException {
        try {
            return this.bookingDAO.createBooking(booking);
        } catch (DBException exception) {
            throw new AppException("Can't create new booking", exception);
        }
    }

    @Override
    public List<Booking> getBookingsByUserId(int userId) throws AppException {
        try {
            return this.bookingDAO.findBookingsByUserId(userId);
        } catch (DBException exception) {
            throw new AppException("Can't retrieve user's bookings by user's id", exception);
        }
    }

    @Override
    public List<Booking> getAllBookings() throws AppException {
        try {
            return this.bookingDAO.findAllBookings();
        } catch (DBException exception) {
            throw new AppException("Can't retrieve all bookings", exception);
        }
    }

    @Override
    public Booking getBookingById(int id) throws AppException {
        try {
            return this.bookingDAO.findBookingById(id);
        } catch (DBException exception) {
            throw new AppException("Can't retrieve booking by id", exception);
        }
    }

    @Override
    public void deleteBookingById(int id) throws AppException {
        try {
            this.bookingDAO.deleteBookingById(id);
        } catch (DBException exception) {
            throw new AppException("Can't remove booking by id", exception);
        }
    }

    @Override
    public List<BookingDetailed> getAllDetailedBookings(int page, int pageSize) throws AppException {
        return getAllDetailedBookings(page, pageSize, BookingStatus.NONE);
    }

    @Override
    public List<BookingDetailed> getAllDetailedBookings(int page, int pageSize, BookingStatus bookingStatus) throws AppException {
        List<Booking> allBookings;
        List<User> users;
        ArrayList<BookingDetailed> result;
        List<Invoice> invoices;

        try {
            allBookings = this.bookingDAO.findAllBookingsForPage(page, pageSize, bookingStatus);

            List<Integer> userIds = allBookings.stream().map(Booking::getUserId).distinct().collect(Collectors.toList());
            users = this.userDao.findUsersByIds(userIds);

            result = new ArrayList<>();
            invoices = this.invoiceDAO.findInvoices(allBookings.stream().map(Booking::getId).collect(Collectors.toList()));
        } catch (DBException exception) {
            throw new AppException("Can't retrieve list of all bookings to show in the manager's account", exception);
        }

        for (Booking booking : allBookings) {
            User bookingUser = users.stream().filter(u -> u.getId() == booking.getUserId()).findFirst().orElseThrow();
            result.add(
                    new BookingDetailed(booking.getId(),
                            bookingUser.getFirstName() + ' ' + bookingUser.getLastName(),
                            bookingUser.getEmail(),
                            booking.getCheckinDate(),
                            booking.getCheckoutDate(),
                            this.roomDAO.findRoomById(booking.getRoomId()).getRoomNumber(),
                            invoices.stream().filter(i -> i.getBookingId() == booking.getId()).findFirst().orElseThrow().getInvoiceStatus().equals("paid")
                    ));
        }
        return result;
    }

    @Override
    public List<UserBookingDetailed> getUserDetailedBookings(int userID, int page, int pageSize) throws AppException {

        List<Booking> allUserBookings;
        ArrayList<UserBookingDetailed> result;

        try {
            allUserBookings = this.bookingDAO.findBookingsByUserIdForPage(userID, page, pageSize);


            result = new ArrayList<>();

        } catch (DBException exception) {
            throw new AppException("Can't retrieve list of client's bookings to show in the client's account", exception);
        }

        IRoomService roomService = AppContext.getInstance().getRoomService();

        for (Booking booking : allUserBookings) {
            var room = roomService.getRoomById(booking.getRoomId());
            result.add(
                    new UserBookingDetailed(booking.getId(),
                            booking.getCheckinDate(),
                            booking.getCheckoutDate(),
                            room.getRoomTypeBySeats(),
                            room.getRoomClass(),
                            booking.isStatus()
                    ));
        }
        return result;

    }

    @Override
    public void cancelUnpaidBookings() throws AppException {

        try {
            IInvoiceService invoiceService = AppContext.getInstance().getInvoiceService();

            List<Invoice> unpaidInvoices = invoiceService.getInvoicesByStatus("cancelled");
            for (Invoice invoice : unpaidInvoices) {
                Booking booking = this.bookingDAO.findBookingById(invoice.getBookingId());
                booking.setStatus(false);
                this.bookingDAO.updateBookingStatus(booking);
            }
        } catch (DBException exception) {
            throw new AppException("Can't cancel unpaid bookings", exception);
        }
    }

    @Override
    public int getAllBookingsCount() throws AppException {
        return getAllBookingsCount(BookingStatus.NONE);
    }

    @Override
    public int getAllBookingsCount(BookingStatus bookingStatus) throws AppException {
        try {
            return this.bookingDAO.findAllBookingsCount(bookingStatus);
        } catch (DBException exception) {
            throw new AppException("Can't retrieve number of bookings", exception);
        }
    }

    @Override
    public int getUserBookingsCount(int userId) throws AppException {
        try {
            return this.bookingDAO.findUserBookingsCount(userId);
        } catch (DBException exception) {
            throw new AppException("Can't retrieve number of client's bookings", exception);
        }
    }
}
