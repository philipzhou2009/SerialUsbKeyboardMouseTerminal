package de.kai_morich.simple_usb_terminal.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static de.kai_morich.simple_usb_terminal.TestHelper.getResourceAsStream;

import android.content.res.Resources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.kai_morich.simple_usb_terminal.enums.PingStepWorkingMode;
import de.kai_morich.simple_usb_terminal.models.PingFlow;

public class PredefinedPingFlowDataServiceTest {

    private final PredefinedFlowDataService subject = new PredefinedFlowDataService(Mockito.mock(Resources.class));

    @BeforeEach
    void beforeEach() {
        subject.setFlowsResId(888);
    }

    @Test
    void loadPredefinedFlowData() {
        InputStream inputStream = getResourceAsStream(this, "flows.json");
        Mockito.when(subject.getResources().openRawResource(anyInt())).thenReturn(inputStream);

        subject.loadPredefinedFlowData();

        List<PingFlow> actual = subject.getPredefinedFlows();

        assertNotNull(actual);
        assertEquals(actual.size(), 4);

        PingFlow pingFlow = actual.get(0);
        assertNotNull(pingFlow.getFlowName());
        List<PingFlow.PingStep> pingSteps = pingFlow.getPingSteps();
        assertEquals(4, pingSteps.size());

        PingFlow.PingStep pingStep0 = pingSteps.get(0);
        assertEquals("sid", pingStep0.getName());
        assertEquals(PingStepWorkingMode.SEQ, pingStep0.getMode());
        assertEquals(new ArrayList<>(Collections.singletonList("r695898")), pingStep0.getKeys());
    }
}