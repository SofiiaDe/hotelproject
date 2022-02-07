package com.epam.javacourse.hotel.model.serviceModels;

import java.time.LocalDateTime;

public class UserApplicationDetailed {

    private int id;
    private LocalDateTime checkinDate;
    private LocalDateTime checkoutDate;
    private String roomTypeBySeats;
    private String roomClass;

    public UserApplicationDetailed(int id, LocalDateTime checkinDate, LocalDateTime checkoutDate,
                                   String roomTypeBySeats, String roomClass) {
        this.id = id;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.roomTypeBySeats = roomTypeBySeats;
        this.roomClass = roomClass;
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
}
