package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.AppException;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.db.BookingDAO;
import com.epam.javacourse.hotel.db.InvoiceDAO;
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
    private final UserDAO userDao;
    private final InvoiceDAO invoiceDAO;

    public BookingServiceImpl(BookingDAO bookingDAO, UserDAO userDao, InvoiceDAO invoiceDAO) {
        this.bookingDAO = bookingDAO;
        this.userDao = userDao;
        this.invoiceDAO = invoiceDAO;
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
            return this.bookingDAO.getAllBookings();
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
        List<Booking> allBookings;
        List<User> users;
        ArrayList<BookingDetailed> result;
        List<Invoice> invoices;

        try {
            allBookings = this.bookingDAO.getAllBookings(page, pageSize);

            List<Integer> userIds = allBookings.stream().map(Booking::getUserId).distinct().collect(Collectors.toList());
            users = this.userDao.findUsersByIds(userIds);

            result = new ArrayList<>();
            invoices = this.invoiceDAO.findInvoices(allBookings.stream().map(Booking::getId).collect(Collectors.toList()));
        } catch (DBException exception){
            throw new AppException("Can't retrieve list of all bookings to show in the manager's account", exception);
        }

        for (Booking booking: allBookings) {
            var bookingUser = users.stream().filter(u -> u.getId() == booking.getUserId()).findFirst().get();
            result.add(
                    new BookingDetailed(booking.getId(),
                        bookingUser.getFirstName() + ' '+ bookingUser.getLastName(),
                        bookingUser.getEmail(),
                        booking.getCheckinDate(),
                        booking.getCheckoutDate(),
                        booking.getRoomId(),
                            invoices.stream().filter(i -> i.getBookingId() == booking.getId()).findFirst().get().getInvoiceStatus() == "paid"
                    ));
        }
        return result;
    }

    @Override
    public List<UserBookingDetailed> getUserDetailedBookings(int userID) throws AppException {

        try {
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
        } catch (DBException exception) {
            throw new AppException("Can't retrieve list of client's bookings to show in the client's account", exception);
        }
    }

    @Override
    public void cancelUnpaidBookings() throws AppException {

        try {
            IInvoiceService invoiceService = AppContext.getInstance().getInvoiceService();

            List<Invoice> unpaidInvoices = invoiceService.getInvoicesByStatus("cancelled");
            for (Invoice invoice : unpaidInvoices) {
                this.bookingDAO.deleteBookingById(invoice.getBookingId());
            }
        } catch (DBException exception) {
            throw new AppException("Can't cancel unpaid bookings", exception);
        }
    }

    @Override
    public int getAllBookingsCount() throws AppException {
        try{
            return this.bookingDAO.getAllBookingsCount();
        }catch(DBException exception){
            throw new AppException("Can't retrieve number of bookings", exception);
        }
    }
}
