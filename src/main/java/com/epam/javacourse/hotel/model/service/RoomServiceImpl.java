package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.db.RoomDAO;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Room;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoomServiceImpl implements IRoomService{

    private final RoomDAO roomDAO;

    public RoomServiceImpl(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }

    @Override
    public List<Room> allRoomsList() throws DBException {
        return this.roomDAO.findAllRooms();
    }

    @Override
    public boolean updateRoom(Room room) throws DBException {
        return this.roomDAO.updateRoom(room);
    }

    @Override
    public Room getRoomById(int roomId) throws DBException {
        return this.roomDAO.getRoomById(roomId);
    }

    @Override
    public List<Room> getRoomsByIds(List<Integer> ids) throws DBException {
        return this.roomDAO.getRoomsByIds(ids);
    }

    @Override
    public List<Room> getCurrentlyFreeRooms() throws DBException {
        IBookingService bookingService = AppContext.getInstance().getBookingService();
        IRoomService roomService = AppContext.getInstance().getRoomService();

        List<Room> allRoomsList = roomService.allRoomsList();

        // get list of booked rooms
        List<Booking> allBookings = bookingService.getAllBookings();

        List<Integer> bookedRoomsId = allBookings.stream()
                .map(Booking::getRoomId)
                .collect(Collectors.toList());

        List<Room> bookedRooms = roomService.getRoomsByIds(bookedRoomsId);

        // + occupied + unavailable

        // get list of free rooms
        List<Room> freeRooms = allRoomsList.stream()
                .filter(room -> !bookedRooms.contains(room))
//                .filter(room -> !occupiedRooms.contains(room))
                .collect(Collectors.toList());


        return freeRooms;
    }


    /**
     *
     * @param checkinDate
     * @param checkoutDate
     * @return list of rooms which are available for the period from checkinDate to checkoutDate
     * @throws DBException
     */
    @Override
    public List<Room> getFreeRoomsForPeriod(LocalDateTime checkinDate, LocalDateTime checkoutDate) throws DBException {

        IBookingService bookingService = AppContext.getInstance().getBookingService();
        IRoomService roomService = AppContext.getInstance().getRoomService();

        List<Room> allRoomsList = roomService.allRoomsList();

        // get list of booked rooms
        List<Booking> allBookings = bookingService.getAllBookings();

        List<Integer> availableRoomsId = new ArrayList<>();
        for (Booking booking: allBookings) {
            if((checkinDate.isAfter(booking.getCheckoutDate()) ||
                    checkoutDate.isAfter(booking.getCheckinDate()))) {
                availableRoomsId.add(booking.getId());
            }
        }

        List<Room> availableRooms = roomService.getRoomsByIds(availableRoomsId);

        // unavailable

        // get list of free rooms
        List<Room> freeRooms = allRoomsList.stream()
                .filter(room -> !availableRooms.contains(room))
//                .filter(room -> !occupiedRooms.contains(room))
                .collect(Collectors.toList());


        return freeRooms;
    }


}
