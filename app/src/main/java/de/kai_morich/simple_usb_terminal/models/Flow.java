package de.kai_morich.simple_usb_terminal.models;

import java.util.List;

import de.kai_morich.simple_usb_terminal.enums.InputStepWorkingMode;

public class Flow {
    String name;
    List<Step> steps;

    public static class Step {
        String name;
        InputStepWorkingMode mode;
        List<String> keys;
    }
}
