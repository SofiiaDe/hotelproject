package com.epam.javacourse.hotel.db;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
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
            pstmt.setObject(2, booking.getCheckinDate());
            pstmt.setObject(3, booking.getCheckoutDate());
            pstmt.setInt(4, booking.getRoomId());
            pstmt.setInt(5, booking.getApplicationId());

            pstmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            rollBack(con);
            throw new DBException("Cannot create a booking", e);
        } finally {
            close(con);
            close(pstmt);
        }
    }

    public List<Booking> findBookingsByUserId(int userId) throws DBException {
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
                mapBookingCommonProperties(rs, booking);
                userBookings.add(booking);
            }
        } catch (SQLException e) {
            String errorMessage = "Cannot find bookings by user_id=" + userId;
            logger.error(errorMessage, e);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(pStmt);
            close(rs);
        }
        return userBookings;
    }

    /**
     * Get all bookings
     *
     * @return All bookings
     * @throws DBException
     */
    public List<Booking> getAllBookings() throws DBException {
        return getAllBookings(-1, -1);
    }

    /**
     * Get all bookings limited by page and pageSize
     *
     * @param page     result's page. Set -1 to not limit by page/size
     * @param pageSize number of items to select
     * @return bookings from specified page and given page size
     * @throws DBException
     */
    public List<Booking> getAllBookings(int page, int pageSize) throws DBException {

        List<Booking> allBookingsList = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        String sql = DBConstatns.SQL_GET_ALL_BOOKINGS;
        sql = Helpers.enrichWithPageSizeStatement(page, pageSize, sql);

        try {
            con = DBManager.getInstance().getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setUserId(rs.getInt("user_id"));
                mapBookingCommonProperties(rs, booking);
                allBookingsList.add(booking);
            }
        } catch (SQLException e) {
            String errorMessage = "Cannot get all bookings for page=" + page + " and pageSize=" + pageSize;
            logger.error(errorMessage, e);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(stmt);
            close(rs);
        }

        return allBookingsList;
    }

    public Booking findBookingById(int id) throws DBException {

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
                mapBookingCommonProperties(rs, booking);
            }
        } catch (SQLException e) {
            String errorMessage = "Cannot find booking by id=" + id;
            logger.error(errorMessage, e);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(pStmt);
            close(rs);
        }
        return booking;
    }

    public void deleteBookingById(int id) throws DBException {

        Connection con = null;
        PreparedStatement pStmt = null;

        try {
            con = DBManager.getInstance().getConnection();
            pStmt = con.prepareStatement(DBConstatns.SQL_DELETE_BOOKING_BY_ID);
            pStmt.setInt(1, id);
            pStmt.executeUpdate();

        } catch (SQLException e) {
            String errorMessage = "Cannot delete booking by id=" + id;
            logger.error(errorMessage, e);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(pStmt);
        }
    }

    private static void close(AutoCloseable itemToBeClosed) {
        if (itemToBeClosed != null) {
            try {
                itemToBeClosed.close();
            } catch (Exception e) {
                logger.error("DB close failed in BookingDAO", e);
            }
        }
    }

    private static void rollBack(Connection con) {
        if (con != null) {
            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("Cannot rollback transaction in BookingDAO", e);
            }
        }
    }

    private static void mapBookingCommonProperties(ResultSet rs, Booking booking) throws SQLException {
        booking.setCheckinDate(rs.getObject("checkin_date", LocalDate.class));
        booking.setCheckoutDate(rs.getObject("checkout_date", LocalDate.class));
        booking.setRoomId(rs.getInt("room_id"));
        booking.setApplicationId(rs.getInt("application_id"));
    }

    /**
     * Retrieve number of all bookings
     *
     * @return number of all bookings
     * @throws DBException
     */
    public int getAllBookingsCount() throws DBException {
        int result;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        String sql = DBConstatns.SQL_GET_ALL_BOOKINGS_COUNT;

        try {
            con = DBManager.getInstance().getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            rs.next();
            result = rs.getInt("cnt");
        } catch (SQLException e) {
            String errorMessage = "Cannot get all bookings count";
            logger.error(errorMessage, e);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(stmt);
            close(rs);
        }

        return result;
    }
}
