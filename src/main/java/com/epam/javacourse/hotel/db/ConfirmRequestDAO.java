package com.epam.javacourse.hotel.db;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.ConfirmationRequest;
import com.epam.javacourse.hotel.model.Invoice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConfirmRequestDAO {

    private static final Logger logger = LogManager.getLogger(ConfirmRequestDAO.class);

    public boolean createConfirmRequest(ConfirmationRequest confirmRequest) throws DBException {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(DBConstatns.SQL_CREATE_CONFIRM_REQUEST);
            pstmt.setInt(1, confirmRequest.getUserId());
            pstmt.setInt(2, confirmRequest.getApplicationId());
            pstmt.setInt(3, confirmRequest.getRoomId());
            pstmt.setObject(4, confirmRequest.getConfirmRequestDate());

            pstmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            logger.error("Cannot create a confirmation request", e);
            rollBack(con);
            throw new DBException("Cannot create a confirmation request", e);
        } finally {
            close(con);
            close(pstmt);
        }
    }

    public List<ConfirmationRequest> findConfirmRequestsByUserId(int userId) throws DBException {
        List<ConfirmationRequest> userConfirmRequests = new ArrayList<>();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getInstance().getConnection();
            pStmt = con.prepareStatement(DBConstatns.SQL_GET_CONFIRM_REQUESTS_BY_USER_ID);
            pStmt.setInt(1, userId);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                ConfirmationRequest confirmRequest = new ConfirmationRequest();
                confirmRequest.setId(rs.getInt("id"));
                confirmRequest.setUserId(userId);
                mapConfirmRequestCommonProperties(rs, confirmRequest);
                userConfirmRequests.add(confirmRequest);
            }

        } catch (SQLException e) {
            logger.error("Cannot get confirmation requests by user_id", e);
            throw new DBException("Cannot get confirmation requests by user_id", e);
        } finally {
            close(con);
            close(pStmt);
            close(rs);
        }
        return userConfirmRequests;
    }

    public List<ConfirmationRequest> findAllConfirmRequests() throws DBException {

        List<ConfirmationRequest> allConfirmRequestsList = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getInstance().getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(DBConstatns.SQL_GET_ALL_CONFIRM_REQUESTS);
            while (rs.next()) {
                ConfirmationRequest confirmRequest = new ConfirmationRequest();
                confirmRequest.setId(rs.getInt("id"));
                confirmRequest.setUserId(rs.getInt("user_id"));
                mapConfirmRequestCommonProperties(rs, confirmRequest);
                allConfirmRequestsList.add(confirmRequest);
            }
        } catch (SQLException e) {
            logger.error("Cannot get all confirmation requests", e);
            throw new DBException("Cannot get all confirmation requests", e);
        } finally {
            close(con);
            close(stmt);
            close(rs);
        }

        return allConfirmRequestsList;
    }

    public void deleteConfirmRequestById(int id) throws DBException {

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(DBConstatns.SQL_DELETE_CONFIRM_REQUEST_BY_ID);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("Cannot delete confirmation request", e);
            throw new DBException("Cannot delete confirmation request", e);
        } finally {
            close(con);
            close(pstmt);
        }
    }

    public ConfirmationRequest findConfirmRequestById(int confirmRequestId) throws DBException {

        ConfirmationRequest confirmRequest = new ConfirmationRequest();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getInstance().getConnection();
            pStmt = con.prepareStatement(DBConstatns.SQL_GET_CONFIRM_REQUEST_BY_ID);
            pStmt.setInt(1, confirmRequestId);

            rs = pStmt.executeQuery();
            while (rs.next()) {
                confirmRequest.setId(confirmRequestId);
                mapConfirmRequestCommonProperties(rs, confirmRequest);
            }

        } catch (SQLException e) {
            logger.error("Cannot get invoice by id", e);
            throw new DBException("Cannot get invoice by id", e);
        } finally {
            close(con);
            close(pStmt);
            close(rs);
        }
        return confirmRequest;
    }

    public boolean updateConfirmRequestStatus(ConfirmationRequest confirmRequest) throws DBException {

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(DBConstatns.SQL_UPDATE_CONFIRM_REQUEST_STATUS);
            pstmt.setString(1, confirmRequest.getStatus());
            pstmt.setInt(2, confirmRequest.getId());
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

    private static void close(AutoCloseable itemToBeClosed) {
        if (itemToBeClosed != null) {
            try {
                itemToBeClosed.close();
            } catch (Exception e) {
                logger.error("DB close failed in ConfirmRequestDAO", e);
            }
        }
    }

    private static void rollBack(Connection con) {
        if (con != null) {
            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("Cannot rollback transaction in ConfirmRequestDAO", e);
            }
        }
    }

    private static void mapConfirmRequestCommonProperties(ResultSet rs, ConfirmationRequest confirmRequest) throws SQLException {
        confirmRequest.setApplicationId(rs.getInt("application_id"));
        confirmRequest.setRoomId(rs.getInt("room_id"));
        confirmRequest.setConfirmRequestDate(rs.getObject("confirm_request_date", LocalDate.class));
        confirmRequest.setStatus(rs.getString("status"));

    }
}
