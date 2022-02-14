package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.AppException;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.serviceModels.ApplicationDetailed;
import com.epam.javacourse.hotel.model.serviceModels.UserApplicationDetailed;

import java.util.List;


public interface IApplicationService {

    void create(Application application) throws AppException;

    List<Application> getAllApplications() throws AppException;

    List<ApplicationDetailed> getAllDetailedApplications() throws AppException;

    List<Application> getApplicationsByUserId(int userId) throws AppException;

    boolean updateApplication(Application application) throws AppException;

    void removeApplication(int id) throws AppException;

    Application getApplicationById(int applicationId) throws AppException;

    List<UserApplicationDetailed> getUserDetailedApplications(int userID) throws AppException;

}
