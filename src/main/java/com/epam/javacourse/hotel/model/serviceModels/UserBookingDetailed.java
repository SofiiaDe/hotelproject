package com.epam.javacourse.hotel.model.serviceModels;

import com.epam.javacourse.hotel.shared.models.BookingStatus;

import java.time.LocalDate;

public class UserBookingDetailed {

    private final int id;
    private final LocalDate checkinDate;
    private final LocalDate checkoutDate;
    private final String roomTypeBySeats;
    private final String roomClass;
    private final boolean isPaid;

    public UserBookingDetailed(int id, LocalDate checkinDate, LocalDate checkoutDate,
                               String roomTypeBySeats, String roomClass, boolean isPaid) {
        this.id = id;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.roomTypeBySeats = roomTypeBySeats;
        this.roomClass = roomClass;
        this.isPaid = isPaid;
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

    public boolean getIsPaid() {
        return isPaid;
    }

    public BookingStatus getBookingStatus(){
        return Helpers.calculateBookingStatus(getCheckinDate(), getCheckoutDate(), getIsPaid());
    }
}
