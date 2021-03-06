package com.epam.javacourse.hotel.db.dao;

import com.epam.javacourse.hotel.db.ConnectionPool;
import com.epam.javacourse.hotel.db.DBConstatns;
import com.epam.javacourse.hotel.db.interfaces.IBookingInvoiceDAO;
import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Invoice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingInvoiceDAO extends GenericDAO implements IBookingInvoiceDAO {

    private static final Logger logger = LogManager.getLogger(BookingInvoiceDAO.class);

    // Create booking and invoice simultaneously when room is booked
    @Override
    public boolean createBookingAndInvoice(Booking booking, Invoice invoice) throws DBException {

        Connection con = null;
        PreparedStatement pstmtForUpdate = null;
        PreparedStatement pstmtAvailable = null;
        PreparedStatement pstmtBooking = null;
        PreparedStatement pstmtBookInvoice = null;
        ResultSet rs = null;

        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setAutoCommit(false);

            // lock the selected room for update -->
            pstmtForUpdate = con.prepareStatement(DBConstatns.SQL_FOR_UPDATE);
            pstmtForUpdate.setInt(1, booking.getRoomId());
            pstmtForUpdate.executeQuery();

            // check if the room is still available -->

            // A default ResultSet object is not updatable and has a cursor that moves forward only.
            // Thus, you can iterate through it only once and only from the first row to the last row.
            // It is possible to produce ResultSet objects that are scrollable and/or updatable.
            pstmtAvailable = con.prepareStatement(DBConstatns.SQL_GET_AVAILABLE_ROOM, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            pstmtAvailable.setObject(1, booking.getCheckinDate());
            pstmtAvailable.setObject(2, booking.getCheckoutDate());
            pstmtAvailable.setInt(3, booking.getRoomId());

            rs = pstmtAvailable.executeQuery();
            if (!rs.first()) {
                throw new DBException("Room is already booked");
            }

            // create new booking
            pstmtBooking = con.prepareStatement(DBConstatns.SQL_CREATE_BOOKING);
            pstmtBooking.setInt(1, booking.getUserId());
            pstmtBooking.setObject(2, booking.getCheckinDate());
            pstmtBooking.setObject(3, booking.getCheckoutDate());
            pstmtBooking.setInt(4, booking.getRoomId());
            pstmtBooking.setInt(5, booking.getApplicationId());
            pstmtBooking.executeUpdate();

            // create new invoice in the same transaction
            pstmtBookInvoice = con.prepareStatement(DBConstatns.SQL_CREATE_INVOICE_WITH_BOOKING);
            pstmtBookInvoice.setInt(1, invoice.getUserId());
            pstmtBookInvoice.setBigDecimal(2, invoice.getAmount());
            pstmtBookInvoice.setObject(3, invoice.getInvoiceDate());
            pstmtBookInvoice.executeUpdate();

            con.commit();
            return true;
        } catch (DBException e) {
            rollback(con);
            logger.error(e);
            throw e;
        } catch (SQLException e) {
            rollback(con);
            throw new DBException("Cannot create booking and invoice", e);
        } finally {
            close(con);
            close(pstmtForUpdate);
            close(pstmtAvailable);
            close(pstmtBooking);
            close(pstmtBookInvoice);
            close(rs);

        }
    }

    private static void close(AutoCloseable itemToBeClosed) {
        close(itemToBeClosed, "DB close failed in BookingInvoiceDAO");
    }

    private static void rollback(Connection con) {
        rollback(con, "Cannot rollback transaction in BookingInvoiceDAO");
    }

}
