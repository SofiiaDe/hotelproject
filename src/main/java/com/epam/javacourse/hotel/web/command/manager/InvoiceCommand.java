package com.epam.javacourse.hotel.web.command.manager;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Invoice;
import com.epam.javacourse.hotel.model.Room;
import com.epam.javacourse.hotel.model.service.IBookingService;
import com.epam.javacourse.hotel.model.service.IInvoiceService;
import com.epam.javacourse.hotel.model.service.IRoomService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InvoiceCommand implements ICommand {

    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        IBookingService bookingService = AppContext.getInstance().getBookingService();
        IInvoiceService invoiceService = AppContext.getInstance().getInvoiceService();
        IRoomService roomService = AppContext.getInstance().getRoomService();

        List<Booking> allBookings = bookingService.getAllBookings();
        List<Invoice> allInvoices = invoiceService.getAllInvoices();
        int allBookingsSize = allBookings.size();

        String address = Path.MANAGER_ACCOUNT_PAGE;

        if (allBookings.isEmpty()) {
            return new AddressCommandResult(address); // ???? - do nothing
        }

        List<Integer> allBookingId = allBookings.stream()
                .map(Booking::getId)
                .collect(Collectors.toList());

        List<Integer> invoicedBookingId = allInvoices.stream()
                .map(Invoice::getBookingId)
                .collect(Collectors.toList());

        int invoicedBookingsSize = invoicedBookingId.size();

        if (invoicedBookingsSize == allBookingsSize) {
            return new AddressCommandResult(address); // ???? - do nothing
        }

        List<Integer> bookingIdToBeInvoiced = new ArrayList<>();
        if (invoicedBookingsSize < allBookingsSize) {
            bookingIdToBeInvoiced = allBookingId.stream()
                    .filter(id -> !invoicedBookingId.contains(id))
                    .collect(Collectors.toList());
        }

        for (Integer bookingId : bookingIdToBeInvoiced) {
            Booking booking = bookingService.getBookingById(bookingId);
            Invoice newInvoice = new Invoice();
            newInvoice.setUserId(booking.getUserId());
            newInvoice.setBookingId(bookingId);
            newInvoice.setInvoiceDate(LocalDateTime.now());
            newInvoice.setInvoiceStatus("new");
            newInvoice.setAmount(invoiceService.getInvoiceAmount(booking));

            invoiceService.createInvoice(newInvoice);
        }

        return new AddressCommandResult(address);
    }
}
