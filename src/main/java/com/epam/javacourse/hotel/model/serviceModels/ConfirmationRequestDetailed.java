package com.epam.javacourse.hotel.model.serviceModels;

import java.io.Serializable;
import java.time.LocalDate;

public class ConfirmationRequestDetailed implements Serializable {

    private static final long serialVersionUID = 1112223342L;

    private int id;
    private String bookedByUser;
    private String bookedByUserEmail;
    private int applicationId;
    private int roomId;
    private LocalDate confirmRequestDate;
    private String status;

    public ConfirmationRequestDetailed(int id, String bookedByUser, String bookedByUserEmail,
                                       int applicationId, int roomId, LocalDate confirmRequestDate, String status) {
        this.id = id;
        this.bookedByUser = bookedByUser;
        this.bookedByUserEmail = bookedByUserEmail;
        this.applicationId = applicationId;
        this.roomId = roomId;
        this.confirmRequestDate = confirmRequestDate;
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

    public int getApplicationId() {
        return applicationId;
    }

    public int getRoomId() {
        return roomId;
    }

    public LocalDate getConfirmRequestDate() {
        return confirmRequestDate;
    }

    public String getStatus() {
        return status;
    }
}
