package com.epam.javacourse.hotel.model;

import java.time.LocalDate;

public class Booking extends Entity{

    private static final long serialVersionUID = 1L;

    private int id;
    private int userId;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private int roomId;
    private int applicationId;
    private boolean status;

    public Booking() {
    }

    public Booking(int id, int userId, LocalDate checkinDate, LocalDate checkoutDate, int roomId, int applicationId) {
        this.id = id;
        this.userId = userId;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.roomId = roomId;
        this.applicationId = applicationId;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public LocalDate getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(LocalDate checkinDate) {
        this.checkinDate = checkinDate;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
