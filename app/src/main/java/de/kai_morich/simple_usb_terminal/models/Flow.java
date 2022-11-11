package de.kai_morich.simple_usb_terminal.models;

import java.util.List;

import de.kai_morich.simple_usb_terminal.enums.InputStepWorkingMode;

public class Flow {
    String flowName;
    List<Step> steps;

    public Flow() {
    }

    public Flow(String flowName, List<Step> steps) {
        this.flowName = flowName;
        this.steps = steps;
    }

    public String getFlowName() {
        return flowName;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public static class Step {
        String name;
        InputStepWorkingMode mode;
        List<String> keys;

        public Step() {
        }

        public Step(String name, InputStepWorkingMode mode, List<String> keys) {
            this.name = name;
            this.mode = mode;
            this.keys = keys;
        }

        public String getName() {
            return name;
        }

        public InputStepWorkingMode getMode() {
            return mode;
        }

        public List<String> getKeys() {
            return keys;
        }
    }
}
