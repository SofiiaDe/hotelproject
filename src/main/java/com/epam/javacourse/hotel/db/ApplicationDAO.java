package com.epam.javacourse.hotel.db;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class ApplicationDAO {

    private static final Logger logger = LogManager.getLogger(ApplicationDAO.class);

    public boolean createApplication(Application application) throws DBException {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(DBConstatns.SQL_CREATE_APPLICATION);
            pstmt.setInt(1, application.getUser().getId());
            pstmt.setString(2, application.getRoomTypeBySeats());
            pstmt.setString(3, application.getRoomClass());
            pstmt.setTimestamp(4, Timestamp.valueOf(application.getCheckInDate()));
            pstmt.setTimestamp(5, Timestamp.valueOf(application.getCheckOutDate()));

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

    private static void close(AutoCloseable itemToBeClosed) {
        if (itemToBeClosed != null) {
            try {
                itemToBeClosed.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void rollBack(Connection con) {
        if (con != null) {
            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("Cannot rollback transaction", e);
            }
        }
    }
}
