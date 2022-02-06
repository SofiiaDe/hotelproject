package com.epam.javacourse.hotel.db;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.User;
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

    public List<Booking> findAllBookings() throws DBException {

        List<Booking> allBookingsList = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getInstance().getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(DBConstatns.SQL_GET_ALL_BOOKINGS);
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setUserId(rs.getInt("user_id"));
                booking.setCheckinDate(LocalDateTime.parse(rs.getString("checkin_date")));
                booking.setCheckoutDate(LocalDateTime.parse(rs.getString("checkout_date")));
                booking.setRoomId(rs.getInt("room_id"));
                booking.setApplicationId(rs.getInt("application_id"));
                allBookingsList.add(booking);

            }
        } catch (SQLException e) {
            logger.error("Cannot get all bookings", e);
            throw new DBException("Cannot get all bookings", e);
        } finally {
            close(con);
            close(stmt);
            close(rs);
        }

        return allBookingsList;
    }

    public Booking getBookingById(int id) throws DBException {

        Booking booking = new Booking();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getInstance().getConnection();
            pStmt = con.prepareStatement(DBConstatns.SQL_GET_BOOKING_BY_ID);
            pStmt.setInt(1, id);

            rs = pStmt.executeQuery();
            while (rs.next()) {
                booking.setId(id);
                booking.setUserId(rs.getInt("user_id"));
                booking.setCheckinDate(rs.getDate("checkin_date").toLocalDate().atStartOfDay());
                booking.setCheckoutDate(rs.getDate("checkout_date").toLocalDate().atStartOfDay());
                booking.setRoomId(rs.getInt("room_id"));
                booking.setApplicationId(rs.getInt("application_id"));
            }

        } catch (SQLException e) {
            logger.error("Cannot get booking by id", e);
            throw new DBException("Cannot get booking by id", e);
        } finally {
            close(con);
            close(pStmt);
            close(rs);
        }
        return booking;
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
