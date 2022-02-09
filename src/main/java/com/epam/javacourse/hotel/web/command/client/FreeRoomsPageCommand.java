package com.epam.javacourse.hotel.web.command.client;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Room;
import com.epam.javacourse.hotel.model.service.IRoomService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

public class FreeRoomsPageCommand implements ICommand {

    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        IRoomService roomService = AppContext.getInstance().getRoomService();

        HttpSession session = request.getSession();
        String freeRoomAttrName = "freeRooms";

        var checkin = request.getParameter("checkin_date");
        var checkout = request.getParameter("checkout_date");

        if (checkin == null || checkout == null){
            session.removeAttribute(freeRoomAttrName);
            return new AddressCommandResult(Path.PAGE_FREE_ROOMS);
        }

        LocalDate checkinDate = LocalDate.parse(checkin);
        LocalDate checkoutDate = LocalDate.parse(checkout);

        List<Room> freeRooms = roomService.getFreeRoomsForPeriod(checkinDate, checkoutDate);
        session.setAttribute(freeRoomAttrName, freeRooms);

        return new AddressCommandResult(Path.PAGE_FREE_ROOMS);
    }
}
