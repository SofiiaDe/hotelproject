package com.epam.javacourse.hotel;

import com.epam.javacourse.hotel.db.ApplicationDAO;
import com.epam.javacourse.hotel.db.BookingDAO;
import com.epam.javacourse.hotel.db.RoomDAO;
import com.epam.javacourse.hotel.db.UserDAO;
import com.epam.javacourse.hotel.model.service.*;

/**
 * Class creates all necessary DAOs and services at the app start.
 */
public class AppContext {

    private static final AppContext appContext = new AppContext();

    //DAOs
    private final UserDAO userDao = new UserDAO();
    private final ApplicationDAO applicationDAO = new ApplicationDAO();
    private final RoomDAO roomDAO = new RoomDAO();
    private final BookingDAO bookingDAO = new BookingDAO();

    // services
    private final IUserService userService = new UserServiceImpl(userDao);
    private final IApplicationService applicationService = new ApplicationServiceImpl(applicationDAO);
    private final IRoomService roomService = new RoomServiceImpl(roomDAO);
    private final IBookingService bookingService = new BookingServiceImpl(bookingDAO);

    public static AppContext getInstance() {
        return appContext;
    }

    public IUserService getUserService() {
        return userService;
    }

    public IApplicationService getApplicationService() {
        return applicationService;
    }

    public IRoomService getRoomService() {
        return roomService;
    }

    public IBookingService getBookingService() {
        return bookingService;
    }
}
