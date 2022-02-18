package com.epam.javacourse.hotel.web.command.manager;

import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.model.Room;
import com.epam.javacourse.hotel.model.service.interfaces.IRoomService;
import com.epam.javacourse.hotel.utils.AppContext;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import com.epam.javacourse.hotel.web.command.RedirectCommandResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

public class AddRoomCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(AddRoomCommand.class);

    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        IRoomService roomService = AppContext.getInstance().getRoomService();

        String roomSeats = request.getParameter("roomSeats");
        String roomClass = request.getParameter("roomClass");
        int roomNumber = Integer.parseInt(request.getParameter("roomNumber"));

        String errorMessage;

        List<Integer> existingRoomNumbers = roomService.getRoomsNumbers();
        if (existingRoomNumbers.contains(roomNumber)) {
            errorMessage = "Room with such number already exists";
            logger.error(errorMessage);
            request.setAttribute("errorMessage", errorMessage);
            return new AddressCommandResult(Path.PAGE_ERROR);
        }

//        double price = Double.parseDouble(request.getParameter("price"));
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(request.getParameter("price")));
        int compareNotLower = price.compareTo(new BigDecimal("100"));
        int compareNotHigher = price.compareTo(new BigDecimal("1000"));
        if (compareNotLower < 0 && compareNotHigher > 0) {
            errorMessage = "Room price should correspond to the company's pricing policy.";
            logger.error(errorMessage);
            request.setAttribute("errorMessage", errorMessage);
            return new AddressCommandResult(Path.PAGE_ERROR);
        }

        String roomStatus = request.getParameter("roomStatus");

        Room newRoom = new Room();
        newRoom.setPrice(price);
        newRoom.setRoomNumber(roomNumber);
        newRoom.setRoomTypeBySeats(roomSeats);
        newRoom.setRoomClass(roomClass);
        newRoom.setRoomStatus(roomStatus);
        roomService.create(newRoom);

        return new RedirectCommandResult(Path.COMMAND_MANAGER_ACCOUNT); //successful submission ==>);
    }
}
