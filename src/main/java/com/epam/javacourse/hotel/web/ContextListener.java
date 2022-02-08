package com.epam.javacourse.hotel.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


/**
 * Servlet Context Listener.
 */
public class ContextListener implements ServletContextListener {

    private static final Logger logger = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log("Servlet Context initialization started");

        initCommandFactory();

        log("Servlet Context initialization finished");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log("Servlet Context destruction starts");
        log("Servlet Context destruction finished");
    }

    /**
     * Initializes CommandFactory.
     */
    private void initCommandFactory() {
        logger.debug("ICommand container initialization started");

        // initialize commands factory, just load class to JVM
        try {
            Class.forName("com.epam.javacourse.hotel.web.command.CommandFactory");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot initialize CommandFactory", e);
        }

        logger.debug("ICommand container initialization finished");
    }

    private void log(String message) {
        System.out.println("[ContextListener] " + message);
    }
}
