package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Invoice;

import java.util.List;

public interface IInvoiceService {

    void createInvoice(Invoice invoice) throws DBException;

    List<Invoice> getAllInvoices() throws DBException;
}
