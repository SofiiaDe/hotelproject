package services;

import com.epam.javacourse.hotel.db.dao.BookingInvoiceDAO;
import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.Invoice;
import com.epam.javacourse.hotel.model.service.impl.BookingInvoiceServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingInvoiceServiceImplTest {

    @Mock
    private BookingInvoiceDAO bookingInvoiceDAOMock;

    @Test
    void testCreateBookingAndInvoice_whenCreate_returnTrue() throws AppException {
        Booking booking = new Booking();
        Invoice invoice = new Invoice();
        BookingInvoiceServiceImpl bookInvService = new BookingInvoiceServiceImpl(bookingInvoiceDAOMock);
        when(bookingInvoiceDAOMock.createBookingAndInvoice(booking, invoice)).thenReturn(true);
        boolean result = bookInvService.createBookingAndInvoice(booking, invoice);

        Assertions.assertTrue(result);
    }

    @Test
    void testCreateBookingAndInvoice_whenCalled_DAOCalled() throws AppException {
        Booking booking = new Booking();
        Invoice invoice = new Invoice();
        BookingInvoiceServiceImpl bookInvService = new BookingInvoiceServiceImpl(bookingInvoiceDAOMock);
        bookInvService.createBookingAndInvoice(booking, invoice);

        verify(bookingInvoiceDAOMock, times(1)).createBookingAndInvoice(booking, invoice);
    }

    @Test
    void testCreateBookingAndInvoice_whenDaoThrows_throwsException() throws AppException {
        when(bookingInvoiceDAOMock.createBookingAndInvoice(any(Booking.class), any(Invoice.class))).thenThrow(new DBException());
        BookingInvoiceServiceImpl bookInvService = new BookingInvoiceServiceImpl(bookingInvoiceDAOMock);

        Assertions.assertThrowsExactly(AppException.class, () -> bookInvService.createBookingAndInvoice(new Booking(), new Invoice()));
    }

    @Test
    void testCreateBookingAndInvoice_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        when(bookingInvoiceDAOMock.createBookingAndInvoice(any(Booking.class), any(Invoice.class))).thenThrow(new DBException(messageNotToGet));
        BookingInvoiceServiceImpl bookInvService = new BookingInvoiceServiceImpl(bookingInvoiceDAOMock);

        try {
            bookInvService.createBookingAndInvoice(new Booking(), new Invoice());
        }catch(AppException ex){
            Assertions.assertEquals("Can't create new booking and invoice", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

}
