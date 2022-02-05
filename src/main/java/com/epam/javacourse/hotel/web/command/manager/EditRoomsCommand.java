package com.epam.javacourse.hotel.web.command.manager;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Room;
import com.epam.javacourse.hotel.model.service.IRoomService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class EditRoomsCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {


        IRoomService roomService = AppContext.getInstance().getRoomService();

        List<Room> rooms = roomService.allRoomsList();

        request.setAttribute("rooms", rooms);


        return Path.PAGE_MANAGER_ACCOUNT;
    }
}
