package com.epam.javacourse.hotel.db.models;

public class BookingRoomIdModel {
    private final int bookingId;
    private final int roomId;

    public BookingRoomIdModel(int bookingId, int roomId) {
        this.bookingId = bookingId;
        this.roomId = roomId;
    }

    public int getBookingId(){
        return bookingId;
    }

    public int getRoomId(){
        return roomId;
    }
}
