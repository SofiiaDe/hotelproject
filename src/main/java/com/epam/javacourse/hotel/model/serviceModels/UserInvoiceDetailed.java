package com.epam.javacourse.hotel.model.serviceModels;

import java.time.LocalDateTime;

public class UserInvoiceDetailed {

    private int id;
    private LocalDateTime invoiceDate;
    private LocalDateTime dueDate;
    private double amount;
    private int bookingId;
    private double pricePerNight;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private String status;

    public UserInvoiceDetailed(int id, LocalDateTime invoiceDate, LocalDateTime dueDate,
                               double amount, int bookingId, double pricePerNight, LocalDateTime checkInDate,
                               LocalDateTime checkOutDate, String status) {
        this.id = id;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
        this.amount = amount;
        this.bookingId = bookingId;
        this.pricePerNight = pricePerNight;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public double getAmount() {
        return amount;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public int getBookingId() {
        return bookingId;
    }

    public LocalDateTime getCheckInDate() {
        return checkInDate;
    }

    public LocalDateTime getCheckOutDate() {
        return checkOutDate;
    }

    public String getStatus() {
        return status;
    }
}
