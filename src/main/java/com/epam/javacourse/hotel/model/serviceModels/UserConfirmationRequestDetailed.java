package com.epam.javacourse.hotel.model.serviceModels;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserConfirmationRequestDetailed {

    private int id;
    private LocalDate confirmRequestDate;
    private LocalDate confirmRequestDueDate;
    private String roomTypeBySeats;
    private String roomClass;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private int applicationId;
    private String status;

    public UserConfirmationRequestDetailed(int id, LocalDate confirmRequestDate,
                                           LocalDate confirmRequestDueDate,
                                           String roomTypeBySeats, String roomClass,
                                           LocalDate checkinDate, LocalDate checkoutDate,
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

    public LocalDate getConfirmRequestDate() {
        return confirmRequestDate;
    }

    public LocalDate getConfirmRequestDueDate() {
        return confirmRequestDueDate;
    }

    public String getRoomTypeBySeats() {
        return roomTypeBySeats;
    }

    public String getRoomClass() {
        return roomClass;
    }

    public LocalDate getCheckinDate() {
        return checkinDate;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public String getStatus() {
        return status;
    }
}
