package com.epam.javacourse.hotel;

import com.epam.javacourse.hotel.Exception.ReadPropertyException;
import com.epam.javacourse.hotel.db.UserDAO;
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
                throw new ReadPropertyException("File was not found " + propertyFile);
            }
            Properties prop = new Properties();
            prop.load(inputStream);
            String property =  prop.getProperty(propertyToRead);
            if (property == null) {
                throw new ReadPropertyException("Property was not found " + propertyToRead);
            }
            return property;
        } catch (IOException e) {
            throw new ReadPropertyException("Property was not found " + propertyToRead, e);
        }
    }
}
