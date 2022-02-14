package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.Exception.AppException;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.db.RoomDAO;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.Room;
import com.epam.javacourse.hotel.shared.models.RoomSeats;
import com.epam.javacourse.hotel.shared.models.RoomStatus;
import com.epam.javacourse.hotel.shared.models.SortBy;
import com.epam.javacourse.hotel.shared.models.SortType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;

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
            return this.roomDAO.getRoomsByIds(ids);
        } catch (DBException exception) {
            throw new AppException("Can't retrieve rooms by ids", exception);
        }
    }

    @Override
    public List<Room> getFreeRoomsForPeriod(LocalDate checkinDate, LocalDate checkoutDate) throws AppException {
        return getFreeRoomsForPeriod(checkinDate, checkoutDate, -1, pageSize);
    }

    /**
     * @param page result's page. When page = -1 it means get all
     * @return list of rooms available for booking
     */
    @Override
    public List<Room> getFreeRoomsForPeriod(LocalDate checkinDate, LocalDate checkoutDate, int page, int pageSize) throws IllegalArgumentException, AppException {
        ensureDatesAreValid(checkinDate, checkoutDate);

        if (page < -1) {
            throw new IllegalArgumentException("Incorrect page");
        }

        try {
            return roomDAO.getAvailableRooms(checkinDate, checkoutDate, page, pageSize);
        } catch (DBException exception) {
            throw new AppException("Can't retrieve free rooms for the specified period", exception);
        }
    }

    @Override
    public List<Room> getRoomsForPeriod(LocalDate checkinDate, LocalDate checkoutDate, int page, int pageSize, SortBy sortBy,
                                        SortType sortType, RoomStatus roomStatus, RoomSeats roomSeats) throws AppException {
        ensureDatesAreValid(checkinDate, checkoutDate);

        try {
            return roomDAO.getRooms(checkinDate, checkoutDate, page, pageSize, sortBy, sortType, roomStatus, roomSeats);
        } catch (DBException exception) {
            throw new AppException("Can't retrieve free rooms for the specified period", exception);
        }
    }

    @Override
    public int getRoomsNumberForPeriod(LocalDate checkinDate, LocalDate checkoutDate, RoomStatus roomStatus, RoomSeats roomSeats) throws AppException {

        ensureDatesAreValid(checkinDate, checkoutDate);

        try {
            return roomDAO.getRoomCount(checkinDate, checkoutDate, roomStatus, roomSeats);
        } catch (DBException exception) {
            throw new AppException("Can't retrieve rooms number for the specified period", exception);
        }
    }

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
}
