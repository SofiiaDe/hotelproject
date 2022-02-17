package com.epam.javacourse.hotel.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);

    private static ConnectionPool dbPool;
    private DataSource dataSource;

    private ConnectionPool() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/hotelDataBase");
        } catch (NamingException e) {
            logger.error("Cannot initialize DB connection", e);
        }
    }

    /**
     * singleton
     *
     * @return ConnectionPool object
     */
    public static synchronized ConnectionPool getInstance() {
        if (dbPool == null) {
            dbPool = new ConnectionPool();
        }
        return dbPool;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


}