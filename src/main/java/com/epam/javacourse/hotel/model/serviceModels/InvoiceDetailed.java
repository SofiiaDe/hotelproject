package com.epam.javacourse.hotel.model.serviceModels;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class InvoiceDetailed implements Serializable {

    private static final long serialVersionUID = 1112223343L;

    private int id;
    private String bookedByUser;
    private String bookedByUserEmail;
    private BigDecimal amount;
    private int bookingId;
    private LocalDate invoiceDate;
    private String status;


    public InvoiceDetailed(int id, String bookedByUser, String bookedByUserEmail,
                           BigDecimal amount, int bookingId, LocalDate invoiceDate,
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

    public BigDecimal getAmount() {
        return amount;
    }

    public int getBookingId() {
        return bookingId;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public String getStatus() {
        return status;
    }

}
