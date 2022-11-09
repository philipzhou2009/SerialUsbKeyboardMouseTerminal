package de.kai_morich.simple_usb_terminal.models;

import java.util.Map;

public class CH9329KeyCodeData {
    private Map<String, String> ch9329NormalKeyCodeMap;
    private Map<String, String> ch9329ShiftKeyCodeMap;

    public Map<String, String> getCh9329NormalKeyCodeMap() {
        return this.ch9329NormalKeyCodeMap;
    }

    public void setCh9329NormalKeyCodeMap(Map<String, String> ch9329NormalKeyCodeMap) {
        this.ch9329NormalKeyCodeMap = ch9329NormalKeyCodeMap;
    }

    public Map<String, String> getCh9329ShiftKeyCodeMap() {
        return ch9329ShiftKeyCodeMap;
    }

    public void setCh9329ShiftKeyCodeMap(Map<String, String> ch9329ShiftKeyCodeMap) {
        this.ch9329ShiftKeyCodeMap = ch9329ShiftKeyCodeMap;
    }
}
