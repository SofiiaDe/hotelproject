package com.epam.javacourse.hotel.db;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    private static final Logger logger = LogManager.getLogger(BookingDAO.class);

    public boolean createBooking(Booking booking) throws DBException {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(DBConstatns.SQL_CREATE_BOOKING);
            pstmt.setInt(1, booking.getUserId());
            pstmt.setTimestamp(2, Timestamp.valueOf(booking.getCheckinDate()));
            pstmt.setTimestamp(3, Timestamp.valueOf(booking.getCheckoutDate()));
            pstmt.setInt(4, booking.getRoomId());
            pstmt.setInt(5, booking.getApplicationId());

            pstmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            logger.error("Cannot create a booking", e);
            rollBack(con);
            throw new DBException("Cannot create a booking", e);
        } finally {
            close(con);
            close(pstmt);
        }
    }

    public List<Booking> findBookingsByUserId(int userId) throws DBException{
        List<Booking> userBookings = new ArrayList<>();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getInstance().getConnection();
            pStmt = con.prepareStatement(DBConstatns.SQL_GET_BOOKINGS_BY_USER_ID);
            pStmt.setInt(1, userId);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setUserId(userId);
                booking.setCheckinDate(LocalDateTime.parse(rs.getString("checkin_date")));
                booking.setCheckoutDate(LocalDateTime.parse(rs.getString("checkout_date")));
                booking.setRoomId(rs.getInt("room_id"));
                booking.setApplicationId(rs.getInt("application_id"));
                userBookings.add(booking);
            }

        } catch (SQLException e) {
            logger.error("Cannot get bookings by user_id", e);
            throw new DBException("Cannot get bookings by user_id", e);
        } finally {
            close(con);
            close(pStmt);
            close(rs);
        }
        return userBookings;
    }

    private static void close(AutoCloseable itemToBeClosed) {
        if (itemToBeClosed != null) {
            try {
                itemToBeClosed.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void rollBack(Connection con) {
        if (con != null) {
            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("Cannot rollback transaction", e);
            }
        }
    }
}
