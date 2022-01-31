package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.db.ApplicationDAO;
import com.epam.javacourse.hotel.model.Application;

public class ApplicationServiceImpl implements IApplicationService{

    private final ApplicationDAO applicationDAO;

    public ApplicationServiceImpl(ApplicationDAO applicationDAO) {
        this.applicationDAO = applicationDAO;
    }

    @Override
    public void create(Application application) throws DBException {
        this.applicationDAO.createApplication(application);
    }
}
