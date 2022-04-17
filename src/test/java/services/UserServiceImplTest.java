package services;

import com.epam.javacourse.hotel.db.dao.UserDAO;
import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDAO userDAOMock;

    @Test
    void testGetAllUsers_whenCalled_callsDAO() throws AppException {
        UserServiceImpl userService = new UserServiceImpl(userDAOMock);

        userService.getAllUsers();
        verify(userDAOMock, times(1)).findAllUsers();
    }

    @Test
    void testGetAllUsers_whenDaoThrows_throwsException() throws DBException {
        when(userDAOMock.findAllUsers()).thenThrow(new DBException());
        UserServiceImpl userService = new UserServiceImpl(userDAOMock);

        Assertions.assertThrowsExactly(AppException.class, userService::getAllUsers);
    }

    @Test
    void testGetAllUsers_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        when(userDAOMock.findAllUsers()).thenThrow(new DBException(messageNotToGet));
        UserServiceImpl userService = new UserServiceImpl(userDAOMock);

        try {
            userService.getAllUsers();
        }catch(AppException ex){
            Assertions.assertEquals("Can't retrieve all users", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }
        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testCreate_whenDaoThrows_throwsException() throws DBException {
        doThrow(new DBException()).when(userDAOMock).createUser(any());
        UserServiceImpl userService = new UserServiceImpl(userDAOMock);

        Assertions.assertThrowsExactly(AppException.class, () -> userService.createUser(new User()));
    }

    @Test
    void testCreate_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        doThrow(new DBException(messageNotToGet)).when(userDAOMock).createUser(any(User.class));

        UserServiceImpl userService = new UserServiceImpl(userDAOMock);

        try {
            userService.createUser(new User());
        }catch(AppException ex){
            Assertions.assertEquals("Can't create a user", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testCreate_whenCalled_callsDAO() throws AppException {
        UserServiceImpl userService = new UserServiceImpl(userDAOMock);

        userService.createUser(new User());
        verify(userDAOMock, times(1)).createUser(any(User.class));
    }

    @Test
    void testGetUserByEmail_whenCalled_DAOCalled() throws AppException {
        String email = "aaa@bbb.ccc";
        UserServiceImpl userService = new UserServiceImpl(userDAOMock);
        userService.getUserByEmail(email);

        verify(userDAOMock, times(1)).findUserByEmail(email);
    }

    @Test
    void testGetUserByEmail_whenDaoThrows_throwsException() throws AppException {
        String email = "aaa@bbb.ccc";
        when(userDAOMock.findUserByEmail(email)).thenThrow(new DBException());
        UserServiceImpl userService = new UserServiceImpl(userDAOMock);

        Assertions.assertThrowsExactly(AppException.class, () -> userService.getUserByEmail(email));
    }

    @Test
    void testGetUserByEmail_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        String email = "aaabbb.ccc";
        when(userDAOMock.findUserByEmail(email)).thenThrow(new DBException(messageNotToGet));
        UserServiceImpl userService = new UserServiceImpl(userDAOMock);

        try {
            userService.getUserByEmail(email);
        }catch(AppException ex){
            Assertions.assertEquals("Can't retrieve user by email", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetUserById_whenCalled_DAOCalled() throws AppException {
        int userId = 112567890;
        UserServiceImpl userService = new UserServiceImpl(userDAOMock);
        userService.getUserById(userId);

        verify(userDAOMock, times(1)).findUserById(userId);
    }

    @Test
    void testGetUserById_whenDaoThrows_throwsException() throws AppException {
        int userId = 112567890;

        when(userDAOMock.findUserById(userId)).thenThrow(new DBException());
        UserServiceImpl userService = new UserServiceImpl(userDAOMock);

        Assertions.assertThrowsExactly(AppException.class, () -> userService.getUserById(userId));
    }

    @Test
    void testGetUserById_whenDaoThrows_doesNotShowDbExceptionMessage() throws DBException {
        String messageNotToGet = "aaaaa";
        int userId = 112567890;

        when(userDAOMock.findUserById(userId)).thenThrow(new DBException(messageNotToGet));
        UserServiceImpl userService = new UserServiceImpl(userDAOMock);

        try {
            userService.getUserById(userId);
        }catch(AppException ex){
            Assertions.assertEquals("Can't retrieve user by id", ex.getMessage());
            Assertions.assertNotEquals(messageNotToGet, ex.getMessage());
            return;
        }

        Assertions.fail("Should have thrown AppException");
    }

    @Test
    void testGetUserById_whenCalled_returnsCorrectUser() throws AppException {
        int userId = 112567890;
        User expectedUser = new User();
        expectedUser.setFirstName("UserFirstName");
        expectedUser.setLastName("UserLastName");
        expectedUser.setEmail("aaa@bbb.ccc");
        expectedUser.setPassword("password");
        expectedUser.setCountry("Ukraine");
        expectedUser.setRole("client");

        when(userDAOMock.findUserById(userId)).thenReturn(expectedUser);
        UserServiceImpl userService = new UserServiceImpl(userDAOMock);

        User result = userService.getUserById(userId);

        Assertions.assertEquals(result, expectedUser);
    }
}
