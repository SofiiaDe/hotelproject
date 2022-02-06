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
import java.util.List;
import java.util.stream.Collectors;

public class FreeRoomsPageCommand implements ICommand {
    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        IRoomService roomService = AppContext.getInstance().getRoomService();
        List<Room> allRoomsList = roomService.allRoomsList();

        List<Room> freeRooms = allRoomsList
                .stream()
                .filter(status -> status.getRoomStatus().equals("free"))
                .collect(Collectors.toList());

        HttpSession session = request.getSession();
        session.setAttribute("freeRooms", freeRooms);

        return new AddressCommandResult(Path.PAGE_FREE_ROOMS);

    }
}
