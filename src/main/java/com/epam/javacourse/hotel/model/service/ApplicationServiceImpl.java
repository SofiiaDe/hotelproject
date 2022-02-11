package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.db.ApplicationDAO;
import com.epam.javacourse.hotel.db.UserDAO;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Invoice;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.serviceModels.ApplicationDetailed;
import com.epam.javacourse.hotel.model.serviceModels.BookingDetailed;
import com.epam.javacourse.hotel.model.serviceModels.UserApplicationDetailed;
import com.epam.javacourse.hotel.model.serviceModels.UserInvoiceDetailed;
import com.epam.javacourse.hotel.web.command.manager.MakeConfirmRequestCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ApplicationServiceImpl implements IApplicationService {

    private static final Logger logger = LogManager.getLogger(ApplicationServiceImpl.class);

    private final ApplicationDAO applicationDAO;
    private final UserDAO userDao;

    public ApplicationServiceImpl(ApplicationDAO applicationDAO, UserDAO userDao) {
        this.applicationDAO = applicationDAO;
        this.userDao = userDao;
    }

    @Override
    public void create(Application application) throws DBException {
        this.applicationDAO.createApplication(application);
    }

    @Override
    public List<Application> getAllApplications() throws DBException {
        return this.applicationDAO.findAllApplications();
    }

    @Override
    public List<ApplicationDetailed> getAllDetailedApplications() throws DBException {
        List<Application> allApplications = this.applicationDAO.findAllApplications();
        List<Integer> userIds = allApplications.stream().map(Application::getUserId).distinct().collect(Collectors.toList());
        List<User> data = this.userDao.getUsersByIds(userIds);

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
    }

    @Override
    public List<UserApplicationDetailed> getUserDetailedApplications(int userID) throws DBException {
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
    public void removeApplication(int applicationId) throws DBException {
        this.applicationDAO.deleteApplication(applicationId);
    }

    @Override
    public Application getApplicationById(int id) throws DBException {
        return this.applicationDAO.getApplicationById(id);
    }

    @Override
    public LocalDateTime parseToLocalDateTime(String date) {
        LocalDateTime parsedDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate localDate = LocalDate.parse(date, formatter);
            parsedDate = LocalDateTime.of(localDate, LocalDateTime.now().toLocalTime());
        } catch (DateTimeParseException e) {
            logger.error("Cannot get date type");
        }
        return parsedDate;
    }
}
