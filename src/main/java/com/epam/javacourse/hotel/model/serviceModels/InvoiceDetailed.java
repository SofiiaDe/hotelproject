package com.epam.javacourse.hotel.model.serviceModels;

import java.time.LocalDateTime;

public class InvoiceDetailed {

    private int id;
    private String bookedByUser;
    private String bookedByUserEmail;
    private double amount;
    private int bookingId;
    private LocalDateTime invoiceDate;
    private String status;

    public InvoiceDetailed(int id, String bookedByUser, String bookedByUserEmail,
                           double amount, int bookingId, LocalDateTime invoiceDate,
                           String status) {
        this.id = id;
        this.bookedByUser = bookedByUser;
        this.bookedByUserEmail = bookedByUserEmail;
        this.amount = amount;
        this.bookingId = bookingId;
        this.invoiceDate = invoiceDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getBookedByUser() {
        return bookedByUser;
    }

    public String getBookedByUserEmail() {
        return bookedByUserEmail;
    }

    public double getAmount() {
        return amount;
    }

    public int getBookingId() {
        return bookingId;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public String getStatus() {
        return status;
    }
}
