import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static com.epam.javacourse.hotel.utils.Validator.validateEmail;
import static com.epam.javacourse.hotel.utils.Validator.validatePassword;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class RegistrationTest {

    @ParameterizedTest(name = "{index} \"{0}\" is not a valid email")
    @ValueSource(strings = {
            " fast.mary@hot.com",
            "ann.flex@gmail@com",
            "alice.example.com",
            "ron.gimmy@epamcom",
            "william...shakespeare@yandex.ru",
            "bob_$bob@ukr.net",
            ".shakespeare123@hotmail.com",
            "hello",
            "tanya.@example.com",
            "nick!+2022@test.com",
            "kate@com.a",
            "roma@example..com",
            "jade@.com",
            "garry@.com.",
            "polina@-gmail.com",

    })
    void validateEmailInvalidCases(String input) {
        String result = validateEmail(input, 50);
        String expected = "field \"email\" has invalid symbols";
        assertEquals(expected, result);
    }

    @Test
    void validateEmailEmptyCase() {
        String result = validateEmail(null, 20);
        String expected = "field \"email\" cannot be empty";
        assertEquals(expected, result);
    }

    @ParameterizedTest(name = "{index} \"{0}\" is a valid email")
    @ValueSource(strings = {
            "tony@example.co.uk",
            "william_shakespeare1@epam.com",
            "william@gmail.com",
            "william.adam3@yahoo.com",
            "_karamel@hotmail.com",
            "william-shakespeare@gmail1.com",
            "hello.ann-2022@test.com",
            "boyko_denys@example.com",
            "h@hotmail.com",
            "h@example-example.com",
            "h@test-test-test.com",
            "h@test.tset-test.com",
            "hello.mary-2022@example.com",

    })
    void validateEmailValidCases(String input) {
        String result = validateEmail(input, 50);
        assertNull(result);
    }


    @ParameterizedTest(name = "{index} \"{0}\" is not a valid password")
    @ValueSource(strings = {
            " fast.mary",
            "ann.flex@",
            "alice.example.com",
            "william...shakespeare",
            "bob_$bob@4 ",
            ".shakespeare123",
            "hello",
            "tanya@5",
            "!2022@R",
            "kate@co1",
            "garry741",
            "polina@",
            "its_too_long_password_thus_invalid_9988777666555@"

    })
    void validatePasswordInvalidCases(String input) {
        String result = validatePassword(input, 8, 20);
        String expected =
                "field \"password\"" +
                        " must contain at least one digit, at least one lowercase and one uppercase Latin characters " +
                        "as well as at least one special character like ! @ # & ( ) etc.\n " +
                        "Password must contain a length of at least 8 characters and a maximum of 20 characters.";
        assertEquals(expected, result);
    }

    @Test
    void validatePasswordEmptyCase() {
        String result = validatePassword("", 8, 20);
        String expected = "field \"password\" cannot be empty";
        assertEquals(expected, result);
    }

    @ParameterizedTest(name = "{index} \"{0}\" is a valid password")
    @ValueSource(strings = {
            "tony@example.Co.uk5",
            "Shakespeare1@32",
            "_Karamel@21",
            "william-@Gmail1.com",
            "hello.Ann-2022@test",
            "H@test234",
            "Mary-2022",
            "Aaaa111#",

    })
    void validatePasswordValidCases(String input) {
        String result = validatePassword(input, 8, 20);
        assertNull(result);
    }
}
