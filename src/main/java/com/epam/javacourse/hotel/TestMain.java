package com.epam.javacourse.hotel;

import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Room;
import com.epam.javacourse.hotel.model.service.impl.RoomServiceImpl;
import com.epam.javacourse.hotel.model.service.interfaces.IRoomService;
import com.epam.javacourse.hotel.utils.AppContext;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Period;

public class TestMain {

    public static void main(String[] args) throws AppException {

        IRoomService roomService = AppContext.getInstance().getRoomService();

        Room room = roomService.getRoomById(20);

                // initialize amount as 0 for a default value
        BigDecimal amount = new BigDecimal(BigInteger.ZERO, 2);

        // number of days is converted to BigDecimal
        BigDecimal totalCost = room.getPrice().multiply(new BigDecimal(Math.abs(2)));
        amount = amount.add(totalCost);

        System.out.println(amount);

    }
}



