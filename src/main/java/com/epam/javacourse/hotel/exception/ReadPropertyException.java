package com.epam.javacourse.hotel.exception;

public class ReadPropertyException extends Exception{
    public ReadPropertyException(String errorMassage, Throwable error) {
        super(errorMassage, error);
    }

    public ReadPropertyException(String errorMessage) {
        super(errorMessage);
    }

}
