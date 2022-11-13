package de.kai_morich.simple_usb_terminal.models;

import java.util.Map;

public class CH9329KeyCodeData {
    private Map<String, String> ch9329NormalKeyCodeMap;
    private Map<String, String> ch9329ShiftKeyCodeMap;
    private Map<String, String> ch9329SpecialKeyCodeMap;
    private Map<String, String> ch9329ControlKeyCodeMap;

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

    public Map<String, String> getCh9329SpecialKeyCodeMap() {
        return ch9329SpecialKeyCodeMap;
    }

    public void setCh9329SpecialKeyCodeMap(Map<String, String> ch9329SpecialKeyCodeMap) {
        this.ch9329SpecialKeyCodeMap = ch9329SpecialKeyCodeMap;
    }

    public Map<String, String> getCh9329ControlKeyCodeMap() {
        return ch9329ControlKeyCodeMap;
    }

    public void setCh9329ControlKeyCodeMap(Map<String, String> ch9329ControlKeyCodeMap) {
        this.ch9329ControlKeyCodeMap = ch9329ControlKeyCodeMap;
    }
}
