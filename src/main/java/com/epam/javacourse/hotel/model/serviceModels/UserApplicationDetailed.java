package com.epam.javacourse.hotel.model.serviceModels;

import java.io.Serializable;
import java.time.LocalDate;

public class UserApplicationDetailed implements Serializable {

    private static final long serialVersionUID = 1112223344L;

    private int id;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private String roomTypeBySeats;
    private String roomClass;

    public UserApplicationDetailed(int id, LocalDate checkinDate, LocalDate checkoutDate,
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

    public LocalDate getCheckinDate() {
        return checkinDate;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public String getRoomTypeBySeats() {
        return roomTypeBySeats;
    }

    public String getRoomClass() {
        return roomClass;
    }
}
