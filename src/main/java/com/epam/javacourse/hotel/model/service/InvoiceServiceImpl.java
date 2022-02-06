package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.db.InvoiceDAO;
import com.epam.javacourse.hotel.model.Invoice;

import java.util.List;

public class InvoiceServiceImpl implements IInvoiceService{

    private final InvoiceDAO invoiceDAO;

    public InvoiceServiceImpl(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    @Override
    public void createInvoice(Invoice invoice) throws DBException {
        this.invoiceDAO.createInvoice(invoice);
    }

    @Override
    public List<Invoice> getAllInvoices() throws DBException {
        return this.invoiceDAO.findAllInvoices();
    }
}
