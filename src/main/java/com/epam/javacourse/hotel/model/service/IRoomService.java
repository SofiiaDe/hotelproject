package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.db.DBConstatns;
import com.epam.javacourse.hotel.model.Room;

import java.time.LocalDateTime;
import java.util.List;

public interface IRoomService {

    List<Room> allRoomsList() throws DBException;

    boolean updateRoom(Room room) throws DBException;

    Room getRoomById(int roomId) throws  DBException;

    List<Room> getRoomsByIds(List<Integer> ids) throws DBException;

    List<Room> getCurrentlyFreeRooms() throws DBException;

    List<Room> getFreeRoomsForPeriod(LocalDateTime checkinDate, LocalDateTime checkoutDate) throws DBException;

}
