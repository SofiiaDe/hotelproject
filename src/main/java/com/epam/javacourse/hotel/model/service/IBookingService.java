package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.User;

import java.util.List;

public interface IBookingService {

    boolean create(Booking booking) throws DBException;

    List<Booking> getBookingsByUserId(int userId) throws DBException;



}
