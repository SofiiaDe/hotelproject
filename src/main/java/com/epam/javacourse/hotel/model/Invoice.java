package com.epam.javacourse.hotel.model;

import java.util.Date;

public class Invoice extends Entity{

    private static final long serialVersionUID = 1L;

    private int id;
    private User user;
    private double amount;
    private Date invoiceDate;
    private InvoiceStatus status;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
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