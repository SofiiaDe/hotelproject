import com.epam.javacourse.hotel.Security;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.epam.javacourse.hotel.Security.validatePasswordByHash;

class SecurityTest {

    // These test were for first variant of hashing ==> to be altered

//    @Test
//    void hashPasswordValidCaseTest() throws NoSuchAlgorithmException, InvalidKeySpecException {
//        String originalPassword = "password";
//        assertTrue(validatePasswordByHash("password",  Security.generatePasswordHash(originalPassword)));
//    }
//
//    @Test
//    void hashPasswordInvalidCaseTest() throws NoSuchAlgorithmException, InvalidKeySpecException {
//        String originalPassword = "password";
//        assertFalse(validatePasswordByHash("password1",  Security.generatePasswordHash(originalPassword)));
//    }

}
