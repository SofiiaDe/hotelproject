package com.epam.javacourse.hotel.web.command.client;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Room;
import com.epam.javacourse.hotel.model.service.IBookingService;
import com.epam.javacourse.hotel.model.service.IRoomService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FreeRoomsPageCommand implements ICommand {
    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        IRoomService roomService = AppContext.getInstance().getRoomService();
        IBookingService bookingService = AppContext.getInstance().getBookingService();

        List<Room> allRoomsList = roomService.allRoomsList();

        // get list of rooms with status "booked"
        List<Booking> allBookings = bookingService.getAllBookings();

        List<Integer> bookedRoomsId = allBookings.stream()
                .map(Booking::getRoomId)
                .collect(Collectors.toList());

        List<Room> bookedRooms = roomService.getRoomsByIds(bookedRoomsId);

        List<Room> freeRooms = allRoomsList.stream()
                .filter(room -> !bookedRooms.contains(room))
                .collect(Collectors.toList());

        // + occupied + unavailable

        HttpSession session = request.getSession();
        session.setAttribute("freeRooms", freeRooms);

        return new AddressCommandResult(Path.PAGE_FREE_ROOMS);

    }
}
