package com.epam.javacourse.hotel.db;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoomDAO {

    private static final Logger logger = LogManager.getLogger(RoomDAO.class);

    public List<Room> getAllRooms() throws DBException {

        List<Room> allRoomsList = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getInstance().getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(DBConstatns.SQL_GET_ALL_ROOMS);
            while (rs.next()) {
                Room room = new Room();
                room.setId(rs.getInt("id"));
                room.setPrice(rs.getBigDecimal("price").doubleValue());
                room.setRoomNumber(rs.getInt("room_number"));
                room.setRoomTypeBySeats(rs.getString("room_seats"));
                room.setRoomClass(rs.getString("room_class"));
                allRoomsList.add(room);
            }
        } catch (SQLException e) {
            logger.error("Cannot get all rooms", e);
            throw new DBException("Cannot get all rooms", e);
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
            logger.error("Cannot update room with id={}", room.getId(), e);
            throw new DBException("Cannot update room.", e);
        } finally {
            close(con);
            close(pstmt);
        }
        return roomUpdated;
    }

    public Room getRoomById(int roomId) throws DBException {

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
//                room.setPrice(rs.getDouble("price"));
//                room.setRoomNumber(rs.getInt("room_number"));
//                room.setRoomTypeBySeats(rs.getString("room_seats"));
//                room.setRoomClass(rs.getString("room_class"));
            }

        } catch (SQLException e) {
            logger.error("Cannot get room by id", e);
            throw new DBException("Cannot get room by id", e);
        } finally {
            close(con);
            close(pStmt);
            close(rs);
        }
        return room;
    }

    public List<Room> getAvailableRoomsExcept(List<Integer> roomsToExclude) throws DBException {
        if (roomsToExclude == null || roomsToExclude.size() == 0){
            return getAllRooms();
        }
        return getRoomsByIdsToIncludeOrExclude(roomsToExclude, false, true);
    }

    private List<Room> getRoomsByIdsToIncludeOrExclude(List<Integer> ids, boolean include, boolean onlyAvailable) throws DBException{
        List<Room> rooms = new ArrayList<>();

        String query;
        if (include){
            query = DBConstatns.SQL_GET_ROOMS_BY_IDS;
        }else{
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
            logger.error("Cannot get rooms by ids", e);
            throw new DBException("Cannot get rooms by ids", e);
        } finally {
            close(con);
            close(pStmt);
        }

        return rooms;
    }

    public List<Room> getRoomsByIds(List<Integer> ids) throws DBException {
        return getRoomsByIdsToIncludeOrExclude(ids, true, true);
    }

    private static Room mapResultSetToRoom(ResultSet rs) throws SQLException{
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
