package commands;

import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.exception.HashPasswordException;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.interfaces.IUserService;
import com.epam.javacourse.hotel.utils.Security;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.CommandResult;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import com.epam.javacourse.hotel.web.command.RedirectCommandResult;
import com.epam.javacourse.hotel.web.command.norole.LoginCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginCommandTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    IUserService userService;

    @Mock
    HttpSession session;

    @InjectMocks
    LoginCommand loginCommand;

    User client = new User();
    User manager = new User();

    @BeforeEach
    public void setUp() throws HashPasswordException {
        client.setEmail("client");
        client.setPassword(Security.validatePasswordByHash("pass"));
        client.setRole("client");

        manager.setEmail("manager");
        manager.setPassword(Security.validatePasswordByHash("pass"));
        manager.setRole("manager");
    }

    @Test
    void ifUserIsClient() throws AppException {
        when(request.getParameter("email")).thenReturn("client");
        when(request.getParameter("password")).thenReturn("pass");
        when(userService.getUserByEmail("client")).thenReturn(client);
        when(request.getSession()).thenReturn(session);

        ICommandResult result = loginCommand.execute(request, response);
        assertInstanceOf(RedirectCommandResult.class, result);
        assertEquals(Path.COMMAND_CLIENT_ACCOUNT, ((CommandResult)result).getAddress());
    }

    @Test
    void ifUserIsManager() throws AppException {
        when(request.getParameter("email")).thenReturn("manager");
        when(request.getParameter("password")).thenReturn("pass");
        when(userService.getUserByEmail("manager")).thenReturn(manager);
        when(request.getSession()).thenReturn(session);

        ICommandResult result = loginCommand.execute(request, response);
        assertInstanceOf(RedirectCommandResult.class, result);
        assertEquals(Path.COMMAND_MANAGER_ACCOUNT, ((CommandResult)result).getAddress());
    }

    @Test
    void ifUserIsBlank() throws AppException {
        when(request.getParameter("email")).thenReturn(null);
        when(request.getParameter("password")).thenReturn(null);
        when(request.getSession()).thenReturn(session);

        ICommandResult result = loginCommand.execute(request, response);
        assertInstanceOf(AddressCommandResult.class, result);
        assertEquals(Path.PAGE_ERROR, ((CommandResult)result).getAddress());

    }

    @Test
    void ifPasswordIsIncorrect() throws AppException {
        when(request.getParameter("email")).thenReturn("manager");
        when(request.getParameter("password")).thenReturn("pass__");
        when(userService.getUserByEmail("manager")).thenReturn(manager);
        when(request.getSession()).thenReturn(session);

        ICommandResult result = loginCommand.execute(request, response);
        assertInstanceOf(AddressCommandResult.class, result);
        assertEquals(Path.PAGE_ERROR, ((CommandResult)result).getAddress());

    }
}