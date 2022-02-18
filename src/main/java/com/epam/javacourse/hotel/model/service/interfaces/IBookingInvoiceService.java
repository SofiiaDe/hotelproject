package com.epam.javacourse.hotel.model.service.interfaces;

import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Invoice;

import java.math.BigDecimal;

public interface IBookingInvoiceService {

    /**
     * Creates new invoice once the new booking appeared.
     * @throws AppException
     */
    boolean createBookingAndInvoice(Booking booking, Invoice invoice) throws AppException;

    BigDecimal getInvoiceAmount(Booking booking) throws AppException;


}
