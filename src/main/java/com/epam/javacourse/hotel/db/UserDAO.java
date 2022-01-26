package com.epam.javacourse.hotel.db;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Role;
import com.epam.javacourse.hotel.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserDAO {

    private static final Logger logger = LogManager.getLogger(UserDAO.class);

    public List<User> findAllUsers() throws DBException {

        List<User> allUsersList = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getInstance().getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(DBConstatns.SQL_GET_ALL_USERS);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setCountry(rs.getString("country"));
                user.setRole(Role.getRoleByType(rs.getString("role")));
                allUsersList.add(user);
            }
        } catch (SQLException e) {
            logger.error("Cannot get all users", e);
            throw new DBException("Cannot get all users", e);
        } finally {
            close(con);
            close(stmt);
            close(rs);
        }

        return allUsersList;
    }

    public void createUser(User user) throws DBException {

        User.createUser(user.getEmail());
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false);

            pstmt = con.prepareStatement(DBConstatns.SQL_INSERT_USER,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getCountry());
            pstmt.setString(6, user.getRole().toString().toLowerCase());

            int count = pstmt.executeUpdate();
            if (count > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setId(rs.getInt("id"));
                    }
                }
            }
            con.commit();

        } catch (SQLException e) {
            logger.error("Cannot create a user", e);
            rollBack(con);
            throw new DBException("Cannot create a user", e);
        } finally {
            close(con);
            close(pstmt);
        }
    }

    public User getUserByEmail(String email) throws DBException {

        User user = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(DBConstatns.SQL_GET_USER_BY_EMAIL);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
//                user.setName(rs.getString("name"));
                user.setEmail(email);
                user.setCountry(rs.getString("country"));

            }

        } catch (SQLException e) {
            logger.error("Cannot get a user by email", e);
            throw new DBException("Cannot get a user by email", e);
        } finally {
            close(con);
            close(pstmt);
            close(rs);
        }

        return user;
    }



    private void close(AutoCloseable itemToBeClosed) {
        if (itemToBeClosed != null) {
            try {
                itemToBeClosed.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void rollBack(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException e) {
                logger.error("Cannot rollback transaction", e);
            }
        }
    }
}
