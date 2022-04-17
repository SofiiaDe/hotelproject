package com.epam.javacourse.hotel.exception;

/**
 * An exception which provides information on an exception when the properties can't be read.
 */
public class ReadPropertyException extends Exception {
    public ReadPropertyException(String errorMassage, Throwable error) {
        super(errorMassage, error);
    }

    public ReadPropertyException(String errorMessage) {
        super(errorMessage);
    }

}
