package com.epam.javacourse.hotel;

import com.epam.javacourse.hotel.Exception.HashPasswordException;
import com.epam.javacourse.hotel.Exception.ReadPropertyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * PBKDF2 salted String (password) hashing.
 */
public class Security {

    private static final Logger logger = LogManager.getLogger(Security.class);

    private static final String SECRET_FACTORY_ALGORITHM = "PBKDF2WithHmacSHA512";
    private static final int HASH_BYTES = 64;
    private static final int PBKDF2_ITERATIONS = 10000;


    public static String validatePasswordByHash(String pass) throws HashPasswordException {
        String salt;
        try {
            String passSaltProps = "password.salt";
            String propsFile = "app.properties";
            salt = PropertiesReader.readProps(propsFile, passSaltProps);
        } catch (ReadPropertyException e) {
            throw new HashPasswordException("Property Salt was not found", e);
        }
        String resultHash;
        try {
            resultHash = Security.createHashFromPassString(pass, salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new HashPasswordException("Hash generation error", ex);
        }
        return resultHash;
    }

    /**
     *
     * @param password Password string to be converted
     * @param salt String salt which is added to pass hash
     * @return String Hash of password with hash
     */
    public static String createHashFromPassString(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return createHashFromCharArray(password.toCharArray(), salt.toCharArray());

    }

    private static String createHashFromCharArray(char[] password, char[] saltArray) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = Security.toByteArray(saltArray);
        byte[] hash = pbkdf2(password, salt);
        return toHex(hash);
    }

    private static byte[] pbkdf2(char[] chars, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(chars, salt, Security.PBKDF2_ITERATIONS, Security.HASH_BYTES * 8);
        SecretKeyFactory skf =
                SecretKeyFactory.getInstance(SECRET_FACTORY_ALGORITHM);
        return skf.generateSecret(spec).getEncoded();

    }

    private static String toHex(byte[] array) {
        BigInteger bigInteger = new BigInteger(1, array);
        String hex = bigInteger.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    private static byte[] toByteArray(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data

        return bytes;
    }
}
