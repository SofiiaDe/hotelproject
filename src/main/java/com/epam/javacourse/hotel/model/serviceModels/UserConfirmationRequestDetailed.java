package com.epam.javacourse.hotel.model.serviceModels;

import java.time.LocalDateTime;

public class UserConfirmationRequestDetailed {

    private int id;
    private LocalDateTime confirmRequestDate;
    private LocalDateTime confirmRequestDueDate;
    private String roomTypeBySeats;
    private String roomClass;
    private LocalDateTime checkinDate;
    private LocalDateTime checkoutDate;
    private int applicationId;
    private String status;

    public UserConfirmationRequestDetailed(int id, LocalDateTime confirmRequestDate,
                                           LocalDateTime confirmRequestDueDate,
                                           String roomTypeBySeats, String roomClass,
                                           LocalDateTime checkinDate, LocalDateTime checkoutDate,
                                           int applicationId, String status) {
        this.id = id;
        this.confirmRequestDate = confirmRequestDate;
        this.confirmRequestDueDate = confirmRequestDueDate;
        this.roomTypeBySeats = roomTypeBySeats;
        this.roomClass = roomClass;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.applicationId = applicationId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getConfirmRequestDate() {
        return confirmRequestDate;
    }

    public LocalDateTime getConfirmRequestDueDate() {
        return confirmRequestDueDate;
    }

    public String getRoomTypeBySeats() {
        return roomTypeBySeats;
    }

    public String getRoomClass() {
        return roomClass;
    }

    public LocalDateTime getCheckinDate() {
        return checkinDate;
    }

    public LocalDateTime getCheckoutDate() {
        return checkoutDate;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public String getStatus() {
        return status;
    }
}
