package edu.bupt.checkinsystem.util;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class PasswordUtils {
    private static final int HASH_LENGTH = 128 / 2;

    public static boolean checkPassword(Object hash, String password) {
        byte[] binary = DatatypeConverter.parseHexBinary((String) hash);
        int i; for (i = 0; i < binary.length && binary[i] != 0; i++) {}
        return password.equals(new String(binary, 0, i, StandardCharsets.UTF_8));
    }

    public static String generateHash(String password) {
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        String plain = DatatypeConverter.printHexBinary(passwordBytes);
        Random random = new Random();
        byte[] paddingBytes = new byte[HASH_LENGTH - 1 - passwordBytes.length];
        random.nextBytes(paddingBytes);
        String padding = DatatypeConverter.printHexBinary(paddingBytes);
        return plain + "00" + padding;
    }
}
