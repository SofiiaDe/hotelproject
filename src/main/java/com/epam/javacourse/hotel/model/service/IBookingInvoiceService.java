package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.AppException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Invoice;

public interface IBookingInvoiceService {

    /**
     * Creates new invoice once the new booking appeared.
     * @throws AppException
     */
    boolean createBookingAndInvoice(Booking booking, Invoice invoice) throws AppException;

    double getInvoiceAmount(Booking booking) throws AppException;


}
