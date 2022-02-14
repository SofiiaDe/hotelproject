package com.epam.javacourse.hotel.db;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Room;
import com.epam.javacourse.hotel.shared.models.RoomSeats;
import com.epam.javacourse.hotel.shared.models.RoomStatus;
import com.epam.javacourse.hotel.shared.models.SortBy;
import com.epam.javacourse.hotel.shared.models.SortType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoomDAO {

    private static final Logger logger = LogManager.getLogger(RoomDAO.class);

    public List<Room> findAllRooms() throws DBException {

        List<Room> allRoomsList = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getInstance().getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(DBConstatns.SQL_GET_ALL_ROOMS);
            fillRoomsFromDb(allRoomsList, rs);
        } catch (SQLException e) {
            throw new DBException("Can't find all rooms", e);
        } finally {
            close(con);
            close(stmt);
            close(rs);
        }

        return allRoomsList;
    }

    public boolean updateRoom(Room room) throws DBException {

        boolean roomUpdated;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(DBConstatns.SQL_UPDATE_ROOM);
            pstmt.setDouble(1, room.getPrice());
            pstmt.setInt(2, room.getRoomNumber());
            pstmt.setString(3, room.getRoomTypeBySeats());
            pstmt.setString(4, room.getRoomClass());
            pstmt.setInt(6, room.getId());
            roomUpdated = pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            String errorMessage = "Can't update room with id=" + room.getId();
            logger.error(errorMessage, e);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(pstmt);
        }
        return roomUpdated;
    }

    public Room findRoomById(int roomId) throws DBException {

        Room room = new Room();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getInstance().getConnection();
            pStmt = con.prepareStatement(DBConstatns.SQL_GET_ROOM_BY_ID);
            pStmt.setInt(1, roomId);

            rs = pStmt.executeQuery();
            while (rs.next()) {
                room.setId(roomId);
                mapCommonProperties(room, rs);
            }

        } catch (SQLException e) {
            String errorMessage = "Can't find room by id";
            logger.error(errorMessage, e);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(pStmt);
            close(rs);
        }
        return room;
    }

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
    private List<Room> getRoomsByIdsToIncludeOrExclude(List<Integer> ids, boolean include, boolean onlyAvailable) throws DBException {
        List<Room> rooms = new ArrayList<>();

        String query;
        if (include) {
            query = DBConstatns.SQL_GET_ROOMS_BY_IDS;
        } else {
            query = DBConstatns.SQL_GET_ROOMS_EXCEPT;
        }

        if (onlyAvailable) {
            query += " AND (room_status is unknown OR room_status = 'available')";
        }

        String sql = String.format(query, preparePlaceHolders(ids.size()));
        Connection con = null;
        PreparedStatement pStmt = null;

        try {
            con = DBManager.getInstance().getConnection();
            pStmt = con.prepareStatement(sql);

            setValuesInPreparedStatement(pStmt, ids.toArray());

            try (ResultSet resultSet = pStmt.executeQuery()) {
                while (resultSet.next()) {
                    rooms.add(mapResultSetToRoom(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DBException("Cannot get rooms by ids", e);
        } finally {
            close(con);
            close(pStmt);
        }

        return rooms;
    }

    /**
     * Get available rooms by ids
     *
     * @param ids
     * @return
     * @throws DBException
     */
    public List<Room> getRoomsByIds(List<Integer> ids) throws DBException {
        return getRoomsByIdsToIncludeOrExclude(ids, true, true);
    }

    public List<Room> getRooms(LocalDate checkin, LocalDate checkout, int page, int pageSize, SortBy sortBy, SortType sortType,
                               RoomStatus roomStatus, RoomSeats roomSeats) throws DBException {
        List<Room> allRoomsList = new ArrayList<>();

        String sql = createRoomsQuery("*", roomStatus, roomSeats, page, pageSize, sortBy, sortType);

        executeGetRoomQuery(checkin, checkout, allRoomsList, sql);

        return allRoomsList;
    }

    private void executeGetRoomQuery(LocalDate checkin, LocalDate checkout, List<Room> allRoomsList, String sql) throws DBException {
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        try {
            con = DBManager.getInstance().getConnection();
            pStmt = con.prepareStatement(sql);
            pStmt.setDate(1, Date.valueOf(checkout));
            pStmt.setDate(2, Date.valueOf(checkin));

            rs = pStmt.executeQuery();
            fillRoomsFromDb(allRoomsList, rs);

        } catch (SQLException e) {
            throw new DBException("Can't get all rooms", e);
        } finally {
            close(con);
            close(pStmt);
            close(rs);
        }
    }

    public List<Room> getAvailableRooms(LocalDate checkin, LocalDate checkout, int page, int pageSize) throws DBException {

        List<Room> allRoomsList = new ArrayList<>();

        String sql = DBConstatns.SQL_GET_AVAILABLE_ROOMS;
        sql = Helpers.enrichWithPageSizeStatement(page, pageSize, sql);

        executeGetRoomQuery(checkin, checkout, allRoomsList, sql);

        return allRoomsList;
    }

    private void fillRoomsFromDb(List<Room> allRoomsList, ResultSet rs) throws SQLException {
        while (rs.next()) {
            Room room = new Room();
            room.setId(rs.getInt("id"));
            room.setPrice(rs.getBigDecimal("price").doubleValue());
            room.setRoomNumber(rs.getInt("room_number"));
            room.setRoomTypeBySeats(rs.getString("room_seats"));
            room.setRoomClass(rs.getString("room_class"));
            allRoomsList.add(room);
        }
    }

    private String createRoomsQuery(String select, RoomStatus roomStatus, RoomSeats roomSeats) {
        return createRoomsQuery(select, roomStatus, roomSeats, -1, -1, SortBy.NONE, SortType.NONE);
    }

    private String createRoomsQuery(String select, RoomStatus roomStatus, RoomSeats roomSeats, int page, int pageSize, SortBy sortBy, SortType sortType) {

        String result = DBConstatns.SQL_GET_ROOMS_BASIC_QUERY;
        result = result.replace("?0?", select + " ");

        switch (roomStatus) {
            case RESERVED:
                result = result.replace("?1?", "= 'new' ");
                result = result.replace("?2?", "not");
                break;
            case BOOKED:
                result = result.replace("?1?", "= 'paid' ");
                result = result.replace("?2?", "not");
                break;
            case UNAVAILABLE:
                result = result.replace("?1?", "!= 'cancelled' ");
                result += "and room_status = 'unavailable' ";
                result = result.replace("?2?", "");
                break;

            default:
                result = result.replace("?1?", "!= 'cancelled'");
                result += "and room_status = 'available' ";
                result = result.replace("?2?", "");
        }

        if (roomSeats != null && roomSeats != RoomSeats.NONE) {
            result += " and room_seats = '" + roomSeats + "'";
        }

        if (sortBy != null && sortBy != SortBy.NONE) {
            result += " ORDER BY " + (sortBy == SortBy.CLASS ? "room_class" : sortBy) + " ";
            if (sortType != null && sortType != SortType.NONE) {
                result += sortType;
            }
        }

        if (page > 0) {
            result += " LIMIT " + pageSize;
            result += page > 1 ? " OFFSET " + (page - 1) * pageSize : " ";
        }

        return result;
    }

    public int getRoomCount(LocalDate checkin, LocalDate checkout, RoomStatus roomStatus, RoomSeats roomSeats) throws DBException {

        int result;
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        String sql = createRoomsQuery("count(*) as cnt", roomStatus, roomSeats);

        try {
            con = DBManager.getInstance().getConnection();
            pStmt = con.prepareStatement(sql);
            pStmt.setDate(1, Date.valueOf(checkout));
            pStmt.setDate(2, Date.valueOf(checkin));

            rs = pStmt.executeQuery();
            rs.next();
            result = rs.getInt("cnt");

        } catch (SQLException e) {
            String errorMessage = "Can't get all rooms number";
            logger.error(errorMessage, e);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(pStmt);
            close(rs);
        }

        return result;
    }

    public int getAvailableRoomCount(LocalDate checkin, LocalDate checkout) throws DBException {
        return getRoomCount(checkin, checkout, RoomStatus.AVAILABLE, null);
    }

    private static Room mapResultSetToRoom(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setId(rs.getInt("id"));
        mapCommonProperties(room, rs);

        return room;
    }

    private static void mapCommonProperties(Room room, ResultSet rs) throws SQLException {
        room.setPrice(rs.getBigDecimal("price").doubleValue());
        room.setRoomNumber(rs.getInt("room_number"));
        room.setRoomTypeBySeats(rs.getString("room_seats"));
        room.setRoomClass(rs.getString("room_class"));
    }

    // todo code duplication
    private static String preparePlaceHolders(int length) {
        return String.join(",", Collections.nCopies(length, "?"));
    }

    // todo code duplication
    private static void setValuesInPreparedStatement(PreparedStatement preparedStatement, Object... values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            preparedStatement.setObject(i + 1, values[i]);
        }
    }

    private static void close(AutoCloseable itemToBeClosed) {
        if (itemToBeClosed != null) {
            try {
                itemToBeClosed.close();
            } catch (Exception e) {
                logger.error("DB close failed in RoomDAO", e);
            }
        }
    }

    private static void rollBack(Connection con) {
        if (con != null) {
            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("Cannot rollback transaction in RoomDAO", e);
            }
        }
    }

}
