package de.kai_morich.simple_usb_terminal;

import java.util.HashMap;
import java.util.Map;

public class CH9329KeyCodeMap {

    public static Map<Character, Byte> ch9329NormalKeyCodeMap = new HashMap<>();
    static {
        ch9329NormalKeyCodeMap.put('a', (byte) 0x04);
        ch9329NormalKeyCodeMap.put('s', (byte) 0x16);
        ch9329NormalKeyCodeMap.put('d', (byte) 0x07);
        ch9329NormalKeyCodeMap.put('f', (byte) 0x09);
    }
}
