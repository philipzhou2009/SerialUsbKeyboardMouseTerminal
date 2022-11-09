package de.kai_morich.simple_usb_terminal;

import java.util.HashMap;
import java.util.Map;

public class CH9329KeyCodeMap {

    private static Map<Character, String> ch9329KeyCodes = null;

    public static Map<Character, String> getCh9329KeyCodes() {
        // Load our JSON file.
//        JSONResourceReader reader = new JSONResourceReader(getActivity().getApplicationContext(), R.raw.ch9329_key_codes);
//        MyJsonObject jsonObj = reader.constructUsingGson(MyJsonObject.class);
        return null;
    }

    public static Map<Character, Byte> ch9329NormalKeyCodeMap = new HashMap<>();

    static {
        ch9329NormalKeyCodeMap.put('a', (byte) 0x04);
        ch9329NormalKeyCodeMap.put('s', (byte) 0x16);
        ch9329NormalKeyCodeMap.put('d', (byte) 0x07);
        ch9329NormalKeyCodeMap.put('f', (byte) 0x09);
    }
}
