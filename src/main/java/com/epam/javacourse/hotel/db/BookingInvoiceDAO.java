package com.epam.javacourse.hotel.db;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Invoice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookingInvoiceDAO {

    private static final Logger logger = LogManager.getLogger(BookingInvoiceDAO.class);

    public boolean createBookingAndInvoice(Booking booking, Invoice invoice) throws DBException {

        Connection con = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;


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


            pstmt2 = con.prepareStatement(DBConstatns.SQL_CREATE_BOOKING_AND_INVOICE);
            pstmt2.setInt(1, invoice.getUserId());
            pstmt2.setDouble(2, invoice.getAmount());
            pstmt2.setObject(3, invoice.getInvoiceDate());
            pstmt2.executeUpdate();

            con.commit();
            return true;
        } catch (SQLException e) {
            rollBack(con);
            throw new DBException("Cannot create booking and invoice", e);
        } finally {
            close(con);
            close(pstmt);
            close(pstmt2);
        }
    }

    private static void close(AutoCloseable itemToBeClosed) {
        if (itemToBeClosed != null) {
            try {
                itemToBeClosed.close();
            } catch (Exception e) {
                logger.error("DB close failed in BookingInvoiceDAO", e);
            }
        }
    }

    private static void rollBack(Connection con) {
        if (con != null) {
            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("Cannot rollback transaction in BookingInvoiceDAO", e);
            }
        }
    }

}
