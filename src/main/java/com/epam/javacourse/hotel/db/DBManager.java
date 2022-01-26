package com.epam.javacourse.hotel.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.tomcat.jdbc.pool.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;

public class DBManager {

    private static final Logger logger = LogManager.getLogger(DBManager.class);

    private static final String DATASOURCE_NAME = "jdbc/hoteldb";
    private static DataSource dataSource;
    private static DBManager instance;

    /**
     * singleton
     *
     * @return DBManager object
     */
    public static synchronized DBManager getInstance() {

        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create ConnectionPool.
     */
    private void init() throws Exception {
        createPool();
    }

    static {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup(DATASOURCE_NAME);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    Stack<Connection> freePool = new Stack<>();
    Set<Connection> occupiedPool = new HashSet<>();

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * returns Connection to the pool
     * @param con
     * @throws SQLException
     */
    public synchronized void returnConnection(Connection con) throws SQLException {
        if (con == null) {
            throw new NullPointerException();
        }
        if (!occupiedPool.remove(con)) {
            logger.error("The connection is returned already or it wasn't gotten from this pool");
            throw new SQLException(
                    "The connection is returned already or it wasn't gotten from this pool");
        }
        freePool.push(con);
    }

    /**
     * Gets utils properties.
     *
     * @return the databases properties
     * @throws IOException by failed or interrupted I/O operations.
     */
    private Properties getProperties() throws IOException {
        Properties props = new Properties();
        props.load(DBManager.class.getResourceAsStream("/db.properties"));
        return props;
    }

    private void createPool() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Properties props = getProperties();
        dataSource = new DataSource();
        dataSource.setUrl(props.getProperty("dbUrl"));
        dataSource.setUsername(props.getProperty("dbUser"));
        dataSource.setPassword(props.getProperty("dbPassword"));
        dataSource.setDriverClassName(props.getProperty("dbDriver"));
        dataSource.setMaxActive(100);
    }

}