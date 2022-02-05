package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.db.DBConstatns;
import com.epam.javacourse.hotel.model.Room;

import java.util.List;

public interface IRoomService {

    List<Room> allRoomsList() throws DBException;

    boolean updateRoom(Room room) throws DBException;
}
