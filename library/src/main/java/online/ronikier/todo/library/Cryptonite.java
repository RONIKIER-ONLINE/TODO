package online.ronikier.todo.library;// Java program for Generating Hashes

import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class Cryptonite {

    public final static String MESSAGE_DIGEST_ALGORITHM = "SHA-256";
    public final static String MESSAGE_ENCODING = "UTF-8";

    public static String encodeHex(String message) {

        try {
            return Hex.encodeHexString(MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM).digest(message.getBytes(MESSAGE_ENCODING)));
        } catch (Exception e) {
            log.warn(Messages.ENCODER_EXCEPTION);
            throw new RuntimeException(e);
        }

    }

    public static String encode(String message) {

        try {
            return new String(Base64.encodeBase64(message.getBytes()));
        } catch (Exception e) {
            log.warn(Messages.ENCODER_EXCEPTION);
            throw new RuntimeException(e);
        }

    }

    public static String decode(String message) {

        try {
            return new String(Base64.decodeBase64(message.getBytes()));
        } catch (Exception e) {
            log.warn(Messages.DECODER_EXCEPTION);
            throw new RuntimeException(e);
        }

    }


}
