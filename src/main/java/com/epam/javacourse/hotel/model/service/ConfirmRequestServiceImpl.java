package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.db.ConfirmRequestDAO;
import com.epam.javacourse.hotel.db.UserDAO;
import com.epam.javacourse.hotel.model.*;
import com.epam.javacourse.hotel.model.serviceModels.ConfirmationRequestDetailed;
import com.epam.javacourse.hotel.model.serviceModels.UserConfirmationRequestDetailed;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Override
    public void deleteConfirmRequestById(int id) throws DBException {
        this.deleteConfirmRequestById(id);
    }

    @Override
    public LocalDate getConfirmRequestDueDate(ConfirmationRequest confirmRequest) {
        LocalDate confirmRequestDate = confirmRequest.getConfirmRequestDate();
        return confirmRequestDate.plusDays(5);

    }

    @Override
    public List<ConfirmationRequestDetailed> getAllDetailedConfirmRequests() throws DBException {
        List<ConfirmationRequest> allConfirmRequests = this.confirmRequestDAO.findAllConfirmRequests();

        if(allConfirmRequests.isEmpty()) {
            return Collections.emptyList();
        }

        List<Integer> userIds = allConfirmRequests.stream()
                .map(ConfirmationRequest::getUserId).distinct().collect(Collectors.toList());
        List<User> data = this.userDAO.getUsersByIds(userIds);
        ArrayList<ConfirmationRequestDetailed> result = new ArrayList<>();

        for (ConfirmationRequest confirmRequest: allConfirmRequests) {
            var bookingUser = data.stream().filter(u -> u.getId() == confirmRequest.getUserId()).findFirst().get();
            result.add(
                    new ConfirmationRequestDetailed(confirmRequest.getId(),
                            bookingUser.getFirstName() + ' '+ bookingUser.getLastName(),
                            bookingUser.getEmail(),
                            confirmRequest.getApplicationId(),
                            confirmRequest.getRoomId(),
                            confirmRequest.getConfirmRequestDate(),
                            confirmRequest.getStatus()
                    ));
        }

        return result;
    }

    @Override
    public List<UserConfirmationRequestDetailed> getUserDetailedConfirmRequests(int userID) throws DBException {

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
    }
}
