package com.epam.javacourse.hotel.db.dao;

import com.epam.javacourse.hotel.db.ConnectionPool;
import com.epam.javacourse.hotel.db.DBConstatns;
import com.epam.javacourse.hotel.db.interfaces.IApplicationDAO;
import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO extends GenericDAO implements IApplicationDAO {

    private static final Logger logger = LogManager.getLogger(ApplicationDAO.class);

    @Override
    public boolean createApplication(Application application) throws DBException {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(DBConstatns.SQL_CREATE_APPLICATION);
            mapApplicationCreateUpdate(pstmt, application);

            pstmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            rollBack(con);
            throw new DBException("Cannot create an application", e);
        } finally {
            close(con);
            close(pstmt);
        }
    }

    @Override
    public Application findApplicationById(int id) throws DBException {

        Application application = new Application();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionPool.getInstance().getConnection();
            pStmt = con.prepareStatement(DBConstatns.SQL_GET_APPLICATION_BY_ID);
            pStmt.setInt(1, id);

            rs = pStmt.executeQuery();
            while (rs.next()) {
                application.setId(id);
                application.setUserId(rs.getInt("user_id"));
                mapApplicationCommonProperties(rs, application);
            }
        } catch (SQLException e) {
            String errorMessage = "Cannot get application by id=" + id;
            logger.error(errorMessage, e);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(pStmt);
            close(rs);
        }
        return application;
    }

    @Override
    public List<Application> findAllApplications() throws DBException {

        List<Application> allApplicationsList = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionPool.getInstance().getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(DBConstatns.SQL_GET_ALL_APPLICATIONS);
            while (rs.next()) {
                Application application = new Application();
                application.setId(rs.getInt("id"));
                application.setUserId(rs.getInt("user_id"));
                mapApplicationCommonProperties(rs, application);
                allApplicationsList.add(application);
            }
        } catch (SQLException e) {
            throw new DBException("Cannot get all applications", e);
        } finally {
            close(con);
            close(stmt);
            close(rs);
        }

        return allApplicationsList;
    }

    @Override
    public List<Application> findApplicationsByUserId(int userId) throws DBException {

        List<Application> userApplications = new ArrayList<>();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionPool.getInstance().getConnection();
            pStmt = con.prepareStatement(DBConstatns.SQL_GET_APPLICATIONS_BY_USER_ID);
            pStmt.setInt(1, userId);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                Application application = new Application();
                application.setId(rs.getInt("id"));
                application.setUserId(userId);
                mapApplicationCommonProperties(rs, application);
                userApplications.add(application);
            }
        } catch (SQLException e) {
            String errorMessage = "Cannot get applications by user_id=" + userId;
            logger.error(errorMessage, e);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(pStmt);
            close(rs);
        }
        return userApplications;
    }

    @Override
    public boolean updateApplication(Application application) throws DBException {

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setAutoCommit(false);

            pstmt = con.prepareStatement(DBConstatns.SQL_UPDATE_APPLICATION);
            mapApplicationCreateUpdate(pstmt, application);
            pstmt.setInt(6, application.getId());
            pstmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            rollBack(con);
            throw new DBException("Cannot update application", e);
        } finally {
            close(con);
            close(pstmt);
        }
    }

    @Override
    public void deleteApplication(int applicationId) throws DBException {

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            pstmt = con.prepareStatement(DBConstatns.SQL_DELETE_APPLICATION_BY_ID);
            pstmt.setInt(1, applicationId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            String errorMessage = "Cannot delete application with id=" + applicationId;
            logger.error(errorMessage, e);
            throw new DBException(errorMessage, e);
        } finally {
            close(con);
            close(pstmt);
        }
    }

    private static void close(AutoCloseable itemToBeClosed) {
        close(itemToBeClosed, "DB close failed in ApplicationDAO");
    }

    private static void rollBack(Connection con) {
        rollback(con, "Cannot rollback transaction in ApplicationDAO");
    }

    private static void mapApplicationCommonProperties(ResultSet rs, Application application) throws SQLException {
        application.setRoomTypeBySeats(rs.getString("room_seats"));
        application.setRoomClass(rs.getString("room_class"));
        application.setCheckinDate(rs.getObject("checkin_date", LocalDate.class));
        application.setCheckoutDate(rs.getObject("checkout_date", LocalDate.class));
    }

    private static void mapApplicationCreateUpdate(PreparedStatement pstmt, Application application) throws SQLException {
        pstmt.setInt(1, application.getUserId());
        pstmt.setString(2, application.getRoomTypeBySeats());
        pstmt.setString(3, application.getRoomClass());
        pstmt.setObject(4, application.getCheckinDate());
        pstmt.setObject(5, application.getCheckoutDate());
    }

}
