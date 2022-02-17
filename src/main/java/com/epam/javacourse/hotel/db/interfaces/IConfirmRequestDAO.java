package com.epam.javacourse.hotel.db.interfaces;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.ConfirmationRequest;

import java.util.List;

public interface IConfirmRequestDAO {

    boolean createConfirmRequest(ConfirmationRequest confirmRequest) throws DBException;

    List<ConfirmationRequest> findConfirmRequestsByUserId(int userId) throws DBException;

    List<ConfirmationRequest> findAllConfirmRequests() throws DBException;

    void deleteConfirmRequestById(int id) throws DBException;

    ConfirmationRequest findConfirmRequestById(int confirmRequestId) throws DBException;

    boolean updateConfirmRequestStatus(ConfirmationRequest confirmRequest) throws DBException;
}