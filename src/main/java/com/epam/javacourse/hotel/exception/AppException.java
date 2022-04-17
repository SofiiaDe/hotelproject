package com.epam.javacourse.hotel.exception;

/**
 * An exception which provides information on an application error.
 */
public class AppException extends Exception{

    public AppException() {
        super();
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(String message) {
        super (message);
    }

    public AppException(Exception exception){
        super(exception);
    }
}