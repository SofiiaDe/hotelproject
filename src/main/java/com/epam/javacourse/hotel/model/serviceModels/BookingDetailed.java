package com.epam.javacourse.hotel.model.serviceModels;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingDetailed{

    private static final long serialVersionUID = 1L;

    private int id;
    private String bookedByUser;
    private String bookedByUserEmail;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private int roomId;
    private boolean isPaid;

    public BookingDetailed(int id, String bookedByUser, String bookedByUserEmail,
                           LocalDate checkinDate, LocalDate checkoutDate, int roomId, boolean isPaid) {
        this.id = id;
        this.bookedByUser = bookedByUser;
        this.bookedByUserEmail = bookedByUserEmail;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.roomId = roomId;
        this.isPaid = isPaid;
    }

    public int getId() {
        return id;
    }
    public String getBookedByUser(){
        return bookedByUser;
    }
    public String getBookedByUserEmail() {
        return bookedByUserEmail;
    }
    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }
    public LocalDate getCheckinDate() {
        return checkinDate;
    }
    public boolean getIsPaid() {
        return isPaid;
    }
    public int getRoomId() {
        return roomId;
    }
}
