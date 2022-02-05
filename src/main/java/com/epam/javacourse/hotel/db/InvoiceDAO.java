package com.epam.javacourse.hotel.db;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.Invoice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class InvoiceDAO {

    private static final Logger logger = LogManager.getLogger(InvoiceDAO.class);

    public boolean createApplication(Invoice invoice) throws DBException {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(DBConstatns.SQL_CREATE_INVOICE);
            pstmt.setInt(1, invoice.getUserId());
            pstmt.setDouble(2, invoice.getAmount());
            pstmt.setInt(3, invoice.getBookingId());
            pstmt.setTimestamp(4, Timestamp.valueOf(invoice.getInvoiceDate()));
            pstmt.setString(5, invoice.getInvoiceStatus());

            pstmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            logger.error("Cannot generate an invoice", e);
            rollBack(con);
            throw new DBException("Cannot generate an invoice", e);
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
                logger.error("DB close failed in InvoiceDAO", e);
            }
        }
    }

    private static void rollBack(Connection con) {
        if (con != null) {
            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("Cannot rollback transaction in InvoiceDAO", e);
            }
        }
    }
}
