package de.kai_morich.simple_usb_terminal;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class CH9329UtilTest {

    @Test
    void fromStringToHex() {
    }

    @Test
    void fromCharToHex() {

        byte[] actual = CH9329Util.fromCharToHex('a');
        LogUtil.i("", Arrays.toString(actual));

        assertNotNull(actual);
    }
}