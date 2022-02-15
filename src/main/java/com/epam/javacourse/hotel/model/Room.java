package com.epam.javacourse.hotel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Room extends Entity implements Serializable {

    private static final long serialVersionUID = -1L;

    private int id;
    private double price;
    private int roomNumber;
    private String roomTypeBySeats;
    private String roomClass;
    private String roomStatus;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomTypeBySeats() {
        return roomTypeBySeats;
    }

    public void setRoomTypeBySeats(String roomTypeBySeats) {
        this.roomTypeBySeats = roomTypeBySeats;
    }

    public String getRoomClass() {
        return roomClass;
    }

    public void setRoomClass(String roomClass) {
        this.roomClass = roomClass;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

}
