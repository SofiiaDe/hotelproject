package com.epam.javacourse.hotel.model.service.impl;

import com.epam.javacourse.hotel.db.dao.RoomDAO;
import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.Room;
import com.epam.javacourse.hotel.model.service.interfaces.IRoomService;
import com.epam.javacourse.hotel.shared.models.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RoomServiceImpl implements IRoomService {

    private static final Logger logger = LogManager.getLogger(RoomServiceImpl.class);

    private final RoomDAO roomDAO;
    private final int pageSize;

    public RoomServiceImpl(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
        this.pageSize = 3; // can put this in config;
    }

    @Override
    public List<Room> getAllRooms() throws AppException {
        try {
            return this.roomDAO.findAllRooms();
        } catch (DBException exception) {
            throw new AppException("Can't retrieve all rooms", exception);
        }
    }

    @Override
    public boolean updateRoom(Room room) throws AppException {
        try {
            return this.roomDAO.updateRoom(room);
        } catch (DBException exception) {
            throw new AppException("Can't update room", exception);
        }
    }

    @Override
    public Room getRoomById(int roomId) throws AppException {
        try {
            return this.roomDAO.findRoomById(roomId);
        } catch (DBException exception) {
            throw new AppException("Can't retrieve room by id", exception);
        }
    }

    @Override
    public List<Room> getRoomsByIds(List<Integer> ids) throws AppException {
        try {
            return this.roomDAO.findRoomsByIds(ids);
        } catch (DBException exception) {
            throw new AppException("Can't retrieve rooms by ids", exception);
        }
    }

    @Override
    public List<Room> getFreeRoomsForPeriod(LocalDate checkinDate, LocalDate checkoutDate) throws AppException {
        return getFreeRoomsForPeriod(checkinDate, checkoutDate, -1, pageSize);
    }

    @Override
    public List<Room> getFreeRoomsForPeriod(LocalDate checkinDate, LocalDate checkoutDate, int page, int pageSize) throws IllegalArgumentException, AppException {
        ensureDatesAreValid(checkinDate, checkoutDate);

        if (page < -1) {
            throw new IllegalArgumentException("Incorrect page");
        }

        try {
            return roomDAO.findAvailableRooms(checkinDate, checkoutDate, page, pageSize);
        } catch (DBException exception) {
            throw new AppException("Can't retrieve free rooms for the specified period", exception);
        }
    }

    @Override
    public List<Room> getRoomsForPeriod(LocalDate checkinDate, LocalDate checkoutDate, int page, int pageSize, SortBy sortBy,
                                        SortType sortType, RoomStatus roomStatus, RoomSeats roomSeats) throws AppException {
        ensureDatesAreValid(checkinDate, checkoutDate);

        try {
            return roomDAO.findRooms(checkinDate, checkoutDate, page, pageSize, sortBy, sortType, roomStatus, roomSeats);
        } catch (DBException exception) {
            throw new AppException("Can't retrieve free rooms for the specified period", exception);
        }
    }

    @Override
    public int getRoomsNumberForPeriod(LocalDate checkinDate, LocalDate checkoutDate, RoomStatus roomStatus, RoomSeats roomSeats) throws AppException {

        ensureDatesAreValid(checkinDate, checkoutDate);

        try {
            return roomDAO.findRoomCount(checkinDate, checkoutDate, roomStatus, roomSeats);
        } catch (DBException exception) {
            throw new AppException("Can't retrieve rooms number for the specified period", exception);
        }
    }

    /**
     * validates if checkin date is not after checkout date or checkout date is equal to checkin date
     * @param checkinDate
     * @param checkoutDate
     * @throws AppException in case of at least one of dates is incorrect
     */
    private void ensureDatesAreValid(LocalDate checkinDate, LocalDate checkoutDate) throws AppException {
        if (checkinDate.isAfter(checkoutDate) || checkoutDate.isEqual(checkinDate)) {
            throw new AppException("Check-in and check-out dates are overlapping or equal");

        }
    }


    @Override
    public Room chooseSuitableRoomForRequest(Application application, List<Room> freeRooms) {

        Room suitableRoom = null;

        try {
            for (Room freeRoom : freeRooms) {
                if ((application.getRoomTypeBySeats().equals(freeRoom.getRoomTypeBySeats()))
                        && (application.getRoomClass().equals(freeRoom.getRoomClass()))) {
                    return freeRoom;
                } else if (application.getRoomTypeBySeats().equals(freeRoom.getRoomTypeBySeats())) {
                    return freeRoom;
                } else if (application.getRoomClass().equals(freeRoom.getRoomClass())) {
                    return freeRoom;
                } else {
                    suitableRoom = freeRoom;
                }

            }

        } catch (Exception exception) {
            String errorMessage = "Can't select suitable room to make confirmation request for application with id=" + application.getId();
            logger.error(errorMessage, exception);
        }
        return suitableRoom;
    }

    @Override
    public List<String> getRoomTypeList() {
        return Arrays.stream(RoomSeats.values())
                .map(RoomSeats::name)
                .filter(type -> !type.equals("NONE"))
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getRoomClassList() {
        return Arrays.stream(RoomClass.values())
                .map(RoomClass::name)
                .filter(type -> !type.equals("NONE"))
                .map(String::toLowerCase)
                .collect(Collectors.toList());

    }

    @Override
    public List<RoomSeats> getRoomSeatsValues() {
        return Arrays.asList(RoomSeats.values());
    }

    @Override
    public List<RoomClass> getRoomClassValues() {
        return Arrays.asList(RoomClass.values());

    }

    @Override
    public void create(Room room) throws AppException {
        try {
            this.roomDAO.createRoom(room);
        } catch (DBException exception) {
            throw new AppException("Can't create room", exception);
        }
    }

    @Override
    public List<Integer> getRoomsNumbers() throws AppException{
        try {
            return this.roomDAO.findAllRoomNumbers();
        } catch (DBException exception) {
            throw new AppException("Can't get all rooms' numbers");
        }
    }

}
