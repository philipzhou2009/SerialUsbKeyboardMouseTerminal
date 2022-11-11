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

import de.kai_morich.simple_usb_terminal.enums.InputStepWorkingMode;
import de.kai_morich.simple_usb_terminal.models.Flow;

public class PredefinedFlowDataServiceTest {

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

        List<Flow> actual = subject.getPredefinedFlows();

        assertNotNull(actual);
        assertEquals(actual.size(), 1);
        Flow flow = actual.get(0);

        assertEquals("Normal Login", flow.getFlowName());
        List<Flow.Step> steps = flow.getSteps();
        assertEquals(4, steps.size());
        Flow.Step step0 = steps.get(0);

        assertEquals("sid", step0.getName());
        assertEquals(InputStepWorkingMode.SEQ, step0.getMode());
        assertEquals(new ArrayList<>(Collections.singletonList("r695898")) , step0.getKeys());
    }


}