package com.epam.javacourse.hotel.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;

public class DBManager {

    private static final Logger logger = LogManager.getLogger(DBManager.class);

    private static DBManager dbPool;
    private DataSource dataSource;

    private DBManager() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/hotelDataBase");
        } catch (NamingException e) {
            logger.error("Cannot initialize DB connection", e);
        }
    }

    public static synchronized DBManager getInstance() {
        if (dbPool == null) {
            dbPool = new DBManager();
        }
        return dbPool;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


}