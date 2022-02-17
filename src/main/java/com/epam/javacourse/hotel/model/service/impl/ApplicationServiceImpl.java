package com.epam.javacourse.hotel.model.service.impl;

import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.db.dao.ApplicationDAO;
import com.epam.javacourse.hotel.db.dao.UserDAO;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.interfaces.IApplicationService;
import com.epam.javacourse.hotel.model.serviceModels.ApplicationDetailed;
import com.epam.javacourse.hotel.model.serviceModels.UserApplicationDetailed;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Application Service interface implementation.
 */
public class ApplicationServiceImpl implements IApplicationService {

    private final ApplicationDAO applicationDAO;
    private final UserDAO userDao;

    public ApplicationServiceImpl(ApplicationDAO applicationDAO, UserDAO userDao) {
        this.applicationDAO = applicationDAO;
        this.userDao = userDao;
    }

    @Override
    public void create(Application application) throws AppException {
        try {
            this.applicationDAO.createApplication(application);
        } catch (DBException exception) {
            throw new AppException("Can't create application", exception);
        }
    }

    @Override
    public List<Application> getAllApplications() throws AppException {
        try {
            return this.applicationDAO.findAllApplications();
        } catch (DBException exception) {
            throw new AppException("Can't retrieve all applications", exception);
        }
    }

    @Override
    public List<ApplicationDetailed> getAllDetailedApplications() throws AppException {
        try {
            List<Application> allApplications = this.applicationDAO.findAllApplications();
            List<Integer> userIds = allApplications.stream().map(Application::getUserId).distinct().collect(Collectors.toList());
            List<User> data = this.userDao.findUsersByIds(userIds);

            ArrayList<ApplicationDetailed> result = new ArrayList<>();

            for (Application application : allApplications) {
                var user = data.stream().filter(u -> u.getId() == application.getUserId()).findFirst().get();
                result.add(
                        new ApplicationDetailed(application.getId(),
                                user.getFirstName() + ' ' + user.getLastName(),
                                user.getEmail(),
                                application.getCheckinDate(),
                                application.getCheckoutDate(),
                                application.getRoomTypeBySeats(),
                                application.getRoomClass()
                        ));
            }

            return result;
        } catch (DBException exception) {
            throw new AppException("Can't retrieve all applications to show in the manager's account", exception);
        }
    }

    @Override
    public List<UserApplicationDetailed> getUserDetailedApplications(int userID) throws AppException {
        try {
            List<Application> allUserApplications = this.applicationDAO.findApplicationsByUserId(userID);

            ArrayList<UserApplicationDetailed> result = new ArrayList<>();

            for (Application application : allUserApplications) {
                result.add(
                        new UserApplicationDetailed(application.getId(),
                                application.getCheckinDate(),
                                application.getCheckoutDate(),
                                application.getRoomTypeBySeats(),
                                application.getRoomClass()
                        ));
            }
            return result;
        } catch (DBException exception) {
            throw new AppException("Can't retrieve client's applications to show in the client's account", exception);
        }
    }


    @Override
    public List<Application> getApplicationsByUserId(int userId) throws AppException {
        try {
            return this.applicationDAO.findApplicationsByUserId(userId);
        } catch (DBException exception) {
            throw new AppException("Can't retrieve client's applications", exception);
        }
    }

    @Override
    public boolean updateApplication(Application application) throws AppException {
        try {
            return this.applicationDAO.updateApplication(application);
        } catch (DBException exception) {
            throw new AppException("Can't update application", exception);
        }
    }

    @Override
    public void removeApplication(int applicationId) throws AppException {
        try {
            this.applicationDAO.deleteApplication(applicationId);
        } catch (DBException exception) {
            throw new AppException("Can't remove application", exception);
        }
    }

    @Override
    public Application getApplicationById(int id) throws AppException {
        try {
            return this.applicationDAO.findApplicationById(id);
        } catch (DBException exception) {
            throw new AppException("Can't retrieve application by its id", exception);
        }
    }

}
