package com.epam.javacourse.hotel.model.service;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.db.BookingDAO;
import com.epam.javacourse.hotel.db.InvoiceDAO;
import com.epam.javacourse.hotel.db.RoomDAO;
import com.epam.javacourse.hotel.db.models.BookingRoomIdModel;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Room;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class RoomServiceImpl implements IRoomService{

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

    // todo old method, refactor or delete
    @Override
    public List<Room> getCurrentlyFreeRooms() throws DBException {
        IBookingService bookingService = AppContext.getInstance().getBookingService();
        IRoomService roomService = AppContext.getInstance().getRoomService();

        List<Room> allRoomsList = roomService.allRoomsList();

        // get list of booked rooms
        List<Booking> allBookings = bookingService.getAllBookings();

        List<Integer> bookedRoomsId = allBookings.stream()
                .map(Booking::getRoomId)
                .collect(Collectors.toList());

        List<Room> bookedRooms = roomService.getRoomsByIds(bookedRoomsId);

        // + occupied + unavailable

        // get list of free rooms
        List<Room> freeRooms = allRoomsList.stream()
                .filter(room -> !bookedRooms.contains(room))
//                .filter(room -> !occupiedRooms.contains(room))
                .collect(Collectors.toList());


        return freeRooms;
    }

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

        var availableRooms = roomDAO.getAvailableRoomsExcept(mapBookingRoomIdModelToRoomIds(bookedRooms));

        return availableRooms;
    }

    private static List<Integer> mapBookingRoomIdModelToRoomIds(List<BookingRoomIdModel> bookedRooms){
        return bookedRooms.stream().map(BookingRoomIdModel::getRoomId).collect(Collectors.toList());
    }
}
