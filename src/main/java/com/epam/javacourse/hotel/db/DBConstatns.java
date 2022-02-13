package com.epam.javacourse.hotel.db;

public class DBConstatns {

    private DBConstatns() {
        throw new IllegalStateException();
    }
    
    //QUERIES

    // UserDAO
    public static final String SQL_INSERT_USER = "INSERT INTO users (id, firstName, lastName, email, password, country, role) " +
            "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_GET_USER_BY_ID = "SELECT * FROM users u WHERE u.id = ?";
    public static final String SQL_GET_USERS_BY_IDS = "SELECT * FROM users u WHERE u.id IN (%s)";
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
    public static final String SQL_DELETE_APPLICATION_BY_ID = "DELETE FROM applications WHERE id =?";


    // RoomDAO
    public static final String SQL_GET_ALL_ROOMS = "SELECT * FROM rooms";
    public static final String SQL_UPDATE_ROOM = "UPDATE rooms SET price = ?, room_number = ?, room_seats = ?," +
            "room_class = ?, WHERE id = ?";
    public static final String SQL_GET_ROOM_BY_ID = "SELECT * FROM rooms WHERE id = ?";
    public static final String SQL_GET_ROOMS_BY_IDS = "SELECT * FROM rooms r WHERE r.id IN (%s)";
    public static final String SQL_GET_ROOMS_EXCEPT = "SELECT * FROM rooms r WHERE NOT(r.id IN (%s))";
    public static final String SQL_GET_AVAILABLE_ROOMS = "SELECT * FROM rooms r LEFT OUTER JOIN (SELECT DISTINCT(room_id) " +
            "FROM bookings b LEFT JOIN invoices i ON i.booking_id = b.id WHERE (b.checkin_date <= ? AND b.checkout_date >= ?) " +
            "AND i.status != 'cancelled') q ON q.room_id = r.id WHERE q.room_id IS null AND room_status = 'available' ";
    public static final String SQL_GET_ROOMS_BASIC_QUERY = "SELECT ?0? FROM rooms r LEFT OUTER JOIN (SELECT DISTINCT(room_id) " +
            "FROM bookings b LEFT JOIN invoices i ON i.booking_id = b.id WHERE (b.checkin_date <= ? AND b.checkout_date >= ?) " +
            "AND i.status ?1?) q ON q.room_id = r.id WHERE q.room_id IS ?2? null ";


    // BookingDAO
    public static final String SQL_CREATE_BOOKING = "INSERT INTO bookings " +
            "(id, user_id, checkin_date, checkout_date, room_id, application_id)\n" +
            "VALUES (DEFAULT, ?, ?, ?, ?, ?)";
    public static final String SQL_GET_BOOKINGS_BY_USER_ID = "SELECT * FROM bookings WHERE user_id = ?";
    public static final String SQL_GET_ALL_BOOKINGS = "SELECT * FROM bookings";
    public static final String SQL_GET_ALL_BOOKINGS_COUNT = "SELECT count(*) as cnt FROM bookings";
    public static final String SQL_GET_BOOKING_BY_ID = "SELECT * FROM bookings WHERE id = ?";
    public static final String SQL_DELETE_BOOKING_BY_ID = "DELETE FROM bookings WHERE ID = ?";
    public static final String SQL_GET_BOOKING_ROOMS_BY_DATE = "SELECT id, room_id FROM bookings WHERE checkin_date <= ? and checkout_date >= ?";

    // ConfirmRequestDAO
    public static final String SQL_CREATE_CONFIRM_REQUEST = "INSERT INTO confirmation_requests " +
            "(id, user_id, application_id, room_id, confirm_request_date, status) VALUES\n" +
            "(DEFAULT, ?, ?, ?, ?, 'new')";
    public static final String SQL_GET_CONFIRM_REQUESTS_BY_USER_ID = "SELECT * FROM confirmation_requests WHERE user_id = ?";
    public static final String SQL_GET_ALL_CONFIRM_REQUESTS = "SELECT * FROM confirmation_requests";
    public  static final String SQL_DELETE_CONFIRM_REQUEST_BY_ID = "DELETE FROM confirmation_requests WHERE id = ?";
    public static final String SQL_GET_CONFIRM_REQUEST_BY_ID = "SELECT * FROM confirmation_requests WHERE id = ?";
    public static final String SQL_UPDATE_CONFIRM_REQUEST_STATUS = "UPDATE confirmation_requests cf SET cf.status = ? WHERE cf.id = ?";


    // InvoiceDAO
    public static final String SQL_CREATE_INVOICE = "INSERT INTO invoices " +
            "(id, user_id, amount, booking_id, invoice_date, status)\n" +
            "VALUES (DEFAULT, ?, ?, ?, ?, ?)";
    public static final String SQL_GET_ALL_INVOICES = "SELECT * FROM invoices";
    public static final String SQL_GET_INVOICES_BY_USER_ID = "SELECT * FROM invoices WHERE user_id = ?";
    public static final String SQL_GET_CANCELLED_INVOICE_BOOKING_IDS_BY_BOOKING_ID =
            "SELECT booking_id FROM invoices i WHERE booking_id IN (%s) and i.status = 'cancelled'";
    public static final String SQL_UPDATE_INVOICE = "UPDATE invoices SET user_id = ?, amount =?, booking_id = ?, " +
            "invoice_date = ?, status = ? WHERE id = ?";
    public static final String SQL_UPDATE_INVOICE_STATUS = "UPDATE invoices i SET i.status = ? WHERE i.id = ?";
    public static final String SQL_GET_INVOICES_BY_STATUS = "SELECT * FROM invoices i WHERE i.status = ?";
    public static final String SQL_GET_INVOICE_BY_ID = "SELECT * FROM invoices WHERE ID = ?";

    //FIELDS
    public static final String F_USER_NAME = "name";

}
