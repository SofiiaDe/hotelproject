package com.epam.javacourse.hotel.web.command.manager;

import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.ConfirmationRequest;
import com.epam.javacourse.hotel.model.Room;
import com.epam.javacourse.hotel.model.service.interfaces.IApplicationService;
import com.epam.javacourse.hotel.model.service.interfaces.IConfirmRequestService;
import com.epam.javacourse.hotel.model.service.interfaces.IRoomService;
import com.epam.javacourse.hotel.utils.AppContext;
import com.epam.javacourse.hotel.utils.Validator;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import com.epam.javacourse.hotel.web.command.RedirectCommandResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

/**
 * Command to create confirmation request
 */
public class MakeConfirmRequestCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(MakeConfirmRequestCommand.class);

    IRoomService roomService = AppContext.getInstance().getRoomService();
    IApplicationService applicationService = AppContext.getInstance().getApplicationService();
    IConfirmRequestService confirmRequestService = AppContext.getInstance().getConfirmRequestService();

    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        HttpSession session = request.getSession();

        if (!"manager".equalsIgnoreCase((String) session.getAttribute("userRole"))) {
            logger.error("You do not have permission to create a confirmation request. " +
                    "Please login as a Manager.");
            return new AddressCommandResult(Path.PAGE_LOGIN);
        }

        String freeRoomAttrName = "freeRooms";

        var checkin = request.getParameter("checkin_date");
        var checkout = request.getParameter("checkout_date");

        LocalDate checkinDate = Validator.dateParameterToLocalDate(checkin, request);
        LocalDate checkoutDate = Validator.dateParameterToLocalDate(checkout, request);

        if (checkin == null || checkout == null){
            session.removeAttribute(freeRoomAttrName);
            return new AddressCommandResult(Path.PAGE_FREE_ROOMS);
        }

        List<Room> freeRoomsRequest;

        try{
            freeRoomsRequest = roomService.getFreeRoomsForPeriod(checkinDate, checkoutDate);
        }catch(AppException exception){
            String errorMessage = "Can't retrieve free rooms for period";
            logger.error(errorMessage, exception);
            request.setAttribute("errorMessage", "Can't retrieve rooms data");
            return new AddressCommandResult(Path.PAGE_ERROR);
        }

        String notification;

        if (freeRoomsRequest.isEmpty()) {
            notification = "There are no free rooms.";
            request.setAttribute("notification", notification);
            return new AddressCommandResult(Path.PAGE_MANAGER_ACCOUNT);
        }

        int id = Integer.parseInt(request.getParameter("applicationId"));

        Application applicationToBeRequested = applicationService.getApplicationById(id);

        ConfirmationRequest newConfirmationRequest = new ConfirmationRequest();
        newConfirmationRequest.setUserId(applicationToBeRequested.getUserId());
        newConfirmationRequest.setApplicationId(id);
        newConfirmationRequest.setRoomId(roomService.chooseSuitableRoomForRequest(applicationToBeRequested, freeRoomsRequest).getId());
        newConfirmationRequest.setConfirmRequestDate(LocalDate.now());
        newConfirmationRequest.setStatus("new");

        confirmRequestService.create(newConfirmationRequest);

        return new RedirectCommandResult(Path.COMMAND_MANAGER_ACCOUNT);
    }


}
