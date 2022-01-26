USE
hoteldb;

CREATE TABLE users
(
    id      INT PRIMARY KEY auto_increment,
    name    VARCHAR(20),
    email   VARCHAR(20) UNIQUE,
    country VARCHAR(20)
);

CREATE TABLE rooms
(
    id          INT PRIMARY KEY auto_increment,
    price       DECIMAL(10, 2) NOT NULL,
    room_number INT UNIQUE,
    room_seats  ENUM ('single', 'double', 'triple', 'twin'),
    room_class  ENUM ('standard', 'business', 'lux'),
    room_status ENUM ('free', 'booked', 'occupied', 'unavailable'),
    KEY `price` (`price`),
    KEY `room_seats` (`room_seats`),
    KEY `room_class` (`room_class`)

);

CREATE TABLE applications
(
    id            INT PRIMARY KEY auto_increment,
    user_id INT,
    room_seats    ENUM('single', 'double', 'triple', 'twin'),
    room_class    ENUM('standard', 'business', 'lux'),
    checkin_date  DATE,
    checkout_date DATE,
    CONSTRAINT FK_1 FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    KEY `checkin_date` (`checkin_date`),
    KEY `checkout_date` (`checkout_date`)
);

CREATE TABLE confirmation_requests
(
    id            INT PRIMARY KEY auto_increment,
    user_id        INT,
    application_id INT,
    room_id INT,
    status ENUM('new', 'confirmed'),
    CONSTRAINT FK_2 FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT FK_3 FOREIGN KEY (application_id) REFERENCES applications (id) ON DELETE CASCADE,
    CONSTRAINT FK_4 FOREIGN KEY (room_id) REFERENCES rooms (id) ON DELETE CASCADE

);

CREATE TABLE bookings
(
    id          INT PRIMARY KEY auto_increment,
    user_id        INT,
    checkin_date  DATE,
    checkout_date DATE,
    room_id INT,
    application_id INT,
    CONSTRAINT FK_5 FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT FK_6 FOREIGN KEY (checkin_date) REFERENCES applications (checkin_date) ON DELETE CASCADE,
    CONSTRAINT FK_7 FOREIGN KEY (checkout_date) REFERENCES applications (checkout_date) ON DELETE CASCADE,
    CONSTRAINT FK_8 FOREIGN KEY (room_id) REFERENCES rooms (id) ON DELETE CASCADE,
    CONSTRAINT FK_9 FOREIGN KEY (application_id) REFERENCES applications (id) ON DELETE CASCADE

);

CREATE TABLE invoices
(
    id          INT PRIMARY KEY auto_increment,
    user_id        INT,
    amount DECIMAL(10, 2) NOT NULL,
    booking_id INT,
    invoice_date DATE,
    status ENUM('new', 'paid', 'cancelled'),
    CONSTRAINT FK_10 FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT FK_11 FOREIGN KEY (booking_id) REFERENCES bookings (id) ON DELETE CASCADE

);

INSERT INTO users (name, email, country)
VALUES ('Ivanov Ivan', 'ivanov.i@gmail.com', 'Ukraine'),
       ('Taylor Jim', 'taylor.j@gmail.com', 'USA'),
       ('Nowak Joanna', 'nowak.j@gmail.com', 'Poland'),
       ('Boyko Mariana', 'boyko.m@gmail.com', 'Ukraine'),
       ('Garcia Hugo', 'garcia.h@gmail.com', 'Spain'),
       ('Meyer Klaus', 'meyer.k@gmail.com', 'Germany');

INSERT INTO applications (room_seats, room_class, checkin_date, checkout_date)
VALUES ('double', 'business', '2022-03-01', '2022-03-04'),
       ('single', 'standard', '2022-03-11', '2022-03-12'),
       ('double', 'business', '2022-03-05', '2022-03-06'),
       ('single', 'lux', '2022-03-16', '2022-03-17'),
       ('twin', 'standard', '2022-03-23', '2022-03-29'),
       ('triple', 'business', '2022-03-20', '2022-03-21');