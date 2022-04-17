package com.epam.javacourse.hotel.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * DataSource connection pool.
 */
public class ConnectionPool {

    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);

    private static volatile ConnectionPool dbPool;
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
     * An implementation of double-checked locking of Singleton.
     * Intention is to minimize cost of synchronization and improve performance,
     * by only locking critical section of code, the code which creates instance of Singleton class.
     * By the way this is still broken, if we don't make _instance volatile, as another thread can * see a half initialized instance of Singleton.
     *
     *
     * @return ConnectionPool object
     */
    public static ConnectionPool getInstance() {
        if (dbPool == null) {
            synchronized (ConnectionPool.class) {
                if (dbPool == null) {
                    dbPool = new ConnectionPool();
                }
            }
        }
        return dbPool;
    }


    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


}