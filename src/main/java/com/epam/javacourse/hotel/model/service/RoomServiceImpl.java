package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.AppException;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.IAppConfigurationManager;
import com.epam.javacourse.hotel.db.RoomDAO;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.Room;

import java.time.LocalDate;
import java.util.List;

public class RoomServiceImpl implements IRoomService {

    private final RoomDAO roomDAO;
    private final int pageSize;

    public RoomServiceImpl(RoomDAO roomDAO) {
        IAppConfigurationManager appConfigurationManager = AppContext.getInstance().getAppConfigurationManager();

        this.roomDAO = roomDAO;
        this.pageSize = appConfigurationManager.getDefaultPageSize();
    }

    @Override
    public List<Room> allRoomsList() throws DBException {
        return this.roomDAO.getAllRooms();
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

        if (page < -1){
            throw new IllegalArgumentException("Incorrect page");
        }

        try{
            return roomDAO.getAvailableRooms(checkinDate, checkoutDate, page, pageSize);
        }catch(DBException exception){
            throw new AppException(exception);
        }
    }

    @Override
    public int getFreeRoomsNumberForPeriod(LocalDate checkinDate, LocalDate checkoutDate) throws DBException{

        ensureDatesAreValid(checkinDate, checkoutDate);

        return roomDAO.getAvailableRoomNumber(checkinDate, checkoutDate);
    }

    private void ensureDatesAreValid(LocalDate checkinDate, LocalDate checkoutDate) {
        if (checkinDate.isAfter(checkoutDate) || checkoutDate.isEqual(checkinDate)) {
            throw new IllegalArgumentException("Check-in and check-out dates are overlapping or equal");
        }
    }

    /**
     * returns a Room which is the most suitable according to the Client's criteria specified in the application
     *
     * @param application
     * @param freeRooms
     * @return
     */
    @Override
    public Room chooseSuitableRoomForRequest(Application application, List<Room> freeRooms) throws DBException {

        Room suitableRoom = null;

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

        return suitableRoom;

    }
}
