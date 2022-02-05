package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.db.InvoiceDAO;

public class InvoiceServiceImpl implements IInvoiceService{

    private final InvoiceDAO invoiceDAO;

    public InvoiceServiceImpl(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }
}
