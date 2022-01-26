import com.epam.javacourse.hotel.Security;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.epam.javacourse.hotel.Security.validatePasswordByHash;



class SecurityTest {

    @Test
    void hashPasswordValidCaseTest(String originalPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {

        assertTrue(validatePasswordByHash("password",  Security.generatePasswordHash("password")));
    }

    @Test
    void hashPasswordInvalidCaseTest(String originalPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {

        assertFalse(validatePasswordByHash("password1",  Security.generatePasswordHash("password")));
    }
}
