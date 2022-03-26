package commands;

import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.CommandResult;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import com.epam.javacourse.hotel.web.command.norole.HomePageCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class HomePageCommandTest {

    @InjectMocks
    private HomePageCommand homePageCommand;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Test
    void testReturnHomePage() throws AppException {
        ICommandResult result = homePageCommand.execute(request, response);
        assertInstanceOf(AddressCommandResult.class, result);
        assertEquals(Path.PAGE_HOME, ((CommandResult)result).getAddress());
    }
}
