package com.epam.javacourse.hotel.db.interfaces;

import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.Room;
import com.epam.javacourse.hotel.shared.models.RoomSeats;
import com.epam.javacourse.hotel.shared.models.RoomStatus;
import com.epam.javacourse.hotel.shared.models.SortBy;
import com.epam.javacourse.hotel.shared.models.SortType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface IRoomDAO {

    boolean createRoom(Room room) throws DBException;

    List<Room> findAllRooms() throws DBException;

    boolean updateRoom(Room room) throws DBException;

    Room findRoomById(int roomId) throws DBException;

    /**
     * Get list of rooms by its ids
     *
     * @param ids           list of rooms ids
     * @param include       true if "ids" param is list of ids which rooms are requested and
     *                      false if "ids" param is list of ids except which rooms are requested
     * @param onlyAvailable true if only available rooms are requested
     * @return
     * @throws DBException
     */
    List<Room> findRoomsByIdsToIncludeOrExclude(List<Integer> ids, boolean include, boolean onlyAvailable) throws DBException;

    /**
     * Get available rooms by ids
     *
     * @param ids
     * @return
     * @throws DBException
     */
    List<Room> findRoomsByIds(List<Integer> ids) throws DBException;

    List<Room> findRooms(LocalDate checkin, LocalDate checkout, int page, int pageSize, SortBy sortBy, SortType sortType,
                         RoomStatus roomStatus, RoomSeats roomSeats) throws DBException;

    /**
     *
     * @param checkin
     * @param checkout
     * @param allRoomsList
     * @param sql
     * @throws DBException
     */
    void executeGetRoomQuery(LocalDate checkin, LocalDate checkout, List<Room> allRoomsList, String sql) throws DBException;

    /**
     * Find all available rooms considering pagination
     * @param checkin
     * @param checkout
     * @param page result's page
     * @param pageSize as configured
     * @return
     * @throws DBException
     */
    List<Room> findAvailableRooms(LocalDate checkin, LocalDate checkout, int page, int pageSize) throws DBException;

    /**
     * Sets to Room object parameters retrieved from DB as a resultSet. Then add this object to given list.
     * @param allRoomsList
     * @param rs
     * @throws SQLException
     */
    void fillRoomsFromDb(List<Room> allRoomsList, ResultSet rs) throws SQLException;

    String createRoomsQuery(String select, RoomStatus roomStatus, RoomSeats roomSeats);

    /**
     * Creates SQL-query to provide sorting along with pagination
     * @param select
     * @param roomStatus
     * @param roomSeats
     * @param page
     * @param pageSize
     * @param sortBy
     * @param sortType
     * @return
     */
    String createRoomsQuery(String select, RoomStatus roomStatus, RoomSeats roomSeats, int page, int pageSize, SortBy sortBy, SortType sortType);

    int findRoomCount(LocalDate checkin, LocalDate checkout, RoomStatus roomStatus, RoomSeats roomSeats) throws DBException;

    int findAvailableRoomCount(LocalDate checkin, LocalDate checkout) throws DBException;

    List<Integer> findAllRoomNumbers() throws DBException;
}