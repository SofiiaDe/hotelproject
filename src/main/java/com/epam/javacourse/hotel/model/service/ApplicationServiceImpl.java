package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.db.ApplicationDAO;
import com.epam.javacourse.hotel.model.Application;

import java.util.List;

public class ApplicationServiceImpl implements IApplicationService{

    private final ApplicationDAO applicationDAO;

    public ApplicationServiceImpl(ApplicationDAO applicationDAO) {
        this.applicationDAO = applicationDAO;
    }

    @Override
    public void create(Application application) throws DBException {
        this.applicationDAO.createApplication(application);
    }

    @Override
    public List<Application> getApplicationsByUserId(int userId) throws DBException {
        return this.applicationDAO.findApplicationsByUserId(userId);
    }

    @Override
    public boolean updateApplication(Application application) throws DBException {
        return this.applicationDAO.updateApplication(application);
    }

    @Override
    public Application getApplicationById(int id) throws DBException {
        return this.applicationDAO.getApplicationById(id);
    }
}
