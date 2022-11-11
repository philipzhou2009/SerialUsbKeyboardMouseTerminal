package de.kai_morich.simple_usb_terminal;

import java.io.InputStream;

public class TestHelper {

    public static InputStream getResourceAsStream(Object obj, String fileName) {
        return obj.getClass().getClassLoader().getResourceAsStream(fileName);
    }
}
