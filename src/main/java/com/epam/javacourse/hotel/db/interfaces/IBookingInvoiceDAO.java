package com.epam.javacourse.hotel.db.interfaces;

import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Invoice;

public interface IBookingInvoiceDAO {

    boolean createBookingAndInvoice(Booking booking, Invoice invoice) throws DBException;
}
