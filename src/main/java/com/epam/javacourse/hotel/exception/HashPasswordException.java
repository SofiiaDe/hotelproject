package com.epam.javacourse.hotel.exception;

/**
 * An exception which provides information on an exception when the password is hashed.
 */
public class HashPasswordException extends Exception{

    public HashPasswordException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
