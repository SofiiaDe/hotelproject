package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Invoice;
import com.epam.javacourse.hotel.model.serviceModels.InvoiceDetailed;

import java.util.List;

public interface IInvoiceService {

    void createInvoice(Invoice invoice) throws DBException;

    List<Invoice> getAllInvoices() throws DBException;

    List<InvoiceDetailed> getAllDetailedInvoices() throws DBException;

    List<Invoice> getInvoicesByUserId(int userId) throws DBException;

    double getInvoiceAmount(Booking booking) throws DBException;

    List<InvoiceDetailed> getInvoicesForUserAccount(int userID) throws DBException;
}
