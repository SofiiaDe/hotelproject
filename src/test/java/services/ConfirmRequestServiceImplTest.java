package services;

import com.epam.javacourse.hotel.db.dao.ConfirmRequestDAO;
import com.epam.javacourse.hotel.db.dao.UserDAO;
import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.ConfirmationRequest;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.impl.ConfirmRequestServiceImpl;
import com.epam.javacourse.hotel.model.serviceModels.ConfirmationRequestDetailed;
import com.epam.javacourse.hotel.model.serviceModels.UserConfirmationRequestDetailed;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfirmRequestServiceImplTest {

    @Mock
    private ConfirmRequestDAO confirmRequestDAOMock;

    @Mock
    private UserDAO userDAOMock;

    @Test
    void testCreate_whenDaoThrows_throwsException() throws DBException {
        when(confirmRequestDAOMock.createConfirmRequest(any(ConfirmationRequest.class))).thenThrow(new DBException());
        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);

        Assertions.assertThrowsExactly(AppException.class, () -> confirmRequestService.create(
                new ConfirmationRequest()));
    }

    @Test
    void testCreate_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        when(confirmRequestDAOMock.createConfirmRequest(any(ConfirmationRequest.class))).thenThrow(new DBException(messageNotToGet));
        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);

        try {
            confirmRequestService.create(new ConfirmationRequest());
        } catch (AppException ex) {
            Assertions.assertEquals("Can't create confirmation request", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testCreate_whenCalled_callsDAO() throws AppException {
        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);

        confirmRequestService.create(new ConfirmationRequest());
        verify(confirmRequestDAOMock, times(1)).createConfirmRequest(any(ConfirmationRequest.class));
    }

    @Test
    void testGetAllConfirmRequests_whenCalled_callsDAO() throws AppException {
        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);

        confirmRequestService.getAllConfirmRequests();
        verify(confirmRequestDAOMock, times(1)).findAllConfirmRequests();
    }

    @Test
    void testGetAllConfirmRequests_whenDaoThrows_throwsException() throws DBException {
        when(confirmRequestDAOMock.findAllConfirmRequests()).thenThrow(new DBException());
        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);

        Assertions.assertThrowsExactly(AppException.class, confirmRequestService::getAllConfirmRequests);
    }

    @Test
    void testGetAllConfirmRequests_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        when(confirmRequestDAOMock.findAllConfirmRequests()).thenThrow(new DBException(messageNotToGet));
        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);

        try {
            confirmRequestService.getAllConfirmRequests();
        } catch (AppException ex) {
            Assertions.assertEquals("Can't retrieve all confirmation requests", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetAllDetailedConfirmRequests_returnsCorrectData() throws AppException {
        // confirmation request dao
        List<ConfirmationRequest> confirmationRequestDb = getConfirmRequests();
        when(confirmRequestDAOMock.findAllConfirmRequests()).thenReturn(confirmationRequestDb);


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

        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, userDAOMock);
        List<ConfirmationRequestDetailed> result = confirmRequestService.getAllDetailedConfirmRequests();

        for (ConfirmationRequestDetailed confirmRequestDetails : result) {
            ConfirmationRequest expectedConfirmRequest = confirmationRequestDb.stream()
                    .filter(cr -> cr.getId() == confirmRequestDetails.getId()).findFirst().get();
            Assertions.assertEquals(confirmRequestDetails.getConfirmRequestDate(), expectedConfirmRequest.getConfirmRequestDate());
            Assertions.assertEquals(confirmRequestDetails.getApplicationId(), expectedConfirmRequest.getApplicationId());
            Assertions.assertEquals(confirmRequestDetails.getRoomId(), expectedConfirmRequest.getRoomId());

            User expectedUser = userDb.stream().filter(u -> Objects.equals(u.getEmail(), confirmRequestDetails.getBookedByUserEmail())).findFirst().get();
            Assertions.assertEquals(confirmRequestDetails.getBookedByUser(), expectedUser.getFirstName() + ' ' + expectedUser.getLastName());
        }
    }

    @Test
    void testGetAllDetailedConfirmRequests_whenNoConfirmRequests_returnsEmptyCollections() throws AppException {
        when(confirmRequestDAOMock.findAllConfirmRequests()).thenReturn(Collections.emptyList());

        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);
        List<ConfirmationRequestDetailed> result = confirmRequestService.getAllDetailedConfirmRequests();

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetAllDetailedConfirmRequests_whenNoConfirmRequests_userDAONotCalled() throws AppException {
        when(confirmRequestDAOMock.findAllConfirmRequests()).thenReturn(Collections.emptyList());
        verify(userDAOMock, times(0)).findUsersByIds(anyList());

        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, userDAOMock);
        List<ConfirmationRequestDetailed> result = confirmRequestService.getAllDetailedConfirmRequests();

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetAllDetailedConfirmRequests_whenDaoThrows_throwsException() throws DBException {
        when(confirmRequestDAOMock.findAllConfirmRequests()).thenThrow(new DBException());
        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);

        Assertions.assertThrowsExactly(AppException.class, confirmRequestService::getAllDetailedConfirmRequests);
    }

    @Test
    void testGetAllDetailedConfirmRequests_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        when(confirmRequestDAOMock.findAllConfirmRequests()).thenThrow(new DBException(messageNotToGet));
        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);

        try {
            confirmRequestService.getAllDetailedConfirmRequests();
        } catch (AppException ex) {
            Assertions.assertEquals("Can't retrieve all confirmation requests to show in the manager's account", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetUserDetailedConfirmRequests_whenNoConfirmRequests_returnsEmptyCollections() throws AppException {
        int userId = 1;
        when(confirmRequestDAOMock.findConfirmRequestsByUserId(userId)).thenReturn(Collections.emptyList());

        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, userDAOMock);
        List<UserConfirmationRequestDetailed> result = confirmRequestService.getUserDetailedConfirmRequests(userId);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetUserDetailedConfirmRequests_whenDaoThrows_throwsException() throws AppException {
        int userId = 1;
        when(confirmRequestDAOMock.findConfirmRequestsByUserId(userId)).thenThrow(new DBException());
        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);

        Assertions.assertThrowsExactly(AppException.class, () -> confirmRequestService.getUserDetailedConfirmRequests(userId));
    }

    @Test
    void testGetUserDetailedConfirmRequests_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        int userId = 1;
        when(confirmRequestDAOMock.findConfirmRequestsByUserId(userId)).thenThrow(new DBException(messageNotToGet));
        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);

        try {
            confirmRequestService.getUserDetailedConfirmRequests(userId);
        } catch (AppException ex) {
            Assertions.assertEquals("Can't retrieve client's confirmation requests to show in the client's account", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetConfirmRequestsByUserId_whenCalled_DAOCalled() throws AppException {
        int userId = 7890;
        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);

        confirmRequestService.getConfirmRequestsByUserId(userId);

        verify(confirmRequestDAOMock, times(1)).findConfirmRequestsByUserId(userId);
    }

    @Test
    void testGetConfirmRequestsByUserId_whenDaoThrows_throwsException() throws AppException {
        int userId = 7890;
        when(confirmRequestDAOMock.findConfirmRequestsByUserId(userId)).thenThrow(new DBException());
        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);

        Assertions.assertThrowsExactly(AppException.class, () -> confirmRequestService.getConfirmRequestsByUserId(userId));
    }

    @Test
    void testGetConfirmRequestsByUserId_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        int userId = 0;
        when(confirmRequestDAOMock.findConfirmRequestsByUserId(userId)).thenThrow(new DBException(messageNotToGet));
        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);

        try {
            confirmRequestService.getConfirmRequestsByUserId(userId);
        } catch (AppException ex) {
            Assertions.assertEquals("Can't retrieve client's confirmation requests", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testDeleteConfirmRequestById_whenCalled_DAOCalled() throws AppException {
        int confirmRequestId = 123;
        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);

        confirmRequestService.deleteConfirmRequestById(confirmRequestId);

        verify(confirmRequestDAOMock, times(1)).deleteConfirmRequestById(confirmRequestId);
    }

    @Test
    void testDeleteConfirmRequestById_whenDaoThrows_throwsException() throws AppException {
        int confirmRequestId = 123;
        doThrow(new DBException()).when(confirmRequestDAOMock).deleteConfirmRequestById(confirmRequestId);
        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);

        Assertions.assertThrowsExactly(AppException.class, () -> confirmRequestService.deleteConfirmRequestById(confirmRequestId));
    }

    @Test
    void testDeleteConfirmRequestById_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        int confirmRequestId = 12321311;
        doThrow(new DBException(messageNotToGet)).when(confirmRequestDAOMock).deleteConfirmRequestById(confirmRequestId);
        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);

        try {
            confirmRequestService.deleteConfirmRequestById(confirmRequestId);
        } catch (AppException ex) {
            Assertions.assertEquals("Can't remove confirmation request by id", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetConfirmRequestById_whenCalled_DAOCalled() throws AppException {
        int confirmRequestId = 112567890;
        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);

        confirmRequestService.getConfirmRequestById(confirmRequestId);

        verify(confirmRequestDAOMock, times(1)).findConfirmRequestById(confirmRequestId);
    }

    @Test
    void testGetConfirmRequestById_whenDaoThrows_throwsException() throws AppException {
        int confirmRequestId = 112567890;

        when(confirmRequestDAOMock.findConfirmRequestById(confirmRequestId)).thenThrow(new DBException());
        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);

        Assertions.assertThrowsExactly(AppException.class, () -> confirmRequestService.getConfirmRequestById(confirmRequestId));
    }

    @Test
    void testGetConfirmRequestById_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        int confirmRequestId = 112567890;

        when(confirmRequestDAOMock.findConfirmRequestById(confirmRequestId)).thenThrow(new DBException(messageNotToGet));
        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);

        try {
            confirmRequestService.getConfirmRequestById(confirmRequestId);
        } catch (AppException ex) {
            Assertions.assertEquals("Can't retrieve confirmation request by id", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetConfirmRequestById_whenCalled_returnsCorrectConfirmRequest() throws AppException {
        int confirmRequestId = 112567890;
        ConfirmationRequest expectedConfirmRequest = new ConfirmationRequest();
        expectedConfirmRequest.setApplicationId(2409);
        expectedConfirmRequest.setStatus("new");
        expectedConfirmRequest.setId(confirmRequestId);
        when(confirmRequestDAOMock.findConfirmRequestById(confirmRequestId)).thenReturn(expectedConfirmRequest);
        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);

        ConfirmationRequest result = confirmRequestService.getConfirmRequestById(confirmRequestId);

        Assertions.assertEquals(result, expectedConfirmRequest);
    }

    @Test
    void testGetConfirmRequestDueDate_returnsCorrectData() {
        ConfirmationRequest confirmRequest = new ConfirmationRequest();
       confirmRequest.setConfirmRequestDate(LocalDate.of(2022, 3, 10));
        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);
        LocalDate result = confirmRequestService.getConfirmRequestDueDate(confirmRequest);
        Assertions.assertEquals(LocalDate.of(2022, 3, 12), result);
    }

    @Test
    void testConfirmRequestByClient_whenDaoThrows_throwsException() throws DBException {

        List<ConfirmationRequest> confirmationRequestDb = getConfirmRequests();
        ConfirmationRequest confirmRequest = confirmationRequestDb.stream().findFirst().get();
        when(confirmRequestDAOMock.findConfirmRequestById(confirmRequest.getId())).thenThrow(new DBException());

        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);
        Assertions.assertThrowsExactly(AppException.class, () -> confirmRequestService.confirmRequestByClient(
                confirmRequest.getId()));
    }

    @Test
    void testConfirmRequestByClient_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        when(confirmRequestDAOMock.findConfirmRequestById(any(Integer.class))).thenThrow(new DBException(messageNotToGet));
        List<ConfirmationRequest> confirmationRequestDb = getConfirmRequests();

        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);
        ConfirmationRequest confirmRequest = confirmationRequestDb.stream().findFirst().get();

        try {
            confirmRequestService.confirmRequestByClient(confirmRequest.getId());
        } catch (AppException ex) {
            Assertions.assertEquals("Can't update confirmation request's status to 'confirmed'", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testConfirmRequestByClient_whenCalled_callsDAO() throws AppException {

        List<ConfirmationRequest> confirmationRequestDb = getConfirmRequests();
        ConfirmationRequest confirmRequest = confirmationRequestDb.stream().findFirst().get();
        when(confirmRequestDAOMock.findConfirmRequestById(confirmRequest.getId())).thenReturn(confirmRequest);

        ConfirmRequestServiceImpl confirmRequestService = new ConfirmRequestServiceImpl(confirmRequestDAOMock, null);

        confirmRequestService.confirmRequestByClient(confirmRequest.getId());
        verify(confirmRequestDAOMock, times(1)).updateConfirmRequestStatus(confirmRequest);
    }

    private List<ConfirmationRequest> getConfirmRequests() {
        // ConfirmationRequest dao
        List<ConfirmationRequest> confirmationRequestDb = new ArrayList<>();
        ConfirmationRequest confirmRequest = new ConfirmationRequest();
        confirmRequest.setId(111);
        confirmRequest.setConfirmRequestDate(LocalDate.MIN);
        confirmRequest.setConfirmRequestDueDate(LocalDate.MAX);
        confirmRequest.setRoomId(155);
        confirmRequest.setApplicationId(444);
        confirmRequest.setUserId(1);
        confirmRequest.setStatus("someStatus");

        ConfirmationRequest confirmRequest2 = new ConfirmationRequest();
        confirmRequest2.setId(222);
        confirmRequest2.setConfirmRequestDate(LocalDate.now().plusDays(1));
        confirmRequest2.setConfirmRequestDueDate(LocalDate.now().plusDays(3));
        confirmRequest.setRoomId(166);
        confirmRequest.setApplicationId(555);
        confirmRequest2.setUserId(2);
        confirmRequest2.setStatus("does not matter");

        confirmationRequestDb.add(confirmRequest);
        confirmationRequestDb.add(confirmRequest2);

        return confirmationRequestDb;
    }

}
