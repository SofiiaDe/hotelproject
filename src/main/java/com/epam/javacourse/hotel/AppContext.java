package com.epam.javacourse.hotel;

import com.epam.javacourse.hotel.db.*;
import com.epam.javacourse.hotel.model.service.*;

/**
 * Class creates all necessary DAOs and services at the app start.
 */
public class AppContext {

    private static final AppContext appContext = new AppContext();

    public static AppContext getInstance() {
        return appContext;
    }

    //DAOs
    private final UserDAO userDAO = new UserDAO();
    private final ApplicationDAO applicationDAO = new ApplicationDAO();
    private final RoomDAO roomDAO = new RoomDAO();
    private final BookingDAO bookingDAO = new BookingDAO();
    private final ConfirmRequestDAO confirmRequestDAO = new ConfirmRequestDAO();
    private final InvoiceDAO invoiceDAO = new InvoiceDAO();

    // services
    private final IUserService userService = new UserServiceImpl(userDAO);
    private final IApplicationService applicationService = new ApplicationServiceImpl(applicationDAO, userDAO);
    private final IRoomService roomService = new RoomServiceImpl(roomDAO);
    private final IBookingService bookingService = new BookingServiceImpl(bookingDAO, userDAO, invoiceDAO);
    private final IConfirmRequestService confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAO, userDAO);
    private final IInvoiceService invoiceService =new InvoiceServiceImpl(invoiceDAO, userDAO);

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

    public IConfirmRequestService getConfirmRequestService() {
        return confirmRequestService;
    }

    public IInvoiceService getInvoiceService() {
        return invoiceService;
    }

    /**
     * TODO move to config to make it configurable
     * @return number of items to return on typical page
     */
    public int getDefaultPageSize(){
        return 5;
    }
}
