package com.epam.javacourse.hotel.model.serviceModels;

import java.time.LocalDateTime;

public class ApplicationDetailed {
    private final int id;
    private final String bookedByUser;
    private final String bookedByUserEmail;
    private final LocalDateTime checkinDate;
    private final LocalDateTime checkoutDate;
    private final String roomTypeBySeats;
    private final String roomClass;

    public ApplicationDetailed(int id, String bookedByUser, String bookedByUserEmail,
                               LocalDateTime checkinDate, LocalDateTime checkoutDate, String roomTypeBySeats, String roomClass) {
        this.id = id;
        this.bookedByUser = bookedByUser;
        this.bookedByUserEmail = bookedByUserEmail;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.roomTypeBySeats = roomTypeBySeats;
        this.roomClass = roomClass;
    }

    public int getId() {
        return id;
    }

    public String getBookedByUser() {
        return bookedByUser;
    }

    public String getBookedByUserEmail() { return bookedByUserEmail; }

    public LocalDateTime getCheckoutDate() {
        return checkoutDate;
    }

    public LocalDateTime getCheckinDate() {
        return checkinDate;
    }

    public String getRoomTypeBySeats() {
        return roomTypeBySeats;
    }

    public String getRoomClass() {
        return roomClass;
    }

}
