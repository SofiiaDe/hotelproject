package services;

import com.epam.javacourse.hotel.db.dao.ApplicationDAO;
import com.epam.javacourse.hotel.db.dao.UserDAO;
import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.impl.ApplicationServiceImpl;
import com.epam.javacourse.hotel.model.serviceModels.ApplicationDetailed;
import com.epam.javacourse.hotel.model.serviceModels.UserApplicationDetailed;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceImplTest {

    @Mock
    private ApplicationDAO applicationDAOMock;

    @Mock
    private UserDAO userDAOMock;

    @Test
    void testCreate_whenDaoThrows_throwsException() throws DBException {
        when(applicationDAOMock.createApplication(any(Application.class))).thenThrow(new DBException());
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);

        Assertions.assertThrowsExactly(AppException.class, () -> appService.create(new Application()));
    }

    @Test
    void testCreate_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        when(applicationDAOMock.createApplication(any(Application.class))).thenThrow(new DBException(messageNotToGet));
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);

        try {
            appService.create(new Application());
        }catch(AppException ex){
            Assertions.assertEquals("Can't create application", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testCreate_whenCalled_callsDAO() throws AppException {
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);

        appService.create(new Application());
        verify(applicationDAOMock, times(1)).createApplication(any(Application.class));
    }

    @Test
    void testGetAllApplications_whenCalled_callsDAO() throws AppException {
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);

        appService.getAllApplications();
        verify(applicationDAOMock, times(1)).findAllApplications();
    }

    @Test
    void testGetAllApplications_whenDaoThrows_throwsException() throws DBException {
        when(applicationDAOMock.findAllApplications()).thenThrow(new DBException());
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);

        Assertions.assertThrowsExactly(AppException.class, appService::getAllApplications);
    }

    @Test
    void testGetAllApplications_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        when(applicationDAOMock.findAllApplications()).thenThrow(new DBException(messageNotToGet));
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);

        try {
            appService.getAllApplications();
        }catch(AppException ex){
            Assertions.assertEquals("Can't retrieve all applications", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetAllDetailedApplications_returnsCorrectData() throws AppException {
        // application dao
        ArrayList<Application> applicationDb = getApplications();
        when(applicationDAOMock.findAllApplications()).thenReturn(applicationDb);


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

        ArrayList<User> userDb = new ArrayList<>();
        userDb.add(user1);
        userDb.add(user2);
        when(userDAOMock.findUsersByIds(anyList())).thenReturn(userDb);

        ApplicationServiceImpl applicationService = new ApplicationServiceImpl(applicationDAOMock, userDAOMock);
        List<ApplicationDetailed> result = applicationService.getAllDetailedApplications();

        for (ApplicationDetailed appDetails :
                result) {
            Application expectedApplication = applicationDb.stream().filter(apl -> apl.getId() == appDetails.getId()).findFirst().get();
            Assertions.assertEquals(appDetails.getCheckinDate(), expectedApplication.getCheckinDate());
            Assertions.assertEquals(appDetails.getCheckoutDate(), expectedApplication.getCheckoutDate());
            Assertions.assertEquals(appDetails.getRoomClass(), expectedApplication.getRoomClass());
            Assertions.assertEquals(appDetails.getRoomTypeBySeats(), expectedApplication.getRoomTypeBySeats());

            User expectedUser = userDb.stream().filter(u -> Objects.equals(u.getEmail(), appDetails.getBookedByUserEmail())).findFirst().get();
            Assertions.assertEquals(appDetails.getBookedByUser(), expectedUser.getFirstName() + ' ' + expectedUser.getLastName());
        }
    }

    @Test
    void testGetAllDetailedApplications_whenNoApplications_returnsEmptyCollections() throws AppException {
        when(applicationDAOMock.findAllApplications()).thenReturn(Collections.emptyList());

        ApplicationServiceImpl applicationService = new ApplicationServiceImpl(applicationDAOMock, null);
        List<ApplicationDetailed> result = applicationService.getAllDetailedApplications();

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetAllDetailedApplications_whenNoApplications_userDAONotCalled() throws AppException {
        when(applicationDAOMock.findAllApplications()).thenReturn(Collections.emptyList());
        verify(userDAOMock, times(0)).findUsersByIds(anyList());

        ApplicationServiceImpl applicationService = new ApplicationServiceImpl(applicationDAOMock, userDAOMock);
        List<ApplicationDetailed> result = applicationService.getAllDetailedApplications();

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetAllDetailedApplications_whenDaoThrows_throwsException() throws DBException {
        when(applicationDAOMock.findAllApplications()).thenThrow(new DBException());
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);

        Assertions.assertThrowsExactly(AppException.class, appService::getAllDetailedApplications);
    }

    @Test
    void testGetAllDetailedApplications_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        when(applicationDAOMock.findAllApplications()).thenThrow(new DBException(messageNotToGet));
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);

        try {
            appService.getAllDetailedApplications();
        }catch(AppException ex){
            Assertions.assertEquals("Can't retrieve all applications to show in the manager's account", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetUserDetailedApplications_returnsCorrectData() throws AppException {
        int expectedUserId = 1;
        ArrayList<Application> applicationDb = getApplications();
        when(applicationDAOMock.findApplicationsByUserId(expectedUserId)).thenReturn(applicationDb);

        ApplicationServiceImpl applicationService = new ApplicationServiceImpl(applicationDAOMock, null);
        List<UserApplicationDetailed> result = applicationService.getUserDetailedApplications(expectedUserId);

        for (UserApplicationDetailed appDetails :
                result) {
            Application expectedApplication = applicationDb.stream().filter(apl -> apl.getId() == appDetails.getId()).findFirst().get();
            Assertions.assertEquals(appDetails.getCheckinDate(), expectedApplication.getCheckinDate());
            Assertions.assertEquals(appDetails.getCheckoutDate(), expectedApplication.getCheckoutDate());
            Assertions.assertEquals(appDetails.getRoomClass(), expectedApplication.getRoomClass());
            Assertions.assertEquals(appDetails.getRoomTypeBySeats(), expectedApplication.getRoomTypeBySeats());
        }
    }

    @Test
    void testGetUserDetailedApplications_whenNoApplications_returnsEmptyCollections() throws AppException {
        int userId = 1;
        when(applicationDAOMock.findApplicationsByUserId(userId)).thenReturn(Collections.emptyList());

        ApplicationServiceImpl applicationService = new ApplicationServiceImpl(applicationDAOMock, userDAOMock);
        List<UserApplicationDetailed> result = applicationService.getUserDetailedApplications(userId);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetUserDetailedApplications_whenDaoThrows_throwsException() throws AppException {
        int userId = 1;
        when(applicationDAOMock.findApplicationsByUserId(userId)).thenThrow(new DBException());
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);

        Assertions.assertThrowsExactly(AppException.class, () -> appService.getUserDetailedApplications(userId));
    }

    @Test
    void testGetUserDetailedApplications_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        int userId = 1;
        when(applicationDAOMock.findApplicationsByUserId(userId)).thenThrow(new DBException(messageNotToGet));
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);

        try {
            appService.getUserDetailedApplications(userId);
        }catch(AppException ex){
            Assertions.assertEquals("Can't retrieve client's applications to show in the client's account", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetApplicationsByUserId_whenCalled_DAOCalled() throws AppException {
        int userId = 7890;
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);

        appService.getApplicationsByUserId(userId);

        verify(applicationDAOMock, times(1)).findApplicationsByUserId(userId);
    }

    @Test
    void testGetApplicationsByUserId_whenDaoThrows_throwsException() throws AppException {
        int userId = 7890;
        when(applicationDAOMock.findApplicationsByUserId(userId)).thenThrow(new DBException());
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);

        Assertions.assertThrowsExactly(AppException.class, () -> appService.getApplicationsByUserId(userId));
    }

    @Test
    void testGetApplicationsByUserId_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        int userId = 0;
        when(applicationDAOMock.findApplicationsByUserId(userId)).thenThrow(new DBException(messageNotToGet));
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);

        try {
            appService.getApplicationsByUserId(userId);
        }catch(AppException ex){
            Assertions.assertEquals("Can't retrieve client's applications", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testUpdateApplication_whenUpdate_returnTrue() throws AppException {
        Application application = new Application();
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);
        when(applicationDAOMock.updateApplication(application)).thenReturn(true);
        boolean result = appService.updateApplication(application);

        Assertions.assertTrue(result);
    }

    @Test
    void testUpdateApplication_whenCalled_DAOCalled() throws AppException {
        Application application = new Application();
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);

        appService.updateApplication(application);

        verify(applicationDAOMock, times(1)).updateApplication(application);
    }

    @Test
    void testUpdateApplication_whenDaoThrows_throwsException() throws AppException {
        when(applicationDAOMock.updateApplication(any(Application.class))).thenThrow(new DBException());
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);

        Assertions.assertThrowsExactly(AppException.class, () -> appService.updateApplication(new Application()));
    }

    @Test
    void testUpdateApplication_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        when(applicationDAOMock.updateApplication(any(Application.class))).thenThrow(new DBException(messageNotToGet));
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);

        try {
            appService.updateApplication(new Application());
        }catch(AppException ex){
            Assertions.assertEquals("Can't update application", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testRemoveApplication_whenCalled_DAOCalled() throws AppException {
        int applicationId = 123;
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);

        appService.removeApplication(applicationId );

        verify(applicationDAOMock, times(1)).deleteApplication(applicationId);
    }

    @Test
    void testRemoveApplication_whenDaoThrows_throwsException() throws AppException {
        int applicationId = 123;
        doThrow(new DBException()).when(applicationDAOMock).deleteApplication(applicationId);
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);

        Assertions.assertThrowsExactly(AppException.class, () -> appService.removeApplication(applicationId));
    }

    @Test
    void testRemoveApplication_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        int applicationId = 12321311;
        doThrow(new DBException(messageNotToGet)).when(applicationDAOMock).deleteApplication(applicationId);
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);

        try {
            appService.removeApplication(applicationId);
        }catch(AppException ex){
            Assertions.assertEquals("Can't remove application", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetApplicationById_whenCalled_DAOCalled() throws AppException {
        int applicationId = 112567890;
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);

        appService.getApplicationById(applicationId);

        verify(applicationDAOMock, times(1)).findApplicationById(applicationId);
    }

    @Test
    void testGetApplicationById_whenDaoThrows_throwsException() throws AppException {
        int applicationId = 112567890;

        when(applicationDAOMock.findApplicationById(applicationId)).thenThrow(new DBException());
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);

        Assertions.assertThrowsExactly(AppException.class, () -> appService.getApplicationById(applicationId));
    }

    @Test
    void testGetApplicationById_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        int applicationId = 112567890;

        when(applicationDAOMock.findApplicationById(applicationId)).thenThrow(new DBException(messageNotToGet));
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);

        try {
            appService.getApplicationById(applicationId);
        }catch(AppException ex){
            Assertions.assertEquals("Can't retrieve application by its id", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetApplicationById_whenCalled_returnsCorrectApplication() throws AppException {
        int applicationId = 112567890;
        Application expectedApplication = new Application();
        expectedApplication.setRoomTypeBySeats("have a nice day");
        expectedApplication.setRoomClass("classy room");
        expectedApplication.setId(applicationId);
        when(applicationDAOMock.findApplicationById(applicationId)).thenReturn(expectedApplication);
        ApplicationServiceImpl appService = new ApplicationServiceImpl(applicationDAOMock, null);

        Application result = appService.getApplicationById(applicationId);

        Assertions.assertEquals(result, expectedApplication);
    }

    private ArrayList<Application> getApplications() {
        // application dao
        ArrayList<Application> applicationDb = new ArrayList<>();
        Application app = new Application();
        app.setId(111);
        app.setCheckinDate(LocalDate.MIN);
        app.setCheckoutDate(LocalDate.MAX);
        app.setRoomTypeBySeats("double");
        app.setUserId(1);
        app.setRoomClass("aaaa");
        Application app2 = new Application();
        app2.setId(222);
        app2.setCheckinDate(LocalDate.now().plusDays(1));
        app2.setCheckinDate(LocalDate.now().plusDays(3));
        app2.setRoomTypeBySeats("twin");
        app2.setUserId(2);
        app2.setRoomClass("does not matter");

        applicationDb.add(app);
        applicationDb.add(app2);

        return applicationDb;
    }
}
