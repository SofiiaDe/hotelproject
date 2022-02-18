package services;

import com.epam.javacourse.hotel.db.dao.ConfirmRequestDAO;
import com.epam.javacourse.hotel.db.interfaces.IConfirmRequestDAO;
import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.ConfirmationRequest;
import com.epam.javacourse.hotel.model.service.impl.ConfirmRequestServiceImpl;
import com.epam.javacourse.hotel.model.service.interfaces.IConfirmRequestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConfirmRequestServiceTest {

    @InjectMocks
    private IConfirmRequestService confirmRequestService;

    @Mock
    private IConfirmRequestDAO confirmRequestDAO;

    @Mock
    private ConfirmationRequest confirmationRequest;

    @Test
    void testCreate() throws DBException {
//        confirmRequestService.create();
        verify(confirmRequestDAO).createConfirmRequest(any(ConfirmationRequest.class));
    }
}
