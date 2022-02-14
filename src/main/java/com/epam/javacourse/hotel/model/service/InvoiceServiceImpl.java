package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.AppException;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.db.InvoiceDAO;
import com.epam.javacourse.hotel.db.UserDAO;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Invoice;
import com.epam.javacourse.hotel.model.Room;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.serviceModels.InvoiceDetailed;
import com.epam.javacourse.hotel.model.serviceModels.UserInvoiceDetailed;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InvoiceServiceImpl implements IInvoiceService {

    private final InvoiceDAO invoiceDAO;
    private final UserDAO userDAO;

    public InvoiceServiceImpl(InvoiceDAO invoiceDAO, UserDAO userDAO) {
        this.invoiceDAO = invoiceDAO;
        this.userDAO = userDAO;
    }


    @Override
    public void createInvoice(Invoice invoice) throws AppException {
        try {
            this.invoiceDAO.createInvoice(invoice);
        } catch (DBException exception) {
            throw new AppException("Can't create invoice", exception);
        }
    }

    @Override
    public List<Invoice> getAllInvoices() throws AppException {
        try {
            return this.invoiceDAO.findAllInvoices();
        } catch (DBException exception) {
            throw new AppException("Can't retrieve all invoices", exception);
        }
    }

    @Override
    public List<Invoice> getInvoicesByUserId(int userId) throws AppException {
        try {
            return this.invoiceDAO.findInvoicesByUserId(userId);
        } catch (DBException exception) {
            throw new AppException("Can't retrieve invoices by client's id", exception);
        }
    }

    @Override
    public List<Invoice> getInvoicesByStatus(String status) throws AppException {
        try {
            return this.invoiceDAO.findInvoicesByStatus(status);
        } catch (DBException exception) {
            throw new AppException("Can't retrieve invoices by status", exception);
        }
    }

    @Override
    public Invoice getInvoiceById(int invoiceId) throws AppException {
        try {
            return this.invoiceDAO.findInvoiceById(invoiceId);
        } catch (DBException exception) {
            throw new AppException("Can't retrieve invoice by id", exception);
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

    @Override
    public LocalDate getInvoiceDueDate(Invoice invoice) {
        LocalDate invoiceDate = invoice.getInvoiceDate();
        return invoiceDate.plusDays(2);

    }

    @Override
    public List<InvoiceDetailed> getAllDetailedInvoices() throws AppException {
        try {
            List<Invoice> allInvoices = this.invoiceDAO.findAllInvoices();

            List<Integer> userIds = allInvoices.stream()
                    .map(Invoice::getUserId)
                    .distinct()
                    .collect(Collectors.toList());
            List<User> data = this.userDAO.findUsersByIds(userIds);
            ArrayList<InvoiceDetailed> result = new ArrayList<>();

            for (Invoice invoice : allInvoices) {
                var bookingUser = data.stream().filter(u -> u.getId() == invoice.getUserId()).findFirst().get();
                result.add(
                        new InvoiceDetailed(invoice.getId(),
                                bookingUser.getFirstName() + ' ' + bookingUser.getLastName(),
                                bookingUser.getEmail(),
                                invoice.getAmount(),
                                invoice.getBookingId(),
                                invoice.getInvoiceDate(),
                                invoice.getInvoiceStatus()
                        ));
            }
            return result;
        } catch (DBException exception) {
            throw new AppException("Can't retrieve all invoices to show in the manager's account", exception);
        }
    }

    @Override
    public List<UserInvoiceDetailed> getUserDetailedInvoices(int userID) throws AppException {

        try {
            List<Invoice> allUserInvoices = this.invoiceDAO.findInvoicesByUserId(userID);


            IBookingService bookingService = AppContext.getInstance().getBookingService();
            List<Booking> userBookings = bookingService.getBookingsByUserId(userID);

            IRoomService roomService = AppContext.getInstance().getRoomService();

            ArrayList<UserInvoiceDetailed> result = new ArrayList<>();

            for (Invoice invoice : allUserInvoices) {
                var booking = userBookings.stream()
                        .filter(b -> b.getId() == invoice.getBookingId())
                        .findFirst()
                        .get();
                var room = roomService.getRoomById(booking.getRoomId());
                result.add(
                        new UserInvoiceDetailed(invoice.getId(),
                                invoice.getInvoiceDate(),
                                getInvoiceDueDate(invoice),
                                invoice.getAmount(),
                                invoice.getBookingId(),
                                room.getPrice(),
                                booking.getCheckinDate(),
                                booking.getCheckoutDate(),
                                invoice.getInvoiceStatus()
                        ));
            }
            return result;
        } catch (DBException exception) {
            throw new AppException("Can't retrieve client's invoices to show in the client's account", exception);
        }
    }

    @Override
    public void generateInvoiceForBooking() throws AppException {

        try {
            IBookingService bookingService = AppContext.getInstance().getBookingService();


            List<Booking> allBookings = bookingService.getAllBookings();
            List<Invoice> allInvoices = this.invoiceDAO.findAllInvoices();
            int allBookingsSize = allBookings.size();

            if (allBookings.isEmpty()) {
                return; // - do nothing
            }

            List<Integer> allBookingIds = allBookings.stream()
                    .map(Booking::getId)
                    .collect(Collectors.toList());

            List<Integer> invoicedBookingIds = allInvoices.stream()
                    .map(Invoice::getBookingId)
                    .collect(Collectors.toList());

            int invoicedBookingsSize = invoicedBookingIds.size();

            if (invoicedBookingsSize == allBookingsSize) {
                return; // - do nothing
            }

            List<Integer> bookingIdsToBeInvoiced = new ArrayList<>();
            if (invoicedBookingsSize < allBookingsSize) {
                bookingIdsToBeInvoiced = allBookingIds.stream()
                        .filter(id -> !invoicedBookingIds.contains(id))
                        .collect(Collectors.toList());
            }

            for (Integer bookingId : bookingIdsToBeInvoiced) {
                Booking booking = bookingService.getBookingById(bookingId);
                Invoice newInvoice = new Invoice();
                newInvoice.setUserId(booking.getUserId());
                newInvoice.setAmount(getInvoiceAmount(booking));
                newInvoice.setBookingId(bookingId);
                newInvoice.setInvoiceDate(LocalDate.now());
                newInvoice.setInvoiceStatus("new");

                createInvoice(newInvoice);
            }
        } catch (DBException exception) {
            throw new AppException("Can't generate invoice for booking", exception);
        }
    }


    @Override
    public void updateInvoiceStatusToCancelled() throws AppException {
        try {
            List<Invoice> allInvoices = this.invoiceDAO.findAllInvoices();

            for (Invoice invoice : allInvoices) {
                if (invoice.getInvoiceStatus().equals("new") &&
                        getInvoiceDueDate(invoice).isBefore(LocalDate.now())) {
                    invoice.setInvoiceStatus("cancelled");
                    this.invoiceDAO.updateInvoiceStatus(invoice);
                }
            }
        } catch (DBException exception) {
            throw new AppException("Can't update invoice's status", exception);
        }
    }

    @Override
    public void payInvoice(int invoiceId) throws AppException {
        try {
            Invoice invoiceToBePaid = this.invoiceDAO.findInvoiceById(invoiceId);
            invoiceToBePaid.setInvoiceStatus("paid");
            this.invoiceDAO.updateInvoiceStatus(invoiceToBePaid);
        } catch (DBException exception) {
            throw new AppException("Can't pay invoice", exception);
        }
    }


}


