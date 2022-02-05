package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.db.RoomDAO;
import com.epam.javacourse.hotel.model.Room;

import java.util.List;

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


}
