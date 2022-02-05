package com.epam.javacourse.hotel.model;

import java.time.LocalDateTime;
import java.util.Date;

public class Invoice extends Entity{

    private static final long serialVersionUID = 1L;

    private int id;
    private int userId;
    private double amount;
    private int bookingId;
    private LocalDateTime invoiceDate;
    private String invoiceStatus;

    private InvoiceStatus status;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }
}

enum InvoiceStatus{
    NEW,
    PAID,
    CANCELLED,

}