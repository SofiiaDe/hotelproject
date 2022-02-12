package com.epam.javacourse.hotel.web.command.client;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.AppException;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.Validator;
import com.epam.javacourse.hotel.model.Room;
import com.epam.javacourse.hotel.model.service.IRoomService;
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
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

        String freeRoomAttrName = "freeRooms";

        String checkin = request.getParameter("checkin_date");
        String checkout = request.getParameter("checkout_date");
        
        if (checkin == null || checkout == null){
            return new AddressCommandResult(Path.PAGE_FREE_ROOMS);
        }

        int page = parsePage(request);

        LocalDate checkinDate = Validator.dateParameterToLocalDate(checkin);
        LocalDate checkoutDate = Validator.dateParameterToLocalDate(checkout);

        int totalFreeRooms;
        List<Room> freeRooms;
        int pageCount;

        try{
            totalFreeRooms = roomService.getFreeRoomsNumberForPeriod(checkinDate, checkoutDate);

            int pageSize = 3; // can put this in config
            pageCount = (int) Math.ceil((float)totalFreeRooms / pageSize);

            boolean toGetRooms = totalFreeRooms > 0 && page <= pageCount;

            freeRooms = toGetRooms ?
                    roomService.getFreeRoomsForPeriod(checkinDate, checkoutDate, page, pageSize) :
                    new ArrayList<>();

        }catch (AppException exception){
            logger.error("Can't retrieve total free rooms", exception);
            request.setAttribute("errorMessage", "Can't retrieve rooms data");
            return new AddressCommandResult(Path.PAGE_ERROR);
        }

        request.setAttribute(freeRoomAttrName, freeRooms);
        request.setAttribute("page", page);
        request.setAttribute("pageCount", pageCount);
        request.setAttribute("checkin", checkin);
        request.setAttribute("checkout", checkout);

        return new AddressCommandResult(Path.PAGE_FREE_ROOMS);
    }

    private static int parsePage(HttpServletRequest request){
        String pageParam = request.getParameter("page");
        int page = pageParam != null ? Integer.parseInt(pageParam) : 1;
        return page <= 0 ? 1: page;
    }
}
