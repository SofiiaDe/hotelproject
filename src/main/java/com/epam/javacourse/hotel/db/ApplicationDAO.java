package com.epam.javacourse.hotel.db;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {

    private static final Logger logger = LogManager.getLogger(ApplicationDAO.class);

    public boolean createApplication(Application application) throws DBException {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(DBConstatns.SQL_CREATE_APPLICATION);
            pstmt.setInt(1, application.getUserId());
            pstmt.setString(2, application.getRoomTypeBySeats());
            pstmt.setString(3, application.getRoomClass());
            pstmt.setTimestamp(4, Timestamp.valueOf(application.getCheckinDate()));
            pstmt.setTimestamp(5, Timestamp.valueOf(application.getCheckoutDate()));

            pstmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            logger.error("Cannot create an application", e);
            rollBack(con);
            throw new DBException("Cannot create an application", e);
        } finally {
            close(con);
            close(pstmt);
        }
    }

    public Application getApplicationById(int id) throws DBException {

        Application application = new Application();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getInstance().getConnection();
            pStmt = con.prepareStatement(DBConstatns.SQL_GET_APPLICATION_BY_ID);
            pStmt.setInt(1, id);

            rs = pStmt.executeQuery();
            while (rs.next()) {
                application.setId(id);
                application.setUserId(rs.getInt("user_id"));
                setApplicationCommonProperties(rs, application);
            }

        } catch (SQLException e) {
            logger.error("Cannot get application by id", e);
            throw new DBException("Cannot get application by id", e);
        } finally {
            close(con);
            close(pStmt);
            close(rs);
        }
        return application;
    }

    public List<Application> findAllApplications() throws DBException {

        List<Application> allApplicationsList = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getInstance().getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(DBConstatns.SQL_GET_ALL_APPLICATIONS);
            while (rs.next()) {
                Application application = new Application();
                application.setId(rs.getInt("id"));
                application.setUserId(rs.getInt("user_id"));
                setApplicationCommonProperties(rs, application);
                allApplicationsList.add(application);
            }
        } catch (SQLException e) {
            logger.error("Cannot get all applications", e);
            throw new DBException("Cannot get all applications", e);
        } finally {
            close(con);
            close(stmt);
            close(rs);
        }

        return allApplicationsList;
    }

    public List<Application> findApplicationsByUserId(int userId) throws DBException {

        List<Application> userApplications = new ArrayList<>();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getInstance().getConnection();
            pStmt = con.prepareStatement(DBConstatns.SQL_GET_APPLICATIONS_BY_USER_ID);
            pStmt.setInt(1, userId);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                Application application = new Application();
                application.setId(rs.getInt("id"));
                application.setUserId(userId);
                setApplicationCommonProperties(rs, application);
                userApplications.add(application);
            }

        } catch (SQLException e) {
            logger.error("Cannot get applications by user_id", e);
            throw new DBException("Cannot get applications by user_id", e);
        } finally {
            close(con);
            close(pStmt);
            close(rs);
        }
        return userApplications;
    }
    
    public boolean updateApplication(Application application) throws DBException {

        boolean applicationUpdated;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(DBConstatns.SQL_UPDATE_APPLICATION);
            pstmt.setInt(1, application.getUserId());
            pstmt.setString(2, application.getRoomTypeBySeats());
            pstmt.setString(3, application.getRoomClass());
            pstmt.setTimestamp(4, Timestamp.valueOf(application.getCheckinDate()));
            pstmt.setTimestamp(5, Timestamp.valueOf(application.getCheckoutDate()));
            pstmt.setInt(6, application.getId());
            applicationUpdated = pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.error("Cannot update application", e);
            throw new DBException("Cannot update application", e);
        } finally {
            close(con);
            close(pstmt);
        }
        return applicationUpdated;
    }

    public void deleteApplication(int applicationId) throws DBException {

        boolean applicationDeleted;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBManager.getInstance().getConnection();

            pstmt = con.prepareStatement(DBConstatns.SQL_DELETE_APPLICATION_BY_ID);
            pstmt.setInt(1, applicationId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("Cannot remove application", e);
            throw new DBException("Cannot remove application", e);
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
                logger.error("DB close failed in ApplicationDAO", e);
            }
        }
    }

    private static void rollBack(Connection con) {
        if (con != null) {
            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("Cannot rollback transaction in ApplicationDAO", e);
            }
        }
    }

    private static void setApplicationCommonProperties(ResultSet rs, Application application) throws SQLException {
        application.setRoomTypeBySeats(rs.getString("room_seats"));
        application.setRoomClass(rs.getString("room_class"));
        application.setCheckinDate(rs.getDate("checkin_date").toLocalDate().atStartOfDay());
        application.setCheckoutDate(rs.getDate("checkout_date").toLocalDate().atStartOfDay());
    }
}
