package services;

import com.epam.javacourse.hotel.db.dao.BookingDAO;
import com.epam.javacourse.hotel.db.dao.InvoiceDAO;
import com.epam.javacourse.hotel.db.dao.UserDAO;
import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.service.impl.BookingServiceImpl;
import com.epam.javacourse.hotel.model.serviceModels.BookingDetailed;
import com.epam.javacourse.hotel.shared.models.BookingStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.epam.javacourse.hotel.shared.models.BookingStatus.NEW;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private BookingDAO bookingDAOMock;

    @Mock
    private UserDAO userDAOMock;

    @Mock
    private InvoiceDAO invoiceDAOMock;

    @Test
    void testCreate_whenDaoThrows_throwsException() throws DBException {
        when(bookingDAOMock.createBooking(any(Booking.class))).thenThrow(new DBException());
        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDAOMock, null, null, null);

        Assertions.assertThrowsExactly(AppException.class, () -> bookingService.create(new Booking()));
    }

    @Test
    void testCreate_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        when(bookingDAOMock.createBooking(any(Booking.class))).thenThrow(new DBException(messageNotToGet));
        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDAOMock, null, null, null);

        try {
            bookingService.create(new Booking());
        }catch(AppException ex){
            Assertions.assertEquals("Can't create new booking", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testCreate_whenCalled_callsDAO() throws AppException {
        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDAOMock, null, null, null);

        bookingService.create(new Booking());
        verify(bookingDAOMock, times(1)).createBooking(any(Booking.class));
    }

    @Test
    void testGetAllBookings_whenCalled_callsDAO() throws AppException {
        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDAOMock, null, null, null);

        bookingService.getAllBookings();
        verify(bookingDAOMock, times(1)).findAllBookings();
    }

    @Test
    void testGetAllBookings_whenDaoThrows_throwsException() throws DBException {
        when(bookingDAOMock.findAllBookings()).thenThrow(new DBException());
        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDAOMock, null, null, null);

        Assertions.assertThrowsExactly(AppException.class, bookingService::getAllBookings);
    }

    @Test
    void testGetAllBookings_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        when(bookingDAOMock.findAllBookings()).thenThrow(new DBException(messageNotToGet));
        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDAOMock, null, null, null);

        try {
            bookingService.getAllBookings();
        }catch(AppException ex){
            Assertions.assertEquals("Can't retrieve all bookings", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetAllDetailedBookings_whenNoBookings_returnsEmptyCollections() throws AppException {
        when(bookingDAOMock.findAllBookingsForPage(anyInt(), anyInt(), any(BookingStatus.class))).thenReturn(Collections.emptyList());

        BookingServiceImpl BookingService = new BookingServiceImpl(bookingDAOMock, userDAOMock, invoiceDAOMock, null);
        List<BookingDetailed> result = BookingService.getAllDetailedBookings(1, 5, NEW);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetAllDetailedBookings_whenNoBookings_userDAONotCalled() throws AppException {
        when(bookingDAOMock.findAllBookingsForPage(anyInt(), anyInt(), any(BookingStatus.class))).thenReturn(Collections.emptyList());
        verify(userDAOMock, times(0)).findUsersByIds(anyList());

        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDAOMock, userDAOMock, invoiceDAOMock, null);
        List<BookingDetailed> result = bookingService.getAllDetailedBookings(1, 5, NEW);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetAllDetailedBookings_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        when(bookingDAOMock.findAllBookingsForPage(anyInt(),anyInt(), any(BookingStatus.class))).thenThrow(new DBException(messageNotToGet));
        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDAOMock, userDAOMock, invoiceDAOMock, null);

        try {
            bookingService.getAllDetailedBookings(1, 5, NEW);
        }catch(AppException ex){
            Assertions.assertEquals("Can't retrieve list of all bookings to show in the manager's account", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetUserDetailedBookings_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        int userId = 1;
        when(bookingDAOMock.findBookingsByUserIdForPage(userId, 1, 5)).thenThrow(new DBException(messageNotToGet));
        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDAOMock, null, null, null);

        try {
            bookingService.getUserDetailedBookings(userId, 1, 5);
        }catch(AppException ex){
            Assertions.assertEquals("Can't retrieve list of client's bookings to show in the client's account", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetBookingsByUserId_whenCalled_DAOCalled() throws AppException {
        int userId = 7890;
        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDAOMock, null, null, null);

        bookingService.getBookingsByUserId(userId);

        verify(bookingDAOMock, times(1)).findBookingsByUserId(userId);
    }

    @Test
    void testGetBookingsByUserId_whenDaoThrows_throwsException() throws AppException {
        int userId = 7890;
        when(bookingDAOMock.findBookingsByUserId(userId)).thenThrow(new DBException());
        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDAOMock, null, null, null);

        Assertions.assertThrowsExactly(AppException.class, () -> bookingService.getBookingsByUserId(userId));
    }

    @Test
    void testGetBookingsByUserId_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        int userId = 0;
        when(bookingDAOMock.findBookingsByUserId(userId)).thenThrow(new DBException(messageNotToGet));
        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDAOMock, null, null, null);

        try {
            bookingService.getBookingsByUserId(userId);
        }catch(AppException ex){
            Assertions.assertEquals("Can't retrieve user's bookings by user's id", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testDeleteBookingById_whenCalled_DAOCalled() throws AppException {
        int bookingId = 123;
        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDAOMock, null, null, null);

        bookingService.deleteBookingById(bookingId);

        verify(bookingDAOMock, times(1)).deleteBookingById(bookingId);
    }

    @Test
    void testDeleteBookingById_whenDaoThrows_throwsException() throws AppException {
        int bookingId = 123;
        doThrow(new DBException()).when(bookingDAOMock).deleteBookingById(bookingId);
        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDAOMock, null, null, null);

        Assertions.assertThrowsExactly(AppException.class, () -> bookingService.deleteBookingById(bookingId));
    }

    @Test
    void testDeleteBookingById_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        int bookingId = 12321311;
        doThrow(new DBException(messageNotToGet)).when(bookingDAOMock).deleteBookingById(bookingId);
        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDAOMock, null, null, null);

        try {
            bookingService.deleteBookingById(bookingId);
        }catch(AppException ex){
            Assertions.assertEquals("Can't remove booking by id", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetBookingById_whenCalled_DAOCalled() throws AppException {
        int BookingId = 112567890;
        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDAOMock, null, null, null);

        bookingService.getBookingById(BookingId);

        verify(bookingDAOMock, times(1)).findBookingById(BookingId);
    }

    @Test
    void testGetBookingById_whenDaoThrows_throwsException() throws AppException {
        int bookingId = 112567890;

        when(bookingDAOMock.findBookingById(bookingId)).thenThrow(new DBException());
        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDAOMock, null, null, null);

        Assertions.assertThrowsExactly(AppException.class, () -> bookingService.getBookingById(bookingId));
    }

    @Test
    void testGetBookingById_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        int bookingId = 112567890;

        when(bookingDAOMock.findBookingById(bookingId)).thenThrow(new DBException(messageNotToGet));
        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDAOMock, null, null, null);

        try {
            bookingService.getBookingById(bookingId);
        }catch(AppException ex){
            Assertions.assertEquals("Can't retrieve booking by id", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetBookingById_whenCalled_returnsCorrectBooking() throws AppException {
        int bookingId = 112567890;
        Booking expectedBooking = new Booking();
        expectedBooking.setRoomId(456);
        expectedBooking.setCheckinDate(LocalDate.now().plusDays(7));
        expectedBooking.setCheckoutDate(LocalDate.now().plusDays(9));
        expectedBooking.setId(bookingId);
        when(bookingDAOMock.findBookingById(bookingId)).thenReturn(expectedBooking);
        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDAOMock, null, null, null);

        Booking result = bookingService.getBookingById(bookingId);

        Assertions.assertEquals(result, expectedBooking);
    }
}
