package com.epam.javacourse.hotel.db;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.Invoice;
import com.epam.javacourse.hotel.model.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InvoiceDAO {

    private static final Logger logger = LogManager.getLogger(InvoiceDAO.class);

    public boolean createInvoice(Invoice invoice) throws DBException {

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

    public List<Invoice> findAllInvoices() throws DBException {

        List<Invoice> allInvoicesList = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getInstance().getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(DBConstatns.SQL_GET_ALL_INVOICES);
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(rs.getInt("id"));
                invoice.setUserId(rs.getInt("user_id"));
                invoice.setAmount(rs.getDouble("amount"));
                invoice.setBookingId(rs.getInt("booking_id"));
                invoice.setInvoiceDate(rs.getDate("invoice_date").toLocalDate().atStartOfDay());
                invoice.setInvoiceStatus(rs.getString("status"));
                allInvoicesList.add(invoice);

            }
        } catch (SQLException e) {
            logger.error("Cannot get all invoices", e);
            throw new DBException("Cannot get all invoices", e);
        } finally {
            close(con);
            close(stmt);
            close(rs);
        }

        return allInvoicesList;
    }

    public List<Invoice> findInvoicesByUserId(int userId) throws DBException {

        List<Invoice> userInvoices = new ArrayList<>();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getInstance().getConnection();
            pStmt = con.prepareStatement(DBConstatns.SQL_GET_INVOICES_BY_USER_ID);
            pStmt.setInt(1, userId);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setUserId(userId);
                mapInvoiceCommonProperties(invoice, rs);
                userInvoices.add(invoice);
            }

        } catch (SQLException e) {
            logger.error("Cannot get invoices by user_id", e);
            throw new DBException("Cannot get invoices by user_id", e);
        } finally {
            close(con);
            close(pStmt);
            close(rs);
        }
        return userInvoices;
    }

    private static Invoice mapResultSetToInvoice(ResultSet rs) throws SQLException{
        Invoice invoice = new Invoice();
        invoice.setId(rs.getInt("id"));
        mapInvoiceCommonProperties(invoice, rs);

        return invoice;
    }

    private static void mapInvoiceCommonProperties(Invoice invoice, ResultSet rs) throws SQLException {
        invoice.setAmount(rs.getDouble("amount"));
        invoice.setBookingId(rs.getInt("booking_id"));
        invoice.setInvoiceDate(rs.getDate("invoice_date").toLocalDate().atStartOfDay());
        invoice.setInvoiceStatus(rs.getString("status"));
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

    public List<Integer> findCancelledInvoicedBookingIds(List<Integer> bookingIds) throws DBException {

        if (bookingIds.isEmpty()){
            return Collections.emptyList();
        }

        List<Integer> bookingIdsResult = new ArrayList<>();

        String query = DBConstatns.SQL_GET_CANCELLED_INVOICE_BOOKING_IDS_BY_BOOKING_ID;

        String sql = String.format(query, preparePlaceHolders(bookingIds.size()));
        Connection con = null;
        PreparedStatement pStmt = null;

        try {
            con = DBManager.getInstance().getConnection();
            pStmt = con.prepareStatement(sql);

            setValuesInPreparedStatement(pStmt, bookingIds.toArray());

            try (ResultSet resultSet = pStmt.executeQuery()) {
                while (resultSet.next()) {
                    bookingIdsResult.add(resultSet.getInt("booking_id"));
                }
            }
        } catch (SQLException e) {
            String error = "Cannot find cancelled invoice's booking ids";
            logger.error(error, e);
            throw new DBException(error, e);
        } finally {
            close(con);
            close(pStmt);
        }

        return bookingIdsResult;
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

    public boolean updateInvoiceStatus(Invoice invoice) throws DBException {

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(DBConstatns.SQL_UPDATE_INVOICE_STATUS);
            pstmt.setString(1, invoice.getInvoiceStatus());
            pstmt.setInt(2, invoice.getId());
            pstmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            logger.error("Cannot update invoice status", e);
            rollBack(con);
            throw new DBException("Cannot update invoice status", e);
        } finally {
            close(con);
            close(pstmt);
        }
    }

    public boolean updateInvoice(Invoice invoice) throws DBException {

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(DBConstatns.SQL_UPDATE_INVOICE);
            pstmt.setInt(1, invoice.getUserId());
            pstmt.setDouble(2, invoice.getAmount());
            pstmt.setInt(3, invoice.getBookingId());
            pstmt.setTimestamp(4, Timestamp.valueOf(invoice.getInvoiceDate()));
            pstmt.setString(5, invoice.getInvoiceStatus());
            pstmt.setInt(6, invoice.getId());

            pstmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            logger.error("Cannot update invoice", e);
            rollBack(con);
            throw new DBException("Cannot update invoice", e);
        } finally {
            close(con);
            close(pstmt);
        }
    }

    public List<Invoice> findInvoicesByStatus(String status) throws DBException {

        List<Invoice> invoicesByStatus = new ArrayList<>();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getInstance().getConnection();
            pStmt = con.prepareStatement(DBConstatns.SQL_GET_INVOICES_BY_STATUS);
            pStmt.setString(1, status);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setUserId(rs.getInt("user_id"));
                invoice.setAmount(rs.getDouble("amount"));
                invoice.setBookingId(rs.getInt("booking_id"));
                invoice.setInvoiceDate(rs.getDate("invoice_date").toLocalDate().atStartOfDay());
                invoice.setInvoiceStatus(status);

                invoicesByStatus.add(invoice);
            }

        } catch (SQLException e) {
            logger.error("Cannot get invoices by status", e);
            throw new DBException("Cannot get invoices by status", e);
        } finally {
            close(con);
            close(pStmt);
            close(rs);
        }
        return invoicesByStatus;
    }

}
