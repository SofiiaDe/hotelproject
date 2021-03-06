package com.epam.javacourse.hotel.db.dao;

import com.epam.javacourse.hotel.db.ConnectionPool;
import com.epam.javacourse.hotel.db.DBConstatns;
import com.epam.javacourse.hotel.db.Helpers;
import com.epam.javacourse.hotel.db.interfaces.IBookingDAO;
import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.shared.models.BookingStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO extends GenericDAO implements IBookingDAO {

    private static final Logger logger = LogManager.getLogger(BookingDAO.class);

    @Override
    public boolean createBooking(Booking booking) throws DBException {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(DBConstatns.SQL_CREATE_BOOKING);
            mapBookingCreateUpdate(pstmt, booking);

            pstmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            rollback(con);
            throw new DBException("Cannot create a booking", e);
        } finally {
            close(con);
            close(pstmt);
        }
    }

    @Override
    public List<Booking> findBookingsByUserId(int userId) throws DBException {
        return findBookingsByUserIdForPage(userId, -1, 0);
    }

    @Override
    public List<Booking> findBookingsByUserIdForPage(int userId, int page, int pageSize) throws DBException {
        List<Booking> userBookings = new ArrayList<>();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        String sql = DBConstatns.SQL_GET_BOOKINGS_BY_USER_ID;
        sql = Helpers.enrichWithPageSizeStatement(page, pageSize, sql);

        try {
            con = ConnectionPool.getInstance().getConnection();
            pStmt = con.prepareStatement(sql);
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

    @Override
    public List<Booking> findAllBookings() throws DBException {
        return findAllBookingsForPage(-1, -1, BookingStatus.NONE);
    }

    @Override
    public List<Booking> findAllBookingsForPage(int page, int pageSize, BookingStatus bookingStatus) throws DBException {

        List<Booking> allBookingsList = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        String sql;
        if (bookingStatus == null || bookingStatus == BookingStatus.NONE){
            sql = DBConstatns.SQL_GET_ALL_BOOKINGS;
        }else{
            sql = DBConstatns.SQL_GET_ALL_BOOKINGS_WITH_STATUS;
        }

        sql = createGetAllBookingsSql(sql, page, pageSize, bookingStatus);

        try {
            con = ConnectionPool.getInstance().getConnection();
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

    @Override
    public Booking findBookingById(int id) throws DBException {

        Booking booking = new Booking();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionPool.getInstance().getConnection();
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

    @Override
    public void deleteBookingById(int id) throws DBException {

        Connection con = null;
        PreparedStatement pStmt = null;

        try {
            con = ConnectionPool.getInstance().getConnection();
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
        close(itemToBeClosed, "DB close failed in BookingDAO");
    }

    private static void rollback(Connection con) {
        rollback(con, "Cannot rollback transaction in BookingDAO");
    }

    private static void mapBookingCommonProperties(ResultSet rs, Booking booking) throws SQLException {
        booking.setCheckinDate(rs.getObject("checkin_date", LocalDate.class));
        booking.setCheckoutDate(rs.getObject("checkout_date", LocalDate.class));
        booking.setRoomId(rs.getInt("room_id"));
        booking.setApplicationId(rs.getInt("application_id"));
    }

    private static void mapBookingCreateUpdate(PreparedStatement pstmt, Booking booking) throws SQLException {
        pstmt.setInt(1, booking.getUserId());
        pstmt.setObject(2, booking.getCheckinDate());
        pstmt.setObject(3, booking.getCheckoutDate());
        pstmt.setInt(4, booking.getRoomId());
        pstmt.setInt(5, booking.getApplicationId());
    }

    @Override
    public int findAllBookingsCount(BookingStatus bookingStatus) throws DBException {
        int result;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        String sql;
        if (bookingStatus == null || bookingStatus == BookingStatus.NONE){
            sql = DBConstatns.SQL_GET_ALL_BOOKINGS_COUNT;
        }else{
            sql = DBConstatns.SQL_GET_ALL_BOOKINGS_COUNT_WITH_STATUS;
        }
        sql = createGetAllBookingsSql(sql, -1, -1, bookingStatus);

        try {
            con = ConnectionPool.getInstance().getConnection();
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

    @Override
    public int findUserBookingsCount(int userId) throws DBException {
        int result;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionPool.getInstance().getConnection();
            pstmt = con.prepareStatement(DBConstatns.SQL_GET_USER_BOOKINGS_COUNT);
            pstmt.setObject(1, userId);
            rs = pstmt.executeQuery();
            rs.next();
            result = rs.getInt("cnt");
        } catch (SQLException e) {
            String errorMessage = "Cannot get client's bookings count";
            logger.error(errorMessage, e);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(pstmt);
            close(rs);
        }

        return result;
    }

    @Override
    public String createGetAllBookingsSql(String baseQuery, int page, int pageSize, BookingStatus bookingStatus){
        String sql = baseQuery;

        switch (bookingStatus){
            case NONE:
                break;
            case NEW:
                sql += DBConstatns.SQL_FILTER_BOOKING_NEW;
                break;
            case CANCELLED:
                sql += DBConstatns.SQL_FILTER_BOOKING_CANCELLED;
                break;
            case PAID:
                sql += DBConstatns.SQL_FILTER_BOOKING_PAID;
                break;
            case FINISHED:
                sql += DBConstatns.SQL_FILTER_BOOKING_FINISHED;
                break;
            case ONGOING:
                sql += DBConstatns.SQL_FILTER_BOOKING_ONGOING;
                break;
        }

        sql = Helpers.enrichWithPageSizeStatement(page, pageSize, sql);

        return sql;
    }

    @Override
    public boolean updateBookingStatus(Booking booking) throws DBException {

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(DBConstatns.SQL_UPDATE_BOOKING_STATUS);
            pstmt.setBoolean(1, booking.isStatus());
            pstmt.setInt(2, booking.getId());
            pstmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            String errorMessage = "Can't update status of booking with id=" + booking.getId();
            logger.error(errorMessage, e);
            rollback(con);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(pstmt);
        }
    }

}
