package com.epam.javacourse.hotel.db.models;

public class BookingInvoiceIdModel {

    private final int bookingId;

    private final int invoiceId;

    public BookingInvoiceIdModel(int bookingId, int invoiceId) {
        this.bookingId = bookingId;
        this.invoiceId = invoiceId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }
}
