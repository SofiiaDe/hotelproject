package com.epam.javacourse.hotel.model.serviceModels;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class UserInvoiceDetailed implements Serializable {

    private static final long serialVersionUID = 1112223347L;

    private int id;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private BigDecimal amount;
    private int bookingId;
    private BigDecimal pricePerNight;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;

    public UserInvoiceDetailed(int id, LocalDate invoiceDate, LocalDate dueDate,
                               BigDecimal amount, int bookingId, BigDecimal pricePerNight, LocalDate checkInDate,
                               LocalDate checkOutDate, String status) {
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

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public int getBookingId() {
        return bookingId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public String getStatus() {
        return status;
    }
}
