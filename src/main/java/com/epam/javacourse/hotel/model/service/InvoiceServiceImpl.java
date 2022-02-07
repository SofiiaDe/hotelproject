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

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InvoiceServiceImpl implements IInvoiceService {

    private final InvoiceDAO invoiceDAO;
    private UserDAO userDAO;

    IRoomService roomService = AppContext.getInstance().getRoomService();

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
    public List<Invoice> findInvoicesByUserId(int userId) throws DBException {
        return this.invoiceDAO.findInvoicesByUserId(userId);
    }

    @Override
    public double getInvoiceAmount(Booking booking) throws DBException {
        LocalDate checkinDate = booking.getCheckinDate().toLocalDate();
        LocalDate checkoutDate = booking.getCheckoutDate().toLocalDate();
        Period period = Period.between(checkinDate, checkoutDate);
        Room room = roomService.getRoomById(booking.getRoomId());

        return room.getPrice() * Math.abs(period.getDays());
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
}
