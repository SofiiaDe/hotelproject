package com.epam.javacourse.hotel.db;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Invoice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
                invoice.setInvoiceDate(LocalDateTime.parse(rs.getString("invoice_date")));
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
        invoice.setInvoiceDate(LocalDateTime.parse(rs.getString("invoice_date")));
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
}
