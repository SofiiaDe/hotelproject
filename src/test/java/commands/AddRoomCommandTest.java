package commands;

import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.model.Room;
import com.epam.javacourse.hotel.model.service.interfaces.IRoomService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import com.epam.javacourse.hotel.web.command.manager.AddRoomCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class AddRoomCommandTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    IRoomService roomService;

    @InjectMocks
    AddRoomCommand addRoomCommand;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute() throws AppException {
        Mockito.when(request.getParameter("roomSeats")).thenReturn("double");
        Mockito.when(request.getParameter("roomClass")).thenReturn("standard");
        Mockito.when(request.getParameter("roomNumber")).thenReturn("50");
        Mockito.when(request.getParameter("price")).thenReturn("300");
        Mockito.when(request.getParameter("roomStatus")).thenReturn("available");
        ICommandResult result = addRoomCommand.execute(request, response);
        assertEquals(Path.COMMAND_MANAGER_ACCOUNT, result);
        Mockito.verify(roomService, Mockito.times(1)).create(any(Room.class));

    }
}
