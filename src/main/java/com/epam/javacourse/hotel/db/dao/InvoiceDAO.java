package com.epam.javacourse.hotel.db.dao;

import com.epam.javacourse.hotel.db.ConnectionPool;
import com.epam.javacourse.hotel.db.DBConstatns;
import com.epam.javacourse.hotel.db.interfaces.IInvoiceDAO;
import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.Invoice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InvoiceDAO extends GenericDAO implements IInvoiceDAO {

    private static final Logger logger = LogManager.getLogger(InvoiceDAO.class);

    @Override
    public boolean createInvoice(Invoice invoice) throws DBException {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(DBConstatns.SQL_CREATE_INVOICE);
            mapInvoiceCreateUpdate(pstmt, invoice);

            pstmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            String errorMessage = "Can't create an invoice";
            logger.error(errorMessage, e);
            rollback(con);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(pstmt);
        }
    }

    @Override
    public List<Invoice> findAllInvoices() throws DBException {

        List<Invoice> allInvoicesList = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionPool.getInstance().getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(DBConstatns.SQL_GET_ALL_INVOICES);
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(rs.getInt("id"));
                invoice.setUserId(rs.getInt("user_id"));
                mapInvoiceCommonProperties(rs, invoice);
                allInvoicesList.add(invoice);

            }
        } catch (SQLException e) {
            throw new DBException("Can't get all invoices", e);
        } finally {
            close(con);
            close(stmt);
            close(rs);
        }

        return allInvoicesList;
    }

    @Override
    public List<Invoice> findInvoicesByUserId(int userId) throws DBException {

        List<Invoice> userInvoices = new ArrayList<>();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionPool.getInstance().getConnection();
            pStmt = con.prepareStatement(DBConstatns.SQL_GET_INVOICES_BY_USER_ID);
            pStmt.setInt(1, userId);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(rs.getInt("id"));
                invoice.setUserId(userId);
                mapInvoiceCommonProperties(rs, invoice);
                userInvoices.add(invoice);
            }

        } catch (SQLException e) {
            String errorMessage = "Cannot find invoices by user_id=" + userId;
            logger.error(errorMessage, e);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(pStmt);
            close(rs);
        }
        return userInvoices;
    }

    @Override
    public List<Integer> findCancelledInvoicedBookingIds(List<Integer> bookingIds) throws DBException {

        if (bookingIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<Integer> bookingIdsResult = new ArrayList<>();

        String query = DBConstatns.SQL_GET_CANCELLED_INVOICE_BOOKING_IDS_BY_BOOKING_ID;

        String sql = String.format(query, preparePlaceHolders(bookingIds.size()));
        Connection con = null;
        PreparedStatement pStmt = null;

        try {
            con = ConnectionPool.getInstance().getConnection();
            pStmt = con.prepareStatement(sql);

            setValuesInPreparedStatement(pStmt, bookingIds.toArray());

            try (ResultSet resultSet = pStmt.executeQuery()) {
                while (resultSet.next()) {
                    bookingIdsResult.add(resultSet.getInt("booking_id"));
                }
            }
        } catch (SQLException e) {
            String errorMessage = "Can't find cancelled invoice's booking ids";
            logger.error(errorMessage, e);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(pStmt);
        }

        return bookingIdsResult;
    }

    @Override
    public boolean updateInvoiceStatus(Invoice invoice) throws DBException {

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(DBConstatns.SQL_UPDATE_INVOICE_STATUS);
            pstmt.setString(1, invoice.getInvoiceStatus());
            pstmt.setInt(2, invoice.getId());
            pstmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            String errorMessage = "Can't update status of invoice with id=" + invoice.getId();
            logger.error(errorMessage, e);
            rollback(con);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(pstmt);
        }
    }

    @Override
    public boolean updateInvoice(Invoice invoice) throws DBException {

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(DBConstatns.SQL_UPDATE_INVOICE);
            mapInvoiceCreateUpdate(pstmt, invoice);
            pstmt.setInt(6, invoice.getId());

            pstmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            String errorMessage = "Can't update invoice with id=" + invoice.getId();
            logger.error(errorMessage, e);
            rollback(con);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(pstmt);
        }
    }

    @Override
    public List<Invoice> findInvoicesByStatus(String status) throws DBException {

        List<Invoice> invoicesByStatus = new ArrayList<>();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionPool.getInstance().getConnection();
            pStmt = con.prepareStatement(DBConstatns.SQL_GET_INVOICES_BY_STATUS);
            pStmt.setString(1, status);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setUserId(rs.getInt("user_id"));
                invoice.setAmount(rs.getDouble("amount"));
                invoice.setBookingId(rs.getInt("booking_id"));
                invoice.setInvoiceDate(rs.getObject("invoice_date", LocalDate.class));
                invoice.setInvoiceStatus(status);

                invoicesByStatus.add(invoice);
            }

        } catch (SQLException e) {
            String errorMessage = "Can't find invoices by status=" + status;
            logger.error(errorMessage, e);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(pStmt);
            close(rs);
        }
        return invoicesByStatus;
    }

    @Override
    public Invoice findInvoiceById(int invoiceId) throws DBException {

        Invoice invoice = new Invoice();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionPool.getInstance().getConnection();
            pStmt = con.prepareStatement(DBConstatns.SQL_GET_INVOICE_BY_ID);
            pStmt.setInt(1, invoiceId);

            rs = pStmt.executeQuery();
            while (rs.next()) {
                invoice.setId(invoiceId);
                invoice.setUserId(rs.getInt("user_id"));
                mapInvoiceCommonProperties(rs, invoice);
            }

        } catch (SQLException e) {
            String errorMessage = "Cannot get invoice by id=" + invoiceId;
            logger.error(errorMessage, e);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(pStmt);
            close(rs);
        }
        return invoice;
    }

    private static Invoice mapResultSetToInvoice(ResultSet rs) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setId(rs.getInt("id"));
        mapInvoiceCommonProperties(rs, invoice);

        return invoice;
    }

    private static void mapInvoiceCommonProperties(ResultSet rs, Invoice invoice) throws SQLException {
        invoice.setAmount(rs.getBigDecimal("amount").doubleValue());
        invoice.setBookingId(rs.getInt("booking_id"));
        invoice.setInvoiceDate(rs.getObject("invoice_date", LocalDate.class));
        invoice.setInvoiceStatus(rs.getString("status"));
    }

    private static void mapInvoiceCreateUpdate(PreparedStatement pstmt, Invoice invoice) throws SQLException {
        pstmt.setInt(1, invoice.getUserId());
        pstmt.setDouble(2, invoice.getAmount());
        pstmt.setInt(3, invoice.getBookingId());
        pstmt.setObject(4, invoice.getInvoiceDate());
        pstmt.setString(5, invoice.getInvoiceStatus());
    }


    private static void close(AutoCloseable itemToBeClosed) {
        close(itemToBeClosed, "DB close failed in InvoiceDAO");
    }

    private static void rollback(Connection con) {
        rollback(con, "Cannot rollback transaction in InvoiceDAO");
    }

    @Override
    public List<Invoice> findInvoices(List<Integer> bookingIds) throws DBException {

        if (bookingIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<Invoice> allInvoicesList = new ArrayList<>();
        Connection con = null;
        PreparedStatement pStmt = null;

        String query = DBConstatns.SQL_GET_INVOICES_BY_BOOKING_ID;
        String sql = String.format(query, preparePlaceHolders(bookingIds.size()));

        try {
            con = ConnectionPool.getInstance().getConnection();
            pStmt = con.prepareStatement(sql);

            setValuesInPreparedStatement(pStmt, bookingIds.toArray());

            try (ResultSet rs = pStmt.executeQuery()) {
                while (rs.next()) {
                    Invoice invoice = new Invoice();
                    invoice.setId(rs.getInt("id"));
                    invoice.setUserId(rs.getInt("user_id"));
                    mapInvoiceCommonProperties(rs, invoice);
                    allInvoicesList.add(invoice);
                }
            }
        } catch (SQLException e) {
            String errorMessage = "Can't find cancelled invoice's booking ids";
            logger.error(errorMessage, e);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(pStmt);
        }

        return allInvoicesList;
    }
}
