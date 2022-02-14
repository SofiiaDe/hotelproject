package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.AppException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Invoice;
import com.epam.javacourse.hotel.model.serviceModels.InvoiceDetailed;
import com.epam.javacourse.hotel.model.serviceModels.UserInvoiceDetailed;

import java.time.LocalDate;
import java.util.List;

public interface IInvoiceService {

    void createInvoice(Invoice invoice) throws AppException;

    List<Invoice> getAllInvoices() throws AppException;

    List<InvoiceDetailed> getAllDetailedInvoices() throws AppException;

    List<Invoice> getInvoicesByUserId(int userId) throws AppException;

    double getInvoiceAmount(Booking booking) throws AppException;

    List<UserInvoiceDetailed> getUserDetailedInvoices(int userID) throws AppException;

    LocalDate getInvoiceDueDate(Invoice invoice);

    /**
     * Creates new invoice once the new booking appeared.
     * @throws AppException
     */
    void generateInvoiceForBooking() throws AppException;

    /**
     * Updates invoice's status to 'cancelled' in case of not paying the invoice by the due date.
     * @throws AppException
     */
    void updateInvoiceStatusToCancelled() throws AppException;

    List<Invoice> getInvoicesByStatus(String status) throws AppException;

    /**
     * Updates invoice's status to 'paid' in case of successful payment transaction.
     * @param invoiceId
     * @throws AppException
     */
    void payInvoice(int invoiceId) throws AppException;

    Invoice getInvoiceById(int invoiceId) throws AppException;

}
