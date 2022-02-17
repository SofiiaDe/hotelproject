package com.epam.javacourse.hotel.exception;

public class HashPasswordException extends Exception{

    public HashPasswordException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
