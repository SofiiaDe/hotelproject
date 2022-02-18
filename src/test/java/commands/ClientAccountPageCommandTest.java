package commands;

import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.interfaces.IUserService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import com.epam.javacourse.hotel.web.command.client.ClientAccountPageCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientAccountPageCommandTest {

    @InjectMocks
    private ClientAccountPageCommand clientAccountCommand;
    
    @Mock
    private IUserService userService;
    
    @Mock
    private User user;
    
    @Mock
    private HttpServletRequest request;
    
    @Mock
    private HttpServletResponse response;
    
    @Mock
    private HttpSession session;

    @Test
    void testExecute() throws AppException {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("authorisedUser")).thenReturn(user);
        when(user.getEmail()).thenReturn("someEmail");
        when(userService.getUserByEmail("someEmail")).thenReturn(user);

        ICommandResult result = clientAccountCommand.execute(request, response);
        verify(request).setAttribute("authorisedUser", user);
        assertEquals(new AddressCommandResult(Path.PAGE_CLIENT_ACCOUNT), result);
    }

}
