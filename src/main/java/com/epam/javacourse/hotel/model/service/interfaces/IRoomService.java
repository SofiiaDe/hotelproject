package com.epam.javacourse.hotel.model.service.interfaces;

import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.Room;
import com.epam.javacourse.hotel.shared.models.RoomSeats;
import com.epam.javacourse.hotel.shared.models.RoomStatus;
import com.epam.javacourse.hotel.shared.models.SortBy;
import com.epam.javacourse.hotel.shared.models.SortType;

import java.time.LocalDate;
import java.util.List;

/**
 * Room Service interface
 */
public interface IRoomService {

    List<Room> getAllRooms() throws AppException;

    boolean updateRoom(Room room) throws AppException;

    Room getRoomById(int roomId) throws  AppException;

    List<Room> getRoomsByIds(List<Integer> ids) throws AppException;

    /**
     *
     * @return list of all available rooms for defined period (notwithstanding the page)
     * @throws AppException
     */
    List<Room> getFreeRoomsForPeriod(LocalDate checkinDate, LocalDate checkoutDate) throws AppException;

    /**
     * @param page result's page. When page = -1 it means get all
     * @return list of rooms available for booking
     */
    List<Room> getFreeRoomsForPeriod(LocalDate checkinDate, LocalDate checkoutDate, int page, int pageSize) throws AppException;


    List<Room> getRoomsForPeriod(LocalDate checkinDate, LocalDate checkoutDate, int page, int pageSize, SortBy sortBy,
                                 SortType sortType, RoomStatus roomStatus, RoomSeats roomSeats) throws AppException;

    int getRoomsNumberForPeriod(LocalDate checkinDate, LocalDate checkoutDate, RoomStatus roomStatus, RoomSeats roomSeats) throws AppException;

    /**
     * finds the most suitable room according to the Client's criteria specified in the application
     *
     * @param application in which Client has specified roomType, roomClass, checkin and checkout dates
     * @param freeRooms list of rooms which are for a period specified in the application
     * @return Room
     */
    Room chooseSuitableRoomForRequest(Application application, List<Room> freeRooms);

    void create(Room room) throws AppException;

    /**
     * gets numbers of all rooms (desc order)
     * @return list of integers
     * @throws AppException
     */
    List<Integer> getRoomsNumbers() throws AppException;
}