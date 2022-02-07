package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.ConfirmationRequest;

import java.util.List;

public interface IConfirmRequestService {

    void create(ConfirmationRequest confirmRequest) throws DBException;

    List<ConfirmationRequest> getConfirmRequestsByUserId(int userId) throws DBException;

    List<ConfirmationRequest> getAllConfirmRequests() throws DBException;

    void deleteConfirmRequestById(int id) throws DBException;

}
