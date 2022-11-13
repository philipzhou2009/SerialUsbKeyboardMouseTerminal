package de.kai_morich.simple_usb_terminal;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static de.kai_morich.simple_usb_terminal.CH9329Util.convertCharIntoCH9329KeyPressCode;
import static de.kai_morich.simple_usb_terminal.CH9329Util.getSimultaneousKeysPressData;
import static de.kai_morich.simple_usb_terminal.MiscUtil.LogByteArray;

import android.util.Log;

import androidx.annotation.NonNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import de.kai_morich.simple_usb_terminal.models.CH9329KeyCodeData;

public class CH9329UtilTest {

    private static final String TAG = "CH9329UtilTest";
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
    void givenString_convertStringToCH9329Code_returnConvertedCodes() {
//        byte[] actual = convertStringToCH9329Code("a");
//        assertNotNull(actual);
        MiscUtil.LogByteArray("", TextUtil.newline_crlf.getBytes());
    }

    @Test
    void givenCharAndCH9329KeyCodeData_convertCharIntoCH9329KeyPressCode_returnsKeyPressCode() {
        CH9329KeyCodeData ch9329KeyCodeData = new CH9329KeyCodeData();

        HashMap<String, String> ch9329NormalKeyCodeMap = new HashMap<>();
        ch9329NormalKeyCodeMap.put("a", "04");
        ch9329KeyCodeData.setCh9329NormalKeyCodeMap(ch9329NormalKeyCodeMap);

        HashMap<String, String> ch9329ShiftKeyCodeMap = new HashMap<>();
        ch9329ShiftKeyCodeMap.put("@", "1F");
        ch9329KeyCodeData.setCh9329ShiftKeyCodeMap(ch9329ShiftKeyCodeMap);

        byte[] actual = convertCharIntoCH9329KeyPressCode('a', ch9329KeyCodeData);

        assertArrayEquals(new byte[]{0x57, (byte) 0xAB, 0x00, 0x02, 0x08, 0x00, 0x00, 0x04, 0x00, 0x00, 0x00, 0x00, 0x00, 0x10}, actual);

        actual = convertCharIntoCH9329KeyPressCode('@', ch9329KeyCodeData);

        assertArrayEquals(new byte[]{0x57, (byte) 0xAB, 0x00, 0x02, 0x08, 0x02, 0x00, 0x1F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x2d}, actual);
    }

    @ParameterizedTest
    @CsvSource({"Tab", "Ctrl_Alt_Delete"})
    void name(String input) {
        CH9329KeyCodeData ch9329KeyCodeData = createCh9329KeyCodeData();

        String[] inputArray = input.split("_");
        List<String> inputList = Arrays.asList(inputArray);

        byte[] actual = getSimultaneousKeysPressData(inputList, ch9329KeyCodeData);

        LogByteArray(TAG, "test result:", actual);
        assertNotNull(actual);
    }

    @NonNull
    private CH9329KeyCodeData createCh9329KeyCodeData() {
        CH9329KeyCodeData ch9329KeyCodeData = new CH9329KeyCodeData();
        HashMap<String, String> ch9329NormalKeyCodeMap = new HashMap<String, String>() {{
            put("a", "04");
            put("b", "05");
        }};
        ch9329KeyCodeData.setCh9329NormalKeyCodeMap(ch9329NormalKeyCodeMap);

        HashMap<String, String> ch9329ShiftKeyCodeMap = new HashMap<String, String>() {{
            put("A", "04");
            put("B", "05");
        }};
        ch9329KeyCodeData.setCh9329ShiftKeyCodeMap(ch9329ShiftKeyCodeMap);

        HashMap<String, String> ch9329SpecialKeyCodeMap = new HashMap<String, String>() {{
            put("Tab", "2B");
            put("Delete", "4C");
        }};
        ch9329KeyCodeData.setCh9329SpecialKeyCodeMap(ch9329SpecialKeyCodeMap);

        HashMap<String, String> ch9329ControlKeyCodeMap = new HashMap<String, String>() {{
            put("Ctrl", "01");
            put("Shift", "02");
            put("Alt", "04");
        }};
        ch9329KeyCodeData.setCh9329ControlKeyCodeMap(ch9329ControlKeyCodeMap);

        return ch9329KeyCodeData;
    }
}