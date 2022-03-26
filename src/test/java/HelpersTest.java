import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.javacourse.hotel.db.Helpers.enrichWithPageSizeStatement;
import static com.epam.javacourse.hotel.web.command.helpers.Helpers.parsePage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HelpersTest {

    @Mock
    HttpServletRequest request;

    @Test
    void testEnrichWithPageSizeStatement_incorrectPage() {
        String inputSql = "SELECT something FROM somewhere";
        String result = enrichWithPageSizeStatement(-1, 5, inputSql);
        assertEquals(inputSql, result);
    }

    @Test
    void testEnrichWithPageSizeStatement_returnCorrectSQLLimit() {
        String inputSql = "SELECT something FROM somewhere";
        String result = enrichWithPageSizeStatement(1, 5, inputSql);
        String expected =  "SELECT something FROM somewhere LIMIT 5";
        assertEquals(expected, result);
    }

    @Test
    void testEnrichWithPageSizeStatement_returnCorrectSQLLimitOffset() {
        String inputSql = "SELECT something FROM somewhere";
        String result = enrichWithPageSizeStatement(3, 5, inputSql);
        String expected =  "SELECT something FROM somewhere LIMIT 5 OFFSET 10";
        assertEquals(expected, result);
    }

    @Test
    void testParsePage_correctPageParam() {
        when(request.getParameter("page")).thenReturn("3");
        assertEquals(3, parsePage(request));
    }

    @Test
    void testParsePage_incorrectPageParam() {
        when(request.getParameter("page")).thenReturn("-4");
        assertEquals(1, parsePage(request));
    }

}
