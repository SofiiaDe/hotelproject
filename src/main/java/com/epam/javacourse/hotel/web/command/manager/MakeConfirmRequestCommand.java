package com.epam.javacourse.hotel.web.command.manager;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.Validator;
import com.epam.javacourse.hotel.model.*;
import com.epam.javacourse.hotel.model.service.IApplicationService;
import com.epam.javacourse.hotel.model.service.IConfirmRequestService;
import com.epam.javacourse.hotel.model.service.IRoomService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import com.epam.javacourse.hotel.web.command.RedirectCommandResult;
import com.epam.javacourse.hotel.web.command.norole.LoginCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class MakeConfirmRequestCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(MakeConfirmRequestCommand.class);

    IRoomService roomService = AppContext.getInstance().getRoomService();
    IApplicationService applicationService = AppContext.getInstance().getApplicationService();
    IConfirmRequestService confirmRequestService = AppContext.getInstance().getConfirmRequestService();

    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        HttpSession session = request.getSession();
        User authorisedUser = (User) session.getAttribute("authorisedUser");

        if (!"manager".equalsIgnoreCase((String) session.getAttribute("userRole"))) {
            logger.error("You do not have permission to create a confirmation request. " +
                    "Please login as Manager.");
            return new AddressCommandResult(Path.PAGE_LOGIN);
        }


        String freeRoomAttrName = "freeRooms";

        var checkin = request.getParameter("checkin_date");
        if(!Validator.validateDate(checkin)) {
            return new RedirectCommandResult(Path.MANAGER_ACCOUNT_PAGE);
        }

        var checkout = request.getParameter("checkout_date");
        if(!Validator.validateDate(checkout)) {
            return new RedirectCommandResult(Path.MANAGER_ACCOUNT_PAGE);
        }


        if (checkin == null || checkout == null){
            session.removeAttribute(freeRoomAttrName);
            return new AddressCommandResult(Path.PAGE_FREE_ROOMS);
        }

        LocalDate checkinDate = LocalDate.parse(request.getParameter("checkin_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate checkoutDate = LocalDate.parse(request.getParameter("checkout_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));


        List<Room> freeRoomsRequest = roomService.getFreeRoomsForPeriod(checkinDate, checkoutDate);

        String notification;

        if (freeRoomsRequest.isEmpty()) {
            notification = "There are no free rooms.";
            request.setAttribute("notification", notification);
            return new AddressCommandResult(Path.PAGE_MANAGER_ACCOUNT);

        }

        int id = Integer.parseInt((String) session.getAttribute("applicationId"));
        Application applicationToBeRequested = applicationService.getApplicationById(id);

        ConfirmationRequest newConfirmationRequest = new ConfirmationRequest();
        newConfirmationRequest.setUserId(applicationToBeRequested.getUserId());
        newConfirmationRequest.setApplicationId(id);
        newConfirmationRequest.setRoomId(roomService.chooseSuitableRoomForRequest(applicationToBeRequested, freeRoomsRequest).getId());
        newConfirmationRequest.setConfirmRequestDate(LocalDateTime.now());
        newConfirmationRequest.setConfirmRequestStatus("new");

        confirmRequestService.create(newConfirmationRequest);

        return new RedirectCommandResult(Path.PAGE_MANAGER_ACCOUNT);
    }


}
