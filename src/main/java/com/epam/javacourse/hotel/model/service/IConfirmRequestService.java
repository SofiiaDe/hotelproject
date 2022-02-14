package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.AppException;
import com.epam.javacourse.hotel.model.ConfirmationRequest;
import com.epam.javacourse.hotel.model.serviceModels.ConfirmationRequestDetailed;
import com.epam.javacourse.hotel.model.serviceModels.UserConfirmationRequestDetailed;

import java.time.LocalDate;
import java.util.List;

public interface IConfirmRequestService {

    void create(ConfirmationRequest confirmRequest) throws AppException;

    List<ConfirmationRequest> getConfirmRequestsByUserId(int userId) throws AppException;

    List<ConfirmationRequest> getAllConfirmRequests() throws AppException;

    List<ConfirmationRequestDetailed> getAllDetailedConfirmRequests() throws AppException;

    void deleteConfirmRequestById(int id) throws AppException;

    List<UserConfirmationRequestDetailed> getUserDetailedConfirmRequests(int userID) throws AppException;

    /**
     * Calculates due date until which the client has to confirm request
     * @param confirmRequest
     * @return LocalDate
     */
    LocalDate getConfirmRequestDueDate(ConfirmationRequest confirmRequest);

    void confirmRequestByClient(int confirmRequestId) throws AppException;
}
