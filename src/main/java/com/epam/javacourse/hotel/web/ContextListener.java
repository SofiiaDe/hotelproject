package com.epam.javacourse.hotel.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Servlet Context Listener.
 */
public class ContextListener implements ServletContextListener {

    private static final Logger logger = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log("Servlet Context initialization started");

        ServletContext servletContext = sce.getServletContext();
        initCommandFactory();
        initI18n(servletContext);

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

    /**
     * Initializes i18n subsystem.
     */
    private void initI18n(ServletContext servletContext) {
        logger.debug("i18n subsystem initialization started");

        String localesName = servletContext.getInitParameter("locales");
        if (localesName == null || localesName.isEmpty()) {
            logger.warn("'locales' init parameter is empty, the default encoding will be used");
        } else {
            List<String> locales = Arrays.stream(localesName.split(" ")).collect(Collectors.toList());

            logger.debug("Application attribute set: locales --> {}", locales);

            servletContext.setAttribute("locales", locales);
        }

        logger.debug("I18n subsystem initialization finished");
    }

    private void log(String message) {
        System.out.println("[ContextListener] " + message);
    }
}
