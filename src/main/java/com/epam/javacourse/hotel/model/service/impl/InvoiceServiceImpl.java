package com.epam.javacourse.hotel.model.service.impl;

import com.epam.javacourse.hotel.db.dao.InvoiceDAO;
import com.epam.javacourse.hotel.db.dao.UserDAO;
import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Invoice;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.interfaces.IBookingService;
import com.epam.javacourse.hotel.model.service.interfaces.IInvoiceService;
import com.epam.javacourse.hotel.model.service.interfaces.IRoomService;
import com.epam.javacourse.hotel.model.serviceModels.InvoiceDetailed;
import com.epam.javacourse.hotel.model.serviceModels.UserInvoiceDetailed;
import com.epam.javacourse.hotel.utils.AppContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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


            if (allUserInvoices.isEmpty()) {
                return Collections.emptyList();
            }

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


