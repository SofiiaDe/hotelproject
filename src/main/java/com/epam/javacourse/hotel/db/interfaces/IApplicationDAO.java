package com.epam.javacourse.hotel.db.interfaces;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Application;

import java.util.List;

public interface IApplicationDAO {

    boolean createApplication(Application application) throws DBException;

    Application findApplicationById(int id) throws DBException;

    List<Application> findAllApplications() throws DBException;

    List<Application> findApplicationsByUserId(int userId) throws DBException;

    boolean updateApplication(Application application) throws DBException;

    void deleteApplication(int applicationId) throws DBException;

}
