package com.epam.javacourse.hotel.db;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.Booking;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class BookingDAO {

    private static final Logger logger = LogManager.getLogger(BookingDAO.class);

    public boolean createBooking(Booking booking) throws DBException {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(DBConstatns.SQL_CREATE_BOOKING);
            pstmt.setInt(1, booking.getUser().getId());
            pstmt.setTimestamp(2, Timestamp.valueOf(booking.getCheckInDate()));
            pstmt.setTimestamp(3, Timestamp.valueOf(booking.getCheckOutDate()));
            pstmt.setInt(4, booking.getRoom().getId());
            pstmt.setInt(5, booking.getApplication().getId());

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
