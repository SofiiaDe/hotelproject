package com.epam.javacourse.hotel.model.serviceModels;

import com.epam.javacourse.hotel.shared.models.BookingStatus;

import java.time.LocalDate;

public class BookingDetailed{

    private static final long serialVersionUID = 1L;

    private final int id;
    private final String bookedByUser;
    private final String bookedByUserEmail;
    private final LocalDate checkinDate;
    private final LocalDate checkoutDate;
    private final int roomId;
    private final boolean isPaid;

    public BookingDetailed(int id, String bookedByUser, String bookedByUserEmail,
                           LocalDate checkinDate, LocalDate checkoutDate, int roomId, boolean isPaid) {
        this.id = id;
        this.bookedByUser = bookedByUser;
        this.bookedByUserEmail = bookedByUserEmail;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.roomId = roomId;
        this.isPaid = isPaid;
    }

    public int getId() {
        return id;
    }
    public String getBookedByUser(){
        return bookedByUser;
    }
    public String getBookedByUserEmail() {
        return bookedByUserEmail;
    }
    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }
    public LocalDate getCheckinDate() {
        return checkinDate;
    }
    public boolean getIsPaid() {
        return isPaid;
    }
    public int getRoomId() {
        return roomId;
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
