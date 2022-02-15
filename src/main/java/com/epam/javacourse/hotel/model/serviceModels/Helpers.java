package com.epam.javacourse.hotel.model.serviceModels;

import com.epam.javacourse.hotel.shared.models.BookingStatus;

import java.time.LocalDate;

public class Helpers {
    public static BookingStatus calculateBookingStatus(LocalDate checkinDate, LocalDate checkoutDate, boolean isPaid){
            if(isPaid){
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
