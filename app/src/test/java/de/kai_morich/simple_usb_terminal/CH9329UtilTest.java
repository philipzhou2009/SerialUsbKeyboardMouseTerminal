package de.kai_morich.simple_usb_terminal;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class CH9329UtilTest {

    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);

    public static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }

    public static String bytesToHexWithSpace(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 3];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 3] = HEX_ARRAY[v >>> 4];
            hexChars[j * 3 + 1] = HEX_ARRAY[v & 0x0F];
            hexChars[j * 3 + 2] = " ".getBytes(StandardCharsets.UTF_8)[0];
        }
        String s = new String(hexChars, StandardCharsets.UTF_8);

        return s;
    }

    @Test
    void fromStringToHex() {
    }

    @Test
    void givenChar_fromCharToHex_returnsCH9329HexCodes() {

        byte[] actual = CH9329Util.fromCharToHex('a');
        LogUtil.i("fromCharToHex", Arrays.toString(actual));
        LogUtil.i("fromCharToHex", bytesToHexWithSpace(actual));

        assertNotNull(actual);
    }


}