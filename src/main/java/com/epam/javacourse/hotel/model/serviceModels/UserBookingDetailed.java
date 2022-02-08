package com.epam.javacourse.hotel.model.serviceModels;

import java.time.LocalDateTime;

public class UserBookingDetailed {

    private int id;
    private LocalDateTime checkinDate;
    private LocalDateTime checkoutDate;
    private String roomTypeBySeats;
    private String roomClass;
    private boolean isPaid;

    public UserBookingDetailed(int id, LocalDateTime checkinDate, LocalDateTime checkoutDate,
                               String roomTypeBySeats, String roomClass, boolean isPaid) {
        this.id = id;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.roomTypeBySeats = roomTypeBySeats;
        this.roomClass = roomClass;
        this.isPaid = isPaid;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getCheckinDate() {
        return checkinDate;
    }

    public LocalDateTime getCheckoutDate() {
        return checkoutDate;
    }

    public String getRoomTypeBySeats() {
        return roomTypeBySeats;
    }

    public String getRoomClass() {
        return roomClass;
    }

    public boolean isPaid() {
        return isPaid;
    }
}