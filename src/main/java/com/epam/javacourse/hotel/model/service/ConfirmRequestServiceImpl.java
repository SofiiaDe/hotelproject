package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.db.ConfirmRequestDAO;
import com.epam.javacourse.hotel.model.ConfirmationRequest;

import java.util.List;

public class ConfirmRequestServiceImpl implements IConfirmRequestService {

    private final ConfirmRequestDAO confirmRequestDAO;

    public ConfirmRequestServiceImpl(ConfirmRequestDAO confirmRequestDAO) {
        this.confirmRequestDAO = confirmRequestDAO;
    }

    @Override
    public void create(ConfirmationRequest confirmRequest) throws DBException {
        this.confirmRequestDAO.createConfirmRequest(confirmRequest);
    }

    @Override
    public List<ConfirmationRequest> getConfirmRequestsByUserId(int userId) throws DBException {
        return this.confirmRequestDAO.findConfirmRequestsByUserId(userId);
    }

    @Override
    public List<ConfirmationRequest> getAllConfirmRequests() throws DBException {
        return this.confirmRequestDAO.findAllConfirmRequests();
    }


}
