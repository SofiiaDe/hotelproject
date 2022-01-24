package com.epam.javacourse.hotel.db;

import com.epam.javacourse.hotel.model.User;

import java.sql.*;


import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static UserDAO instance;

    private static final String dbUrl = "jdbc:mysql://localhost:3306/userdb?useSSL=false";
    private static final String dbUser = "root";
    private static final String dbPassword = "root";
    private static final String dbDriver = "com.mysql.cj.jdbc.Driver";

    public UserDAO() {
    }

    /**
     * singleton
     *
     * @return DBManager object
     */
    public static synchronized UserDAO getInstance() {

        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(dbDriver);
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    public List<User> findAllUsers() {

    List<User> allUsersList = new ArrayList<>();
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

        try

    {
        con = getConnection();

        stmt = con.createStatement();
        rs = stmt.executeQuery(DBConstatns.SQL_GET_ALL_USERS);
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setCountry(rs.getString("country"));
            allUsersList.add(user);
        }
    } catch(
    SQLException e)

    {
        e.printStackTrace();
    } finally

    {
        close(con);
        close(stmt);
        close(rs);
    }

        return allUsersList;
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
}
