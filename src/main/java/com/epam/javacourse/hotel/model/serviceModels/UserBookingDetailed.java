package com.epam.javacourse.hotel.model.serviceModels;

import com.epam.javacourse.hotel.shared.models.BookingStatus;

import java.time.LocalDate;

public class UserBookingDetailed {

    private int id;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private String roomTypeBySeats;
    private String roomClass;
    private boolean isPaid;

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

        if(getIsPaid()){
            if (LocalDate.now().isBefore(checkinDate)){
                return BookingStatus.PAID;
            }
            if (LocalDate.now().isAfter(checkinDate) && LocalDate.now().isBefore(checkoutDate)){
                return BookingStatus.ONGOING;
            }
            if (LocalDate.now().isAfter(checkoutDate)){
                return BookingStatus.FINISHED;
            }
        }else{
            if (LocalDate.now().isBefore(checkinDate)){
                return BookingStatus.NEW;
            }
            return BookingStatus.CANCELLED;
        }

        return BookingStatus.NONE;
    }
}
