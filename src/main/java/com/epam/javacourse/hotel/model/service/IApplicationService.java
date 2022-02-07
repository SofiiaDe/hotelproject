package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.serviceModels.ApplicationDetailed;
import com.epam.javacourse.hotel.model.serviceModels.UserApplicationDetailed;
import com.epam.javacourse.hotel.model.serviceModels.UserInvoiceDetailed;

import java.util.List;


public interface IApplicationService {

    void create(Application application) throws DBException;

    List<Application> getAllApplications() throws DBException;

    List<ApplicationDetailed> getAllDetailedApplications() throws DBException;

    List<Application> getApplicationsByUserId(int userId) throws DBException;

    boolean updateApplication(Application application) throws DBException;

    void removeApplication(int id) throws DBException;

    Application getApplicationById(int applicationId) throws DBException;

    List<UserApplicationDetailed> getUserDetailedApplications(int userID) throws DBException;

}
