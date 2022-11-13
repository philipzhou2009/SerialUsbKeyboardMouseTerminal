package de.kai_morich.simple_usb_terminal.models;

import java.util.List;

import de.kai_morich.simple_usb_terminal.enums.PingStepWorkingMode;

public class PingFlow {
    String flowName;
    List<PingStep> pingSteps;

    public PingFlow() {
    }

    public PingFlow(String flowName, List<PingStep> pingSteps) {
        this.flowName = flowName;
        this.pingSteps = pingSteps;
    }

    public String getFlowName() {
        return flowName;
    }

    public List<PingStep> getPingSteps() {
        return pingSteps;
    }

    public static class PingStep {
        String name;
        PingStepWorkingMode mode;
        List<String> keys;

        public PingStep() {
        }

        public PingStep(String name, PingStepWorkingMode mode, List<String> keys) {
            this.name = name;
            this.mode = mode;
            this.keys = keys;
        }

        public String getName() {
            return name;
        }

        public PingStepWorkingMode getMode() {
            return mode;
        }

        public List<String> getKeys() {
            return keys;
        }
    }
}
