package com.epam.javacourse.hotel.Exception;

/**
 * An exception which provides information on an application error.
 */
public class AppException extends Exception{

//    private static final long serialVersionUID =

    public AppException() {
        super();
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(String message) {
        super (message);
    }
}