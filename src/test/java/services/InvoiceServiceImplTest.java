package services;

import com.epam.javacourse.hotel.db.dao.InvoiceDAO;
import com.epam.javacourse.hotel.db.dao.UserDAO;
import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.Invoice;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.impl.InvoiceServiceImpl;
import com.epam.javacourse.hotel.model.serviceModels.InvoiceDetailed;
import com.epam.javacourse.hotel.model.serviceModels.UserInvoiceDetailed;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceImplTest {

    @Mock
    private InvoiceDAO invoiceDAOMock;

    @Mock
    private UserDAO userDAOMock;

    @Test
    void testCreateInvoice_whenDaoThrows_throwsException() throws DBException {
        when(invoiceDAOMock.createInvoice(any(Invoice.class))).thenThrow(new DBException());
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);

        Assertions.assertThrowsExactly(AppException.class, () -> invoiceService.createInvoice(new Invoice()));
    }

    @Test
    void testCreateInvoice_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        when(invoiceDAOMock.createInvoice(any(Invoice.class))).thenThrow(new DBException(messageNotToGet));
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);

        try {
            invoiceService.createInvoice(new Invoice());
        }catch(AppException ex){
            Assertions.assertEquals("Can't create invoice", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testCreateInvoice_whenCalled_callsDAO() throws AppException {
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);

        invoiceService.createInvoice(new Invoice());
        verify(invoiceDAOMock, times(1)).createInvoice(any(Invoice.class));
    }

    @Test
    void testGetAllInvoices_whenCalled_callsDAO() throws AppException {
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);

        invoiceService.getAllInvoices();
        verify(invoiceDAOMock, times(1)).findAllInvoices();
    }

    @Test
    void testGetAllInvoices_whenDaoThrows_throwsException() throws DBException {
        when(invoiceDAOMock.findAllInvoices()).thenThrow(new DBException());
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);

        Assertions.assertThrowsExactly(AppException.class, invoiceService::getAllInvoices);
    }

    @Test
    void testGetAllInvoices_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        when(invoiceDAOMock.findAllInvoices()).thenThrow(new DBException(messageNotToGet));
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);

        try {
            invoiceService.getAllInvoices();
        }catch(AppException ex){
            Assertions.assertEquals("Can't retrieve all invoices", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetAllDetailedInvoices_returnsCorrectData() throws AppException {
        // Invoice dao
        List<Invoice> invoiceDb = getInvoices();
        when(invoiceDAOMock.findAllInvoices()).thenReturn(invoiceDb);
        
        // user dao
        User user1 = new User();
        user1.setId(1);
        user1.setFirstName("UserFirstName");
        user1.setLastName("UserLastName");
        user1.setEmail("aaa@bbb.ccc");
        User user2 = new User();
        user2.setId(2);
        user2.setFirstName("AAAAA");
        user2.setLastName("BBBB");
        user2.setEmail("writing.tests@is.timeconsuming");

        List<User> userDb = new ArrayList<>();
        userDb.add(user1);
        userDb.add(user2);
        when(userDAOMock.findUsersByIds(anyList())).thenReturn(userDb);

        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, userDAOMock);
        List<InvoiceDetailed> result = invoiceService.getAllDetailedInvoices();

        for (InvoiceDetailed invoiceDetails :
                result) {
            Invoice expectedInvoice = invoiceDb.stream().filter(apl -> apl.getId() == invoiceDetails.getId()).findFirst().get();
            Assertions.assertEquals(invoiceDetails.getAmount(), expectedInvoice.getAmount());
            Assertions.assertEquals(invoiceDetails.getBookingId(), expectedInvoice.getBookingId());
            Assertions.assertEquals(invoiceDetails.getInvoiceDate(), expectedInvoice.getInvoiceDate());
            Assertions.assertEquals(invoiceDetails.getStatus(), expectedInvoice.getInvoiceStatus());

            User expectedUser = userDb.stream().filter(u -> Objects.equals(u.getEmail(), invoiceDetails.getBookedByUserEmail())).findFirst().get();
            Assertions.assertEquals(invoiceDetails.getBookedByUser(), expectedUser.getFirstName() + ' ' + expectedUser.getLastName());
        }
    }

    @Test
    void testGetAllDetailedInvoices_whenNoInvoices_returnsEmptyCollections() throws AppException {
        when(invoiceDAOMock.findAllInvoices()).thenReturn(Collections.emptyList());

        InvoiceServiceImpl InvoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);
        List<InvoiceDetailed> result = InvoiceService.getAllDetailedInvoices();

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetAllDetailedInvoices_whenNoInvoices_userDAONotCalled() throws AppException {
        when(invoiceDAOMock.findAllInvoices()).thenReturn(Collections.emptyList());
        verify(userDAOMock, times(0)).findUsersByIds(anyList());

        InvoiceServiceImpl InvoiceService = new InvoiceServiceImpl(invoiceDAOMock, userDAOMock);
        List<InvoiceDetailed> result = InvoiceService.getAllDetailedInvoices();

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetAllDetailedInvoices_whenDaoThrows_throwsException() throws DBException {
        when(invoiceDAOMock.findAllInvoices()).thenThrow(new DBException());
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);

        Assertions.assertThrowsExactly(AppException.class, invoiceService::getAllDetailedInvoices);
    }

    @Test
    void testGetAllDetailedInvoices_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        when(invoiceDAOMock.findAllInvoices()).thenThrow(new DBException(messageNotToGet));
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);

        try {
            invoiceService.getAllDetailedInvoices();
        }catch(AppException ex){
            Assertions.assertEquals("Can't retrieve all invoices to show in the manager's account", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetUserDetailedInvoices_whenNoInvoices_returnsEmptyCollections() throws AppException {
        int userId = 1;
        when(invoiceDAOMock.findInvoicesByUserId(userId)).thenReturn(Collections.emptyList());

        InvoiceServiceImpl InvoiceService = new InvoiceServiceImpl(invoiceDAOMock, userDAOMock);
        List<UserInvoiceDetailed> result = InvoiceService.getUserDetailedInvoices(userId);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetUserDetailedInvoices_whenDaoThrows_throwsException() throws AppException {
        int userId = 1;
        when(invoiceDAOMock.findInvoicesByUserId(userId)).thenThrow(new DBException());
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);

        Assertions.assertThrowsExactly(AppException.class, () -> invoiceService.getUserDetailedInvoices(userId));
    }

    @Test
    void testGetUserDetailedInvoices_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        int userId = 1;
        when(invoiceDAOMock.findInvoicesByUserId(userId)).thenThrow(new DBException(messageNotToGet));
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);

        try {
            invoiceService.getUserDetailedInvoices(userId);
        }catch(AppException ex){
            Assertions.assertEquals("Can't retrieve client's invoices to show in the client's account", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetInvoicesByUserId_whenCalled_DAOCalled() throws AppException {
        int userId = 7890;
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);

        invoiceService.getInvoicesByUserId(userId);

        verify(invoiceDAOMock, times(1)).findInvoicesByUserId(userId);
    }

    @Test
    void testGetInvoicesByUserId_whenDaoThrows_throwsException() throws AppException {
        int userId = 7890;
        when(invoiceDAOMock.findInvoicesByUserId(userId)).thenThrow(new DBException());
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);

        Assertions.assertThrowsExactly(AppException.class, () -> invoiceService.getInvoicesByUserId(userId));
    }

    @Test
    void testGetInvoicesByUserId_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        int userId = 0;
        when(invoiceDAOMock.findInvoicesByUserId(userId)).thenThrow(new DBException(messageNotToGet));
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);

        try {
            invoiceService.getInvoicesByUserId(userId);
        }catch(AppException ex){
            Assertions.assertEquals("Can't retrieve invoices by client's id", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testUpdateInvoiceStatusToCancelled_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        when(invoiceDAOMock.createInvoice(any(Invoice.class))).thenThrow(new DBException(messageNotToGet));
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);

        try {
            invoiceService.createInvoice(new Invoice());
        }catch(AppException ex){
            Assertions.assertEquals("Can't create invoice", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testUpdateInvoiceStatusToCancelled_whenCalled_callsDAO() throws AppException {
        List<Invoice> invoicesDb = getInvoices();
        Invoice invoice = invoicesDb.stream().findFirst().get();
        when(invoiceDAOMock.findAllInvoices()).thenReturn(invoicesDb);

        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);
        invoiceService.updateInvoiceStatusToCancelled();
        verify(invoiceDAOMock, times(1)).updateInvoiceStatus(invoice);
    }

    @Test
    void testUpdateInvoiceStatusToCancelled_whenDaoThrows_throwsException() throws AppException {
        when(invoiceDAOMock.findAllInvoices()).thenThrow(new DBException());
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);
        Assertions.assertThrowsExactly(AppException.class, () -> invoiceService.updateInvoiceStatusToCancelled());
    }


    @Test
    void testPayInvoice_whenCalled_DAOCalled() throws AppException {
        List<Invoice> invoicesDb = getInvoices();
        Invoice invoiceToPay = invoicesDb.stream().findFirst().get();
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);
        when(invoiceDAOMock.findInvoiceById(invoiceToPay.getId())).thenReturn(invoiceToPay);

        invoiceService.payInvoice(invoiceToPay.getId());

        verify(invoiceDAOMock, times(1)).updateInvoiceStatus(invoiceToPay);
    }

    @Test
    void testPayInvoice_whenDaoThrows_throwsException() throws AppException {
        List<Invoice> invoicesDb = getInvoices();
        Invoice invoiceToPay = invoicesDb.stream().findFirst().get();
        when(invoiceDAOMock.findInvoiceById(invoiceToPay.getId())).thenReturn(invoiceToPay);
        doThrow(new DBException()).when(invoiceDAOMock).updateInvoiceStatus(invoiceToPay);
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);

        Assertions.assertThrowsExactly(AppException.class, () -> invoiceService.payInvoice(invoiceToPay.getId()));
    }

    @Test
    void testPayInvoice_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        List<Invoice> invoicesDb = getInvoices();
        Invoice invoiceToPay = invoicesDb.stream().findFirst().get();
        doThrow(new DBException(messageNotToGet)).when(invoiceDAOMock).updateInvoiceStatus(invoiceToPay);
        when(invoiceDAOMock.findInvoiceById(invoiceToPay.getId())).thenReturn(invoiceToPay);

        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);

        try {
            invoiceService.payInvoice(invoiceToPay.getId());
        }catch(AppException ex){
            Assertions.assertEquals("Can't pay invoice", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetInvoiceById_whenCalled_DAOCalled() throws AppException {
        int InvoiceId = 112567890;
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);

        invoiceService.getInvoiceById(InvoiceId);

        verify(invoiceDAOMock, times(1)).findInvoiceById(InvoiceId);
    }

    @Test
    void testGetInvoiceById_whenDaoThrows_throwsException() throws AppException {
        int InvoiceId = 112567890;

        when(invoiceDAOMock.findInvoiceById(InvoiceId)).thenThrow(new DBException());
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);

        Assertions.assertThrowsExactly(AppException.class, () -> invoiceService.getInvoiceById(InvoiceId));
    }

    @Test
    void testGetInvoiceById_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        int InvoiceId = 112567890;

        when(invoiceDAOMock.findInvoiceById(InvoiceId)).thenThrow(new DBException(messageNotToGet));
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);

        try {
            invoiceService.getInvoiceById(InvoiceId);
        }catch(AppException ex){
            Assertions.assertEquals("Can't retrieve invoice by id", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetInvoiceById_whenCalled_returnsCorrectInvoice() throws AppException {
        int InvoiceId = 112567890;
        Invoice expectedInvoice = new Invoice();
        expectedInvoice.setInvoiceDate(LocalDate.now().plusDays(5));
        expectedInvoice.setAmount(new BigDecimal("500.00"));
        expectedInvoice.setId(InvoiceId);
        when(invoiceDAOMock.findInvoiceById(InvoiceId)).thenReturn(expectedInvoice);
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceDAOMock, null);

        Invoice result = invoiceService.getInvoiceById(InvoiceId);

        Assertions.assertEquals(result, expectedInvoice);
    }

    private List<Invoice> getInvoices() {
        // Invoice dao
        List<Invoice> invoiceDb = new ArrayList<>();
        Invoice invoice = new Invoice();
        invoice.setId(111);
        invoice.setInvoiceDate(LocalDate.MIN);
        invoice.setAmount(new BigDecimal("300.00"));
        invoice.setBookingId(888);
        invoice.setUserId(1);
        invoice.setInvoiceStatus("new");
        Invoice invoice2 = new Invoice();
        invoice2.setId(222);
        invoice2.setInvoiceDate(LocalDate.now().plusDays(1));
        invoice2.setAmount(new BigDecimal("200.00"));
        invoice2.setBookingId(999);
        invoice2.setUserId(2);
        invoice2.setInvoiceStatus("someStatus");

        invoiceDb.add(invoice);
        invoiceDb.add(invoice2);

        return invoiceDb;
    }
}
