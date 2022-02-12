package com.epam.javacourse.hotel;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final Logger logger = LogManager.getLogger(Validator.class);


    private static final String FIELD = "field \"";
    private static final String NOT_EMPTY = "\" cannot be empty";
    private static final String TOO_LONG = "\" is too long";
    private static final String INVALID_SYMBOLS = "\" has invalid symbols";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";


    /**
     *
     * @param email is email entered by a user
     * @param length is max length of the email variable in a database
     * @return message to a user explaining incorrect input or null if validation was successful
     */
    public static String validateEmail(String email, int length) {


        String emailRegex =
                "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        if (email == null) {
            return FIELD + EMAIL + NOT_EMPTY;

        } else if (email.length() > length) {
            return FIELD + EMAIL + TOO_LONG;
        }
        if (Pattern.compile(emailRegex)
                .matcher(email)
                .matches()) {
            return null;
        }
        return FIELD + EMAIL + INVALID_SYMBOLS;
    }


    /**
     *
     * @param password is password entered by a user
     * @param minLength is min length of the password established in a regex
     * @param maxLength is max length of the password variable in a database
     * @return message to a user explaining incorrect input or null if validation was successful
     */
    public static String validatePassword(String password, int minLength, int maxLength) {

        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[{}\\]:;',?\\*~$^+=<>]).{8,20}$";

        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);

        if (password.length() == 0) {
            return FIELD + PASSWORD + NOT_EMPTY;
        } else if (password.length() > maxLength || password.length() < 8 || !matcher.find()) {
            return FIELD + PASSWORD +
                    " must contain at least one digit, at least one lowercase and one uppercase Latin characters " +
                    "as well as at least one special character like ! @ # & ( ) etc.\n " +
                    "Password must contain a length of at least 8 characters and a maximum of 20 characters.";
        }
        return null;
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public static LocalDate dateParameterToLocalDate(String date) {
        LocalDate parsedDate = null;
        try {
            parsedDate = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            logger.error("Cannot get date type", e);
        }
        return parsedDate;
    }


}

