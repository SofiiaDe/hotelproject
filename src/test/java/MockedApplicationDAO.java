import com.epam.javacourse.hotel.db.dao.ApplicationDAO;
import com.epam.javacourse.hotel.db.interfaces.IApplicationDAO;
import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.Application;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class MockedApplicationDAO {

    private IApplicationDAO applicationDAO;

    @BeforeEach
    void setUp() throws Exception {
        this.applicationDAO = new ApplicationDAO();
    }

    @Test
    void getApplicationByIdShouldReturnTrue() throws DBException {
        Application application = applicationDAO.findApplicationById(26);
        assertNotNull(application);
        assertEquals(26, application.getId());
    }
}
