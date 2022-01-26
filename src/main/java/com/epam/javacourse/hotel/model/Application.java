package com.epam.javacourse.hotel.model;

import java.util.Date;

public class Application extends Entity{

    private int id;
    private User user;
    private RoomTypeBySeats roomTypeBySeats;
    private RoomClass roomClass;
    private Date checkInDate;
    private Date checkOutDate;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RoomTypeBySeats getRoomTypeBySeats() {
        return roomTypeBySeats;
    }

    public void setRoomTypeBySeats(RoomTypeBySeats roomTypeBySeats) {
        this.roomTypeBySeats = roomTypeBySeats;
    }

    public RoomClass getRoomClass() {
        return roomClass;
    }

    public void setRoomClass(RoomClass roomClass) {
        this.roomClass = roomClass;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}
