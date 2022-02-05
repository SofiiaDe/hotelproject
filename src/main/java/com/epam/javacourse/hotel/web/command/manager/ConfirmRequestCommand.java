package com.epam.javacourse.hotel.web.command.manager;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.*;
import com.epam.javacourse.hotel.model.service.IApplicationService;
import com.epam.javacourse.hotel.model.service.IConfirmRequestService;
import com.epam.javacourse.hotel.model.service.IRoomService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.common.LoginCommand;
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
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

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
            return Path.PAGE_LOGIN;
        }

        int id = Integer.parseInt((String) session.getAttribute("applicationId"));
        Application applicationToBeRequested = applicationService.getApplicationById(id);

        if (!freeRooms.isEmpty()) {
            ConfirmationRequest newConfirmationRequest = new ConfirmationRequest();
            newConfirmationRequest.setUserId(applicationToBeRequested.getUserId());
            newConfirmationRequest.setApplicationId(id);
            newConfirmationRequest.setRoomId(chooseSuitableRoom(applicationToBeRequested, freeRooms).getId());
            newConfirmationRequest.setConfirmRequestStatus("new");

            confirmRequestService.create(newConfirmationRequest);
        }

        return null;
    }

    /**
     * returns a Room which is the most suitable according to the Client's criteria specified in the application
     * @param application
     * @param freeRooms
     * @return
     */
    private Room chooseSuitableRoom(Application application, List<Room> freeRooms) throws DBException {

//        if (freeRooms.isEmpty()) {
//
//        }
        Room suitableRoom = null;

        for (int i = 0; i < freeRooms.size(); i++) {
            if ((application.getRoomTypeBySeats().equals(freeRooms.get(i).getRoomTypeBySeats()))
            && (application.getRoomClass().equals(freeRooms.get(i).getRoomClass()))) {
                return freeRooms.get(i);
            } else if (application.getRoomTypeBySeats().equals(freeRooms.get(i).getRoomTypeBySeats())) {
                return freeRooms.get(i);
            } else if (application.getRoomClass().equals(freeRooms.get(i).getRoomClass())) {
                return freeRooms.get(i);
            } else {
                suitableRoom = freeRooms.get(i);
            }


        }

        return suitableRoom;

    }
}
