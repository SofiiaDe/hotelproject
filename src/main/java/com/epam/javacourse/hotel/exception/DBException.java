package com.epam.javacourse.hotel.exception;

/**
 *  * An exception which provides information on a database error.
 */
public class DBException extends AppException{

    //    private static final long serialVersionUID =

    public DBException() {
        super();
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBException(String message) {
        super(message);
    }
}
