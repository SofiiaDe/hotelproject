package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.AppException;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.Room;
import com.epam.javacourse.hotel.shared.models.RoomSeats;
import com.epam.javacourse.hotel.shared.models.RoomStatus;
import com.epam.javacourse.hotel.shared.models.SortBy;
import com.epam.javacourse.hotel.shared.models.SortType;

import java.time.LocalDate;
import java.util.List;

public interface IRoomService {

    List<Room> getAllRooms() throws AppException;

    boolean updateRoom(Room room) throws AppException;

    Room getRoomById(int roomId) throws  AppException;

    List<Room> getRoomsByIds(List<Integer> ids) throws AppException;

    List<Room> getFreeRoomsForPeriod(LocalDate checkinDate, LocalDate checkoutDate) throws AppException;

    List<Room> getFreeRoomsForPeriod(LocalDate checkinDate, LocalDate checkoutDate, int page, int pageSize) throws AppException;

    List<Room> getRoomsForPeriod(LocalDate checkinDate, LocalDate checkoutDate, int page, int pageSize, SortBy sortBy,
                                 SortType sortType, RoomStatus roomStatus, RoomSeats roomSeats) throws AppException;

    int getRoomsNumberForPeriod(LocalDate checkinDate, LocalDate checkoutDate, RoomStatus roomStatus, RoomSeats roomSeats) throws AppException;

    /**
     * returns a Room which is the most suitable according to the Client's criteria specified in the application
     *
     * @param application
     * @param freeRooms
     * @return
     */
    Room chooseSuitableRoomForRequest(Application application, List<Room> freeRooms);
}