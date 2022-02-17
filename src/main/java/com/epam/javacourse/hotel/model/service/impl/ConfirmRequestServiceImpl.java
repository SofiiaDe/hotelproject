package com.epam.javacourse.hotel.model.service.impl;

import com.epam.javacourse.hotel.model.service.interfaces.IApplicationService;
import com.epam.javacourse.hotel.model.service.interfaces.IConfirmRequestService;
import com.epam.javacourse.hotel.utils.AppContext;
import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.db.dao.ConfirmRequestDAO;
import com.epam.javacourse.hotel.db.dao.UserDAO;
import com.epam.javacourse.hotel.model.*;
import com.epam.javacourse.hotel.model.serviceModels.ConfirmationRequestDetailed;
import com.epam.javacourse.hotel.model.serviceModels.UserConfirmationRequestDetailed;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ConfirmRequestServiceImpl implements IConfirmRequestService {

    private final ConfirmRequestDAO confirmRequestDAO;
    private final UserDAO userDAO;

    public ConfirmRequestServiceImpl(ConfirmRequestDAO confirmRequestDAO, UserDAO userDAO) {
        this.confirmRequestDAO = confirmRequestDAO;
        this.userDAO = userDAO;
    }

    @Override
    public void create(ConfirmationRequest confirmRequest) throws AppException {
        try {
            this.confirmRequestDAO.createConfirmRequest(confirmRequest);
        } catch (DBException exception) {
            throw new AppException("Can't create confirmation request", exception);
        }
    }

    @Override
    public ConfirmationRequest getConfirmRequestById(int confirmRequestId) throws AppException {
        try {
            return this.confirmRequestDAO.findConfirmRequestById(confirmRequestId);
        } catch (DBException exception) {
            throw new AppException("Can't get confirmation request by id");
        }
    }

    @Override
    public List<ConfirmationRequest> getConfirmRequestsByUserId(int userId) throws AppException {
        try {
            return this.confirmRequestDAO.findConfirmRequestsByUserId(userId);
        } catch (DBException exception) {
            throw new AppException("Can't retrieve clients confirmation requests", exception);
        }
    }

    @Override
    public List<ConfirmationRequest> getAllConfirmRequests() throws AppException {
        try {
            return this.confirmRequestDAO.findAllConfirmRequests();
        } catch (DBException exception) {
            throw new AppException("Can't retrieve all confirmation requests", exception);
        }
    }

    @Override
    public void deleteConfirmRequestById(int id) throws AppException {
        try {
            this.deleteConfirmRequestById(id);
        } catch (DBException exception) {
            throw new AppException("Can't remove confirmation request by id", exception);
        }
    }

    @Override
    public LocalDate getConfirmRequestDueDate(ConfirmationRequest confirmRequest) {
        LocalDate confirmRequestDate = confirmRequest.getConfirmRequestDate();
        return confirmRequestDate.plusDays(2);

    }

    @Override
    public List<ConfirmationRequestDetailed> getAllDetailedConfirmRequests() throws AppException {
        try {
            List<ConfirmationRequest> allConfirmRequests = this.confirmRequestDAO.findAllConfirmRequests();

            if (allConfirmRequests.isEmpty()) {
                return Collections.emptyList();
            }

            List<Integer> userIds = allConfirmRequests.stream()
                    .map(ConfirmationRequest::getUserId).distinct().collect(Collectors.toList());
            List<User> data = this.userDAO.findUsersByIds(userIds);
            ArrayList<ConfirmationRequestDetailed> result = new ArrayList<>();

            for (ConfirmationRequest confirmRequest : allConfirmRequests) {
                var bookingUser = data.stream().filter(u -> u.getId() == confirmRequest.getUserId()).findFirst().get();
                result.add(
                        new ConfirmationRequestDetailed(confirmRequest.getId(),
                                bookingUser.getFirstName() + ' ' + bookingUser.getLastName(),
                                bookingUser.getEmail(),
                                confirmRequest.getApplicationId(),
                                confirmRequest.getRoomId(),
                                confirmRequest.getConfirmRequestDate(),
                                confirmRequest.getStatus()
                        ));
            }

            return result;
        } catch (DBException exception) {
            throw new AppException("Can't retrieve all confirmation requests to show in the manager's account", exception);
        }
    }

    @Override
    public List<UserConfirmationRequestDetailed> getUserDetailedConfirmRequests(int userID) throws AppException {

        try {

            List<ConfirmationRequest> allUserConfirmRequests = this.confirmRequestDAO.findConfirmRequestsByUserId(userID);
            IApplicationService applicationService = AppContext.getInstance().getApplicationService();
            List<Application> userApplications = applicationService.getApplicationsByUserId(userID);

            ArrayList<UserConfirmationRequestDetailed> result = new ArrayList<>();

            for (ConfirmationRequest confirmRequest : allUserConfirmRequests) {
                var application = userApplications.stream()
                        .filter(a -> a.getId() == confirmRequest.getApplicationId())
                        .findFirst()
                        .get();
                result.add(
                        new UserConfirmationRequestDetailed(confirmRequest.getId(),
                                confirmRequest.getConfirmRequestDate(),
                                getConfirmRequestDueDate(confirmRequest),
                                application.getRoomTypeBySeats(),
                                application.getRoomClass(),
                                application.getCheckinDate(),
                                application.getCheckoutDate(),
                                confirmRequest.getApplicationId(),
                                confirmRequest.getStatus()
                        ));
            }
            return result;
        } catch (DBException exception) {
            throw new AppException("Can't retrieve client's confirmation requests to show in the client's account", exception);
        }
    }

    @Override
    public void confirmRequestByClient(int confirmRequestId) throws AppException {

        try {
            ConfirmationRequest requestToBeConfirmed = this.confirmRequestDAO.findConfirmRequestById(confirmRequestId);
            requestToBeConfirmed.setStatus("confirmed");
            this.confirmRequestDAO.updateConfirmRequestStatus(requestToBeConfirmed);
        } catch (DBException exception) {
            throw new AppException("Can't update confirmation request's status to 'confirmed'", exception);        }
    }
}
