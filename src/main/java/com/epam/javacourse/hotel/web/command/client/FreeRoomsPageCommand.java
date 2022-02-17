package com.epam.javacourse.hotel.web.command.client;

import com.epam.javacourse.hotel.utils.AppContext;
import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.utils.Validator;
import com.epam.javacourse.hotel.model.Room;
import com.epam.javacourse.hotel.model.service.interfaces.IRoomService;
import com.epam.javacourse.hotel.shared.models.RoomSeats;
import com.epam.javacourse.hotel.shared.models.RoomStatus;
import com.epam.javacourse.hotel.shared.models.SortBy;
import com.epam.javacourse.hotel.shared.models.SortType;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import com.epam.javacourse.hotel.web.command.helpers.Helpers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Command to get list of rooms available for booking.
 */
public class FreeRoomsPageCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(FreeRoomsPageCommand.class);

    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) {

        IRoomService roomService = AppContext.getInstance().getRoomService();

        String checkin = request.getParameter("checkin_date");
        String checkout = request.getParameter("checkout_date");
        
        if (checkin == null || checkout == null){
            return new AddressCommandResult(Path.PAGE_FREE_ROOMS);
        }

        int page = Helpers.parsePage(request);

        LocalDate checkinDate = Validator.dateParameterToLocalDate(checkin, request);
        LocalDate checkoutDate = Validator.dateParameterToLocalDate(checkout, request);

        int totalFreeRooms;
        List<Room> freeRooms;
        int pageCount;

        RoomStatus roomStatus = RoomStatus.fromString(request.getParameter("roomStatus"));
        RoomSeats roomSeats = RoomSeats.fromString(request.getParameter("roomSeats"));

        try{
            totalFreeRooms = roomService.getRoomsNumberForPeriod(checkinDate, checkoutDate, roomStatus, roomSeats);

            int pageSize = AppContext.getInstance().getDefaultPageSize();
            pageCount = (int) Math.ceil((float)totalFreeRooms / pageSize);

            boolean toGetRooms = totalFreeRooms > 0 && page <= pageCount;

            SortBy sortBy = SortBy.fromString(request.getParameter("sortBy"));
            SortType sortType = SortType.fromString(request.getParameter("sortType"));

            freeRooms = toGetRooms ?
                    roomService.getRoomsForPeriod(checkinDate, checkoutDate, page, pageSize, sortBy, sortType, roomStatus, roomSeats) :
                    new ArrayList<>();

        }catch (AppException exception){
            logger.error("Can't retrieve total free rooms", exception);
            request.setAttribute("errorMessage", "Can't retrieve rooms data");
            return new AddressCommandResult(Path.PAGE_ERROR);
        }

        request.setAttribute("freeRooms", freeRooms);
        request.setAttribute("page", page);
        request.setAttribute("pageCount", pageCount);
        request.setAttribute("checkin", checkin);
        request.setAttribute("checkout", checkout);
        if (roomStatus != RoomStatus.NONE){
            request.setAttribute("roomStatus", roomStatus.getText());
        }
        if (roomSeats != RoomSeats.NONE){
            request.setAttribute("roomSeats", roomSeats.getText());
        }

        return new AddressCommandResult(Path.PAGE_FREE_ROOMS);
    }

}
