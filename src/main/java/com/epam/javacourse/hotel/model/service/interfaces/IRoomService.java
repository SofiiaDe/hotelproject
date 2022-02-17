package com.epam.javacourse.hotel.model.service.interfaces;

import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.Room;
import com.epam.javacourse.hotel.shared.models.*;

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

    List<String> getRoomTypeList();

    List<String> getRoomClassList();

    List<RoomSeats> getRoomSeatsValues();

    List<RoomClass> getRoomClassValues();

    void create(Room room) throws AppException;

    List<Integer> getRoomsNumbers() throws AppException;
}