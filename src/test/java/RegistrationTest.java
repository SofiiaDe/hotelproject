import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.epam.javacourse.hotel.utils.Validator.validateEmail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class RegistrationTest {

    @ParameterizedTest(name = "{index} \"{0}\" is not a valid email")
    @NullAndEmptySource
    @ValueSource(strings = {
            " ",
            "",
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

    @ParameterizedTest(name = "{index} \"{0}\" is not a valid email")
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


}
