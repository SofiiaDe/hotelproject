package com.epam.javacourse.hotel.Exception;

public class HashPasswordException extends Exception{

    public HashPasswordException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
