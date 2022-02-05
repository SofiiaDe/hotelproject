package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Application;
import java.util.List;


public interface IApplicationService {

    void create(Application application) throws DBException;

    List<Application> getApplicationsByUserId(int userId) throws DBException;

    boolean updateApplication(Application application) throws DBException;

    Application getApplicationById(int id) throws DBException;
}