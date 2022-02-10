package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.db.InvoiceDAO;
import com.epam.javacourse.hotel.db.UserDAO;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Invoice;
import com.epam.javacourse.hotel.model.Room;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.serviceModels.InvoiceDetailed;
import com.epam.javacourse.hotel.model.serviceModels.UserInvoiceDetailed;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.RedirectCommandResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InvoiceServiceImpl implements IInvoiceService {

    private final InvoiceDAO invoiceDAO;
    private UserDAO userDAO;

    public InvoiceServiceImpl(InvoiceDAO invoiceDAO, UserDAO userDAO) {
        this.invoiceDAO = invoiceDAO;
        this.userDAO = userDAO;
    }


    @Override
    public void createInvoice(Invoice invoice) throws DBException {
        this.invoiceDAO.createInvoice(invoice);
    }

    @Override
    public List<Invoice> getAllInvoices() throws DBException {
        return this.invoiceDAO.findAllInvoices();
    }

    @Override
    public List<Invoice> getInvoicesByUserId(int userId) throws DBException {
        return this.invoiceDAO.findInvoicesByUserId(userId);
    }

    @Override
    public List<Invoice> getInvoicesByStatus(String status) throws DBException {
        return this.invoiceDAO.findInvoicesByStatus(status);
    }


    @Override
    public double getInvoiceAmount(Booking booking) throws DBException {
        IRoomService roomService = AppContext.getInstance().getRoomService();

        LocalDate checkinDate = booking.getCheckinDate().toLocalDate();
        LocalDate checkoutDate = booking.getCheckoutDate().toLocalDate();
        Period period = Period.between(checkinDate, checkoutDate);
        Room room = roomService.getRoomById(booking.getRoomId());

        return room.getPrice() * Math.abs(period.getDays());
    }

    @Override
    public LocalDateTime getInvoiceDueDate(Invoice invoice) {
        LocalDate invoiceDate = invoice.getInvoiceDate().toLocalDate();
        return invoiceDate.plusDays(2).atStartOfDay();

    }

    @Override
    public List<InvoiceDetailed> getAllDetailedInvoices() throws DBException {
        List<Invoice> allInvoices = this.invoiceDAO.findAllInvoices();
        List<Integer> userIds = allInvoices.stream()
                .map(Invoice::getUserId)
                .distinct()
                .collect(Collectors.toList());
        List<User> data = this.userDAO.getUsersByIds(userIds);
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
    }

    @Override
    public List<UserInvoiceDetailed> getUserDetailedInvoices(int userID) throws DBException {

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
    }

    @Override
    public void generateInvoiceForBooking() throws DBException {

        IBookingService bookingService = AppContext.getInstance().getBookingService();
//        IInvoiceService invoiceService = AppContext.getInstance().getInvoiceService();
        IRoomService roomService = AppContext.getInstance().getRoomService();

        List<Booking> allBookings = bookingService.getAllBookings();
        List<Invoice> allInvoices = this.invoiceDAO.findAllInvoices();
        int allBookingsSize = allBookings.size();

        if (allBookings.isEmpty()) {
            return; // ???? - do nothing
        }

        List<Integer> allBookingIds = allBookings.stream()
                .map(Booking::getId)
                .collect(Collectors.toList());

        List<Integer> invoicedBookingIds = allInvoices.stream()
                .map(Invoice::getBookingId)
                .collect(Collectors.toList());

        int invoicedBookingsSize = invoicedBookingIds.size();

        if (invoicedBookingsSize == allBookingsSize) {
            return; // ???? - do nothing
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
            newInvoice.setInvoiceDate(LocalDateTime.now());
            newInvoice.setInvoiceStatus("new");

            createInvoice(newInvoice);
        }

    }

    public void updateInvoiceStatusToCancelled() throws DBException {
        List<Invoice> allInvoices = this.invoiceDAO.findAllInvoices();
        for(Invoice invoice : allInvoices) {
            if(invoice.getInvoiceStatus().equals("new") &&
                    getInvoiceDueDate(invoice).isBefore(LocalDateTime.now())) {
                invoice.setInvoiceStatus("cancelled");
                this.invoiceDAO.updateInvoiceStatus(invoice);
            }
        }
    }

}


