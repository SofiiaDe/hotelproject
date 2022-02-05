package com.epam.javacourse.hotel.db;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
            while (rs.next()) {
                Room room = new Room();
                room.setId(rs.getInt("id"));
                room.setPrice(rs.getBigDecimal("price").doubleValue());
                room.setRoomNumber(rs.getInt("room_number"));
                room.setRoomTypeBySeats(rs.getString("room_seats"));
                room.setRoomClass(rs.getString("room_class"));
                room.setRoomStatus(rs.getString("room_status"));
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
            pstmt.setString(5, room.getRoomStatus());
            pstmt.setInt(6, room.getId());
            roomUpdated = pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.error("Cannot update status of room with id=" + room.getId(), e);
            throw new DBException("Cannot update status of room with id=" + room.getId(), e);
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
                room.setPrice(rs.getDouble("price"));
                room.setRoomNumber(rs.getInt("room_number"));
                room.setRoomTypeBySeats(rs.getString("room_seats"));
                room.setRoomClass(rs.getString("room_class"));
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
