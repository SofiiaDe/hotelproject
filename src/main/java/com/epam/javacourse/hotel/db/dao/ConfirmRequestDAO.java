package com.epam.javacourse.hotel.db.dao;

import com.epam.javacourse.hotel.db.ConnectionPool;
import com.epam.javacourse.hotel.db.DBConstatns;
import com.epam.javacourse.hotel.db.interfaces.IConfirmRequestDAO;
import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.ConfirmationRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConfirmRequestDAO extends GenericDAO implements IConfirmRequestDAO {

    private static final Logger logger = LogManager.getLogger(ConfirmRequestDAO.class);

    @Override
    public boolean createConfirmRequest(ConfirmationRequest confirmRequest) throws DBException {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(DBConstatns.SQL_CREATE_CONFIRM_REQUEST);
            mapConfirmRequestCreateUpdate(pstmt, confirmRequest);

            pstmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            rollback(con);
            throw new DBException("Can't create confirmation request", e);
        } finally {
            close(con);
            close(pstmt);
        }
    }

    @Override
    public List<ConfirmationRequest> findConfirmRequestsByUserId(int userId) throws DBException {
        List<ConfirmationRequest> userConfirmRequests = new ArrayList<>();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionPool.getInstance().getConnection();
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
            String errorMessage = "Can't find confirmation requests by user_id=" + userId;
            logger.error(errorMessage, e);
            throw new DBException("Cannot find confirmation requests by user_id", e);
        } finally {
            close(con);
            close(pStmt);
            close(rs);
        }
        return userConfirmRequests;
    }

    @Override
    public List<ConfirmationRequest> findAllConfirmRequests() throws DBException {

        List<ConfirmationRequest> allConfirmRequestsList = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionPool.getInstance().getConnection();
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
            throw new DBException("Can't find all confirmation requests", e);
        } finally {
            close(con);
            close(stmt);
            close(rs);
        }

        return allConfirmRequestsList;
    }

    @Override
    public void deleteConfirmRequestById(int id) throws DBException {

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            pstmt = con.prepareStatement(DBConstatns.SQL_DELETE_CONFIRM_REQUEST_BY_ID);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            String errorMessage = "Can't delete confirmation request by id=" + id;
            logger.error(errorMessage, e);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(pstmt);
        }
    }

    @Override
    public ConfirmationRequest findConfirmRequestById(int confirmRequestId) throws DBException {

        ConfirmationRequest confirmRequest = new ConfirmationRequest();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionPool.getInstance().getConnection();
            pStmt = con.prepareStatement(DBConstatns.SQL_GET_CONFIRM_REQUEST_BY_ID);
            pStmt.setInt(1, confirmRequestId);

            rs = pStmt.executeQuery();
            while (rs.next()) {
                confirmRequest.setId(confirmRequestId);
                mapConfirmRequestCommonProperties(rs, confirmRequest);
            }

        } catch (SQLException e) {
            String errorMessage = "Can't find invoice by id=" + confirmRequestId;
            logger.error(errorMessage, e);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(pStmt);
            close(rs);
        }
        return confirmRequest;
    }

    @Override
    public boolean updateConfirmRequestStatus(ConfirmationRequest confirmRequest) throws DBException {

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(DBConstatns.SQL_UPDATE_CONFIRM_REQUEST_STATUS);
            pstmt.setString(1, confirmRequest.getStatus());
            pstmt.setInt(2, confirmRequest.getId());
            pstmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            String errorMessage = "Can't update invoice status with id=" + confirmRequest.getId();
            logger.error(errorMessage, e);
            rollback(con);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(pstmt);
        }
    }

    private static void close(AutoCloseable itemToBeClosed) {
        close(itemToBeClosed, "DB close failed in ConfirmRequestDAO");
    }

    private static void rollback(Connection con) {
        rollback(con, "Cannot rollback transaction in ConfirmRequestDAO");
    }

    private static void mapConfirmRequestCommonProperties(ResultSet rs, ConfirmationRequest confirmRequest) throws SQLException {
        confirmRequest.setApplicationId(rs.getInt("application_id"));
        confirmRequest.setRoomId(rs.getInt("room_id"));
        confirmRequest.setConfirmRequestDate(rs.getObject("confirm_request_date", LocalDate.class));
        confirmRequest.setStatus(rs.getString("status"));

    }

    private static void mapConfirmRequestCreateUpdate(PreparedStatement pstmt, ConfirmationRequest confirmRequest) throws SQLException{
        pstmt.setInt(1, confirmRequest.getUserId());
        pstmt.setInt(2, confirmRequest.getApplicationId());
        pstmt.setInt(3, confirmRequest.getRoomId());
        pstmt.setObject(4, confirmRequest.getConfirmRequestDate());
    }
}
