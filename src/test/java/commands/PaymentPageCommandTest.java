package commands;

import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.CommandResult;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import com.epam.javacourse.hotel.web.command.client.PaymentPageCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ExtendWith(MockitoExtension.class)
class PaymentPageCommandTest {

    @InjectMocks
    private PaymentPageCommand paymentPageCommand;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Test
    void testReturnPaymentPage() throws AppException {
        ICommandResult result = paymentPageCommand.execute(request, response);
        assertInstanceOf(AddressCommandResult.class, result);
        assertEquals(Path.PAGE_PAY_INVOICE, ((CommandResult)result).getAddress());
    }
}
