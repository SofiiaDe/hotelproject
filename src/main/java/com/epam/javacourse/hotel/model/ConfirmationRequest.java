package com.epam.javacourse.hotel.model;

import java.io.Serializable;
import java.time.LocalDate;

public class ConfirmationRequest extends Entity implements Serializable {

    private static final long serialVersionUID = 1112223336L;

    private int id;
    private int userId;
    private int applicationId;
    private int roomId;
    private LocalDate confirmRequestDate;
    private LocalDate confirmRequestDueDate;
    private String status;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getConfirmRequestDueDate() {
        return confirmRequestDueDate;
    }

    public void setConfirmRequestDueDate(LocalDate confirmRequestDueDate) {
        this.confirmRequestDueDate = confirmRequestDueDate;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public LocalDate getConfirmRequestDate() {
        return confirmRequestDate;
    }

    public void setConfirmRequestDate(LocalDate confirmRequestDate) {
        this.confirmRequestDate = confirmRequestDate;
    }


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

enum ConfirmRequestStatus{
    NEW,
    CONFIRMED,
    CANCELLED,
}
