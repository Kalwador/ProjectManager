package com.project.manager.utils;
import java.security.Key;
import javax.crypto.Cipher;
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;
import javax.crypto.spec.SecretKeySpec;

/**
 * This is the class which provides basic method to cipher some data
 */
public class AESAlgorithm {
    /**
     * This is the type of cipher algoritm
     */
    private static final String ALGORITHM = "AES";

    /**
     * This is the key table we store here the key to cipher some data
     */
    private static final byte[] key =
            new byte[]{'T','h','i','s','I','s','S','e','c','r','e','t','K','e','y','!'};

    /**
     * Encrypt a String with AES algorithm.
     *
     * @param data is a string
     * @return the encrypted string
     */
    public static String encrypt(String data) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encodeValue = cipher.doFinal(data.getBytes());
        return new BASE64Encoder().encode(encodeValue);
    }

    /**
     * Decrypt a string with AES algorithm.
     *
     * @param encryptedData is a string
     * @return the decrypted string
     */
    public static String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = cipher.doFinal(decodedValue);
        return new String(decValue);
    }

    /**
     * Generate a new encryption key.
     */
    private static Key generateKey() throws Exception {
        return new SecretKeySpec(key, ALGORITHM);
    }
}
