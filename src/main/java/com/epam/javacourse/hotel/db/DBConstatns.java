package com.epam.javacourse.hotel.db;

public class DBConstatns {
    
    //QUERIES

    // UserDAO
    public static final String SQL_INSERT_USER = "INSERT INTO users (id, firstName, lastName, email, password, country, role) " +
            "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_GET_USER_BY_ID = "SELECT * FROM users u WHERE u.id = ?";
    public static final String SQL_GET_USER_BY_EMAIL = "SELECT id, firstName, lastName, email, password, country, " +
            "role FROM users WHERE email=?";
    public static final String SQL_GET_ALL_USERS = "SELECT * FROM users";
    public static final String SQL_DELETE_USER = "DELETE FROM users WHERE email = ?";
    public static final String SQL_UPDATE_USER = "UPDATE users SET name = ?, email = ?, country = ? WHERE id = ?";
    public static final String SQL_GET_USER_APPLICATIONS =
            "SELECT t.id, t.room_seats, t.room_class, t.checkin_date, t.checkout_date FROM applications AS t JOIN "
                    + "user_has_applications AS uha ON t.id = uha.applications_id AND uha.users_id = "
                    + "(SELECT id FROM users WHERE id = ?)";
    public static final String SQL_GET_USER_ALL_BOOKED_ROOMS =
            "SELECT t.id, t.room_seats, t.room_class, t.room_status FROM rooms AS t JOIN "
                    + "user_has_booked_rooms AS uhbr ON t.id = uhbr.rooms_id AND uhbr.users_id = "
                    + "(SELECT id FROM users WHERE id = ?)";

    // ApplicationDAO
    public static final String SQL_CREATE_APPLICATION = "INSERT INTO applications " +
            "(id, user_id, room_seats, room_class, checkin_date, checkout_date)\n" +
            "VALUES (DEFAULT, ?, ?, ?, ?, ?)";
    public static final String SQL_GET_ALL_APPLICATIONS = "SELECT * FROM applications";
    public static final String SQL_GET_APPLICATIONS_BY_USER_ID = "SELECT * FROM applications WHERE user_id = ?";
    public static final String SQL_UPDATE_APPLICATION = "UPDATE applications SET user_id = ?, room_seats = ?, " +
            "room_class = ?, checkin_date = ?, checkout_date = ? WHERE id = ?";
    public static final String SQL_GET_APPLICATION_BY_ID = "SELECT * FROM applications WHERE id = ?";

    // RoomDAO
    public static final String SQL_GET_ALL_ROOMS = "SELECT * FROM rooms";
    public static final String SQL_UPDATE_ROOM = "UPDATE rooms SET price = ?, room_number = ?, room_seats = ?," +
            "room_class = ?, room_status =? WHERE id = ?";

    // BookingDAO
    public static final String SQL_CREATE_BOOKING = "INSERT INTO bookings " +
            "(id, user_id, checkin_date, checkout_date, room_id, application_id)\n" +
            "VALUES (DEFAULT, ?, ?, ?, ?, ?)";
    public static final String SQL_GET_BOOKINGS_BY_USER_ID = "SELECT * FROM bookings WHERE user_id = ?";


    // ConfirmRequestDAO
    public static final String SQL_CREATE_CONFIRM_REQUEST = "INSERT INTO confirmation_requests " +
            "(id, user_id, application_id, room_id, status) VALUES\n" +
            "(DEFAULT, ?, ?, ?, 'new')";
    public static final String SQL_GET_CONFIRM_REQUESTS_BY_USER_ID = "SELECT * FROM confirmation_requests WHERE user_id = ?";


    //FIELDS
    public static final String F_USER_NAME = "name";

}
