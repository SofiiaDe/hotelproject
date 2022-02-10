package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.db.BookingDAO;
import com.epam.javacourse.hotel.db.InvoiceDAO;
import com.epam.javacourse.hotel.db.RoomDAO;
import com.epam.javacourse.hotel.db.models.BookingRoomIdModel;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Room;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class RoomServiceImpl implements IRoomService {

    private final RoomDAO roomDAO;
    private BookingDAO bookingDAO;
    private InvoiceDAO invoiceDAO;

    public RoomServiceImpl(RoomDAO roomDAO, BookingDAO bookingDAO, InvoiceDAO invoiceDAO) {
        this.roomDAO = roomDAO;
        this.bookingDAO = bookingDAO;
        this.invoiceDAO = invoiceDAO;
    }

    @Override
    public List<Room> allRoomsList() throws DBException {
        return this.roomDAO.getAllRooms();
    }

    @Override
    public boolean updateRoom(Room room) throws DBException {
        return this.roomDAO.updateRoom(room);
    }

    @Override
    public Room getRoomById(int roomId) throws DBException {
        return this.roomDAO.getRoomById(roomId);
    }

    @Override
    public List<Room> getRoomsByIds(List<Integer> ids) throws DBException {
        return this.roomDAO.getRoomsByIds(ids);
    }


    /**
     * @return list of rooms available for booking
     */
    @Override
    public List<Room> getFreeRoomsForPeriod(LocalDate checkinDate, LocalDate checkoutDate) throws DBException, IllegalArgumentException {
        if (checkinDate.isAfter(checkoutDate) || checkoutDate.isEqual(checkinDate)){
            throw new IllegalArgumentException("Check-in and check-out dates are overlapping or equal");
        }

        List<BookingRoomIdModel> bookedRooms
                = bookingDAO.getBookedRoomIdsByDates(checkinDate, checkoutDate);

        List<Integer> cancelledBookings = invoiceDAO.findCancelledInvoicedBookingIds(mapBookingRoomIdModelToRoomIds(bookedRooms));

        for (int bookingId: cancelledBookings ) {
            bookedRooms.removeIf(i -> i.getBookingId() == bookingId);
        }

        return roomDAO.getAvailableRoomsExcept(mapBookingRoomIdModelToRoomIds(bookedRooms));
    }

    private static List<Integer> mapBookingRoomIdModelToRoomIds(List<BookingRoomIdModel> bookedRooms){
        return bookedRooms.stream().map(BookingRoomIdModel::getRoomId).collect(Collectors.toList());
    }

    /**
     * returns a Room which is the most suitable according to the Client's criteria specified in the application
     *
     * @param application
     * @param freeRooms
     * @return
     */
    @Override
    public Room chooseSuitableRoomForRequest(Application application, List<Room> freeRooms) throws DBException {

        Room suitableRoom = null;

        for (Room freeRoom : freeRooms) {
            if ((application.getRoomTypeBySeats().equals(freeRoom.getRoomTypeBySeats()))
                    && (application.getRoomClass().equals(freeRoom.getRoomClass()))) {
                return freeRoom;
            } else if (application.getRoomTypeBySeats().equals(freeRoom.getRoomTypeBySeats())) {
                return freeRoom;
            } else if (application.getRoomClass().equals(freeRoom.getRoomClass())) {
                return freeRoom;
            } else {
                suitableRoom = freeRoom;
            }

        }

        return suitableRoom;

    }
}
