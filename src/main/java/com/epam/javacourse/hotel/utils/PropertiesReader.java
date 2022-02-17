package com.epam.javacourse.hotel.utils;

import com.epam.javacourse.hotel.exception.ReadPropertyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    private static final Logger logger = LogManager.getLogger(PropertiesReader.class);


    private PropertiesReader() {
    }
    /**
     * Read props key
     * @param propertyFile property file container
     * @param propertyToRead property to be read
     * @return property
     * @throws ReadPropertyException
     */
    public static String readProps(String propertyFile, String propertyToRead) throws ReadPropertyException {
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(propertyFile)) {
            if (inputStream == null) {
                String errorMessage = "Can't read properties because file " + propertyFile + " was not found.";
                logger.error(errorMessage);
                throw new ReadPropertyException(errorMessage);
            }
            Properties prop = new Properties();
            prop.load(inputStream);
            String property =  prop.getProperty(propertyToRead);
            if (property == null) {
                String errorMessage = "Can't read properties because property " + propertyToRead + " was not found.";
                logger.error(errorMessage);
                throw new ReadPropertyException(errorMessage);
            }
            return property;
        } catch (IOException e) {
            String errorMessage = "Can't read properties because property " + propertyToRead + " was not found.";
            logger.error(errorMessage);
            throw new ReadPropertyException(errorMessage, e);
        }
    }
}
