package services;

import com.epam.javacourse.hotel.db.dao.RoomDAO;
import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.Room;
import com.epam.javacourse.hotel.model.service.impl.RoomServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {

    @Mock
    private RoomDAO roomDAOMock;

    @Test
    void testCreate_whenDaoThrows_throwsException() throws DBException {
        when(roomDAOMock.createRoom(any(Room.class))).thenThrow(new DBException());
        RoomServiceImpl roomService = new RoomServiceImpl(roomDAOMock);

        Assertions.assertThrowsExactly(AppException.class, () -> roomService.create(new Room()));
    }

    @Test
    void testCreate_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        when(roomDAOMock.createRoom(any(Room.class))).thenThrow(new DBException(messageNotToGet));
        RoomServiceImpl roomService = new RoomServiceImpl(roomDAOMock);

        try {
            roomService.create(new Room());
        }catch(AppException ex){
            assertEquals("Can't create room", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testCreate_whenCalled_callsDAO() throws AppException {
        RoomServiceImpl roomService = new RoomServiceImpl(roomDAOMock);

        roomService.create(new Room());
        verify(roomDAOMock, times(1)).createRoom(any(Room.class));
    }

    @Test
    void testGetAllRooms_whenCalled_callsDAO() throws AppException {
        RoomServiceImpl roomService = new RoomServiceImpl(roomDAOMock);

        roomService.getAllRooms();
        verify(roomDAOMock, times(1)).findAllRooms();
    }

    @Test
    void testGetAllRooms_whenDaoThrows_throwsException() throws DBException {
        when(roomDAOMock.findAllRooms()).thenThrow(new DBException());
        RoomServiceImpl roomService = new RoomServiceImpl(roomDAOMock);

        Assertions.assertThrowsExactly(AppException.class, roomService::getAllRooms);
    }

    @Test
    void testGetAllRooms_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        when(roomDAOMock.findAllRooms()).thenThrow(new DBException(messageNotToGet));
        RoomServiceImpl roomService = new RoomServiceImpl(roomDAOMock);

        try {
            roomService.getAllRooms();
        }catch(AppException ex){
            assertEquals("Can't retrieve all rooms", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testUpdateRoom_whenUpdate_returnTrue() throws AppException {
        Room room = new Room();
        RoomServiceImpl roomService = new RoomServiceImpl(roomDAOMock);
        when(roomDAOMock.updateRoom(room)).thenReturn(true);
        boolean result = roomService.updateRoom(room);

        Assertions.assertTrue(result);
    }

    @Test
    void testUpdateRoom_whenCalled_DAOCalled() throws AppException {
        Room room = new Room();
        RoomServiceImpl roomService = new RoomServiceImpl(roomDAOMock);

        roomService.updateRoom(room);

        verify(roomDAOMock, times(1)).updateRoom(room);
    }

    @Test
    void testUpdateRoom_whenDaoThrows_throwsException() throws AppException {
        when(roomDAOMock.updateRoom(any(Room.class))).thenThrow(new DBException());
        RoomServiceImpl roomService = new RoomServiceImpl(roomDAOMock);

        Assertions.assertThrowsExactly(AppException.class, () -> roomService.updateRoom(new Room()));
    }

    @Test
    void testUpdateRoom_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        when(roomDAOMock.updateRoom(any(Room.class))).thenThrow(new DBException(messageNotToGet));
        RoomServiceImpl roomService = new RoomServiceImpl(roomDAOMock);

        try {
            roomService.updateRoom(new Room());
        }catch(AppException ex){
            assertEquals("Can't update room", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetRoomById_whenCalled_DAOCalled() throws AppException {
        int roomId = 112567890;
        RoomServiceImpl roomService = new RoomServiceImpl(roomDAOMock);

        roomService.getRoomById(roomId);

        verify(roomDAOMock, times(1)).findRoomById(roomId);
    }

    @Test
    void testGetRoomById_whenDaoThrows_throwsException() throws AppException {
        int roomId = 112567890;

        when(roomDAOMock.findRoomById(roomId)).thenThrow(new DBException());
        RoomServiceImpl roomService = new RoomServiceImpl(roomDAOMock);

        Assertions.assertThrowsExactly(AppException.class, () -> roomService.getRoomById(roomId));
    }

    @Test
    void testGetRoomById_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        int roomId = 112567890;

        when(roomDAOMock.findRoomById(roomId)).thenThrow(new DBException(messageNotToGet));
        RoomServiceImpl roomService = new RoomServiceImpl(roomDAOMock);

        try {
            roomService.getRoomById(roomId);
        }catch(AppException ex){
            assertEquals("Can't retrieve room by id", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetRoomById_whenCalled_returnsCorrectRoom() throws AppException {
        int roomId = 112567890;
        Room expectedRoom = new Room();
        expectedRoom.setRoomTypeBySeats("have a nice day");
        expectedRoom.setRoomClass("classy room");
        expectedRoom.setId(roomId);
        when(roomDAOMock.findRoomById(roomId)).thenReturn(expectedRoom);
        RoomServiceImpl roomService = new RoomServiceImpl(roomDAOMock);

        Room result = roomService.getRoomById(roomId);

        assertEquals(result, expectedRoom);
    }

    @Test
    void testGetRoomsByIds_whenCalled_DAOCalled() throws AppException {
        int roomId = 112567890;
        RoomServiceImpl roomService = new RoomServiceImpl(roomDAOMock);

        roomService.getRoomById(roomId);

        verify(roomDAOMock, times(1)).findRoomById(roomId);
    }

    @Test
    void testGetRoomsByIds_whenDaoThrows_throwsException() throws AppException {
        List<Room> rooms = getRooms();
        List<Integer> roomIds = rooms.stream().map(Room::getId).collect(Collectors.toList());

        when(roomDAOMock.findRoomsByIds(roomIds)).thenThrow(new DBException());
        RoomServiceImpl roomService = new RoomServiceImpl(roomDAOMock);

        Assertions.assertThrowsExactly(AppException.class, () -> roomService.getRoomsByIds(roomIds));
    }

    @Test
    void testGetRoomsByIds_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        List<Room> rooms = getRooms();
        List<Integer> roomIds = rooms.stream().map(Room::getId).collect(Collectors.toList());

        when(roomDAOMock.findRoomsByIds(roomIds)).thenThrow(new DBException(messageNotToGet));
        RoomServiceImpl roomService = new RoomServiceImpl(roomDAOMock);

        try {
            roomService.getRoomsByIds(roomIds);
        }catch(AppException ex){
            assertEquals("Can't retrieve rooms by ids", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetRoomsByIds_whenCalled_returnsCorrectRoom() throws AppException {
        List<Room> rooms = getRooms();
        List<Integer> roomIds = rooms.stream().map(Room::getId).collect(Collectors.toList());

        when(roomDAOMock.findRoomsByIds(roomIds)).thenReturn(rooms);
        RoomServiceImpl roomService = new RoomServiceImpl(roomDAOMock);

        List<Room> result = roomService.getRoomsByIds(roomIds);

        assertEquals(result, rooms);
    }

    @Test
    void testChooseSuitableRoomForRequest_roomTypeAndRoomClassCase() {
        List<Room> freeRooms = getRooms();
        List<Application> applications = getApplications();

        RoomServiceImpl roomService = new RoomServiceImpl(roomDAOMock);
        Room result = roomService.chooseSuitableRoomForRequest(applications.get(0), freeRooms);
        assertEquals(freeRooms.get(0), result);
    }

    @Test
    void testChooseSuitableRoomForRequest_roomTypeCase() {
        List<Room> freeRooms = getRooms();
        List<Application> applications = getApplications();

        RoomServiceImpl roomService = new RoomServiceImpl(roomDAOMock);
        Room result = roomService.chooseSuitableRoomForRequest(applications.get(1), freeRooms);
        assertEquals(freeRooms.get(1), result);
    }

    @Test
    void testChooseSuitableRoomForRequest_roomClassCase() {
        List<Room> freeRooms = getRooms();
        List<Application> applications = getApplications();

        RoomServiceImpl roomService = new RoomServiceImpl(roomDAOMock);
        Room result = roomService.chooseSuitableRoomForRequest(applications.get(2), freeRooms);
        assertEquals(freeRooms.get(2), result);
    }

    private List<Room> getRooms() {
        // Room dao
        List<Room> roomDb = new ArrayList<>();
        Room room1 = new Room();
        room1.setId(111);
        room1.setRoomTypeBySeats("double");
        room1.setRoomClass("business");
        room1.setRoomNumber(77);
        room1.setPrice(new BigDecimal("250.00"));

        Room room2 = new Room();
        room2.setId(222);
        room2.setRoomTypeBySeats("twin");
        room2.setRoomClass("standard");
        room2.setRoomNumber(88);
        room2.setPrice(new BigDecimal("200.00"));

        Room room3 = new Room();
        room3.setId(333);
        room3.setRoomTypeBySeats("single");
        room3.setRoomClass("lux");
        room3.setRoomNumber(77);
        room3.setPrice(new BigDecimal("300.00"));

        roomDb.add(room1);
        roomDb.add(room2);
        roomDb.add(room3);

        return roomDb;
    }

    private List<Application> getApplications() {
        List<Application> applications = new ArrayList<>();

        Application app1 = new Application();
        app1.setId(111);
        app1.setCheckinDate(LocalDate.now().plusDays(5));
        app1.setCheckoutDate(LocalDate.now().plusDays(6));
        app1.setRoomTypeBySeats("double");
        app1.setUserId(1);
        app1.setRoomClass("business");

        Application app2 = new Application();
        app2.setId(222);
        app2.setCheckinDate(LocalDate.now().plusDays(1));
        app2.setCheckinDate(LocalDate.now().plusDays(3));
        app2.setRoomTypeBySeats("twin");
        app2.setUserId(1);
        app2.setRoomClass("standard");

        Application app3 = new Application();
        app3.setId(333);
        app3.setCheckinDate(LocalDate.now().plusDays(4));
        app3.setCheckinDate(LocalDate.now().plusDays(5));
        app3.setRoomTypeBySeats("triple");
        app3.setUserId(1);
        app3.setRoomClass("lux");

        applications.add(app1);
        applications.add(app2);
        applications.add(app3);

        return applications;
    }
}
