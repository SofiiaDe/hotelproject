import com.epam.javacourse.hotel.db.interfaces.IApplicationDAO;
import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.model.Application;
import org.junit.jupiter.api.Test;



public class MockedApplicationDAO {

    private IApplicationDAO applicationDAO;

    @Test
    public void getApplicationByIdShouldReturnTrue() throws DBException {
        Application application = applicationDAO.findApplicationById(26);
//        assert()
    }
}
