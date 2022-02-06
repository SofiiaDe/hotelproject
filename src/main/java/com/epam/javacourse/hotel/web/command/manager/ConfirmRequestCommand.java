package com.epam.javacourse.hotel.web.command.manager;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.*;
import com.epam.javacourse.hotel.model.service.IApplicationService;
import com.epam.javacourse.hotel.model.service.IConfirmRequestService;
import com.epam.javacourse.hotel.model.service.IRoomService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import com.epam.javacourse.hotel.web.command.norole.LoginCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

public class ConfirmRequestCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(LoginCommand.class);

    IRoomService roomService = AppContext.getInstance().getRoomService();
    IApplicationService applicationService = AppContext.getInstance().getApplicationService();
    IConfirmRequestService confirmRequestService = AppContext.getInstance().getConfirmRequestService();

    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        HttpSession session = request.getSession();
        User authorisedUser = (User) session.getAttribute("authorisedUser");

        List<Room> allRoomsList = roomService.allRoomsList();

        List<Room> freeRooms = allRoomsList
                .stream()
                .filter(status -> status.getRoomStatus().equals("free"))
                .collect(Collectors.toList());

        if (session.getAttribute("userRole") != Role.MANAGER) {
            logger.error("You do not have permission to create a confirmation request. " +
                    "Please login as Manager.");
            return new AddressCommandResult(Path.PAGE_LOGIN);
        }

        String notification;

        if (freeRooms.isEmpty()) {
            notification = "There are no free rooms.";
            request.setAttribute("notification", notification);
            return new AddressCommandResult(Path.PAGE_MANAGER_ACCOUNT);
        }

        int id = Integer.parseInt((String) session.getAttribute("applicationId"));
        Application applicationToBeRequested = applicationService.getApplicationById(id);

        ConfirmationRequest newConfirmationRequest = new ConfirmationRequest();
        newConfirmationRequest.setUserId(applicationToBeRequested.getUserId());
        newConfirmationRequest.setApplicationId(id);
        newConfirmationRequest.setRoomId(chooseSuitableRoom(applicationToBeRequested, freeRooms).getId());
        newConfirmationRequest.setConfirmRequestStatus("new");

        confirmRequestService.create(newConfirmationRequest);

        return new AddressCommandResult(Path.PAGE_MANAGER_ACCOUNT);
    }

    /**
     * returns a Room which is the most suitable according to the Client's criteria specified in the application
     *
     * @param application
     * @param freeRooms
     * @return
     */
    private Room chooseSuitableRoom(Application application, List<Room> freeRooms) {

        Room suitableRoom = null;

        for (Room freeRoom : freeRooms) {
            if ((application.getRoomTypeBySeats().equals(freeRoom.getRoomTypeBySeats()))
                    && (application.getRoomClass().equals(freeRoom.getRoomClass()))) {
                return freeRoom;
            } else if (application.getRoomTypeBySeats().equals(freeRoom.getRoomTypeBySeats())) {
                return freeRoom;
            } else if (application.getRoomClass().equals(freeRoom.getRoomClass())) {
                return freeRoom;
            } else {
                suitableRoom = freeRoom;
            }

        }

        return suitableRoom;

    }
}
