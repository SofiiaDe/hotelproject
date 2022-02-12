package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Invoice;
import com.epam.javacourse.hotel.model.serviceModels.InvoiceDetailed;
import com.epam.javacourse.hotel.model.serviceModels.UserInvoiceDetailed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IInvoiceService {

    void createInvoice(Invoice invoice) throws DBException;

    List<Invoice> getAllInvoices() throws DBException;

    List<InvoiceDetailed> getAllDetailedInvoices() throws DBException;

    List<Invoice> getInvoicesByUserId(int userId) throws DBException;

    double getInvoiceAmount(Booking booking) throws DBException;

    List<UserInvoiceDetailed> getUserDetailedInvoices(int userID) throws DBException;

    LocalDate getInvoiceDueDate(Invoice invoice);

    void generateInvoiceForBooking() throws DBException;

    void updateInvoiceStatusToCancelled() throws DBException;

    List<Invoice> getInvoicesByStatus(String status) throws DBException;

    void payInvoice(int invoiceId) throws DBException;

    Invoice getInvoiceById(int invoiceId) throws DBException;

}
