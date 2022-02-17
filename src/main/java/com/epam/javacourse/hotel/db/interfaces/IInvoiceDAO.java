package com.epam.javacourse.hotel.db.interfaces;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Invoice;

import java.util.List;

public interface IInvoiceDAO {

    boolean createInvoice(Invoice invoice) throws DBException;

    List<Invoice> findAllInvoices() throws DBException;

    List<Invoice> findInvoicesByUserId(int userId) throws DBException;

    List<Integer> findCancelledInvoicedBookingIds(List<Integer> bookingIds) throws DBException;

    boolean updateInvoiceStatus(Invoice invoice) throws DBException;

    boolean updateInvoice(Invoice invoice) throws DBException;

    List<Invoice> findInvoicesByStatus(String status) throws DBException;

    Invoice findInvoiceById(int invoiceId) throws DBException;

    /**
     * Get invoices attached to bookings
     *
     * @param bookingIds ids of bookings for which retrieve invoices
     * @return invoices by provided booking ids
     * @throws DBException
     */
    List<Invoice> findInvoices(List<Integer> bookingIds) throws DBException;
}
