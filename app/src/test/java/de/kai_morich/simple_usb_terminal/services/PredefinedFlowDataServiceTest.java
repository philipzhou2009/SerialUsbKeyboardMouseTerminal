package de.kai_morich.simple_usb_terminal.services;

import static org.junit.jupiter.api.Assertions.*;

import android.content.res.Resources;

import org.junit.jupiter.api.Test;

import java.io.InputStream;

import de.kai_morich.simple_usb_terminal.utils.JSONResourceReader;

class PredefinedFlowDataServiceTest {
    private PredefinedFlowDataService subject = new PredefinedFlowDataService(null);

    @Test
    void loadPredefinedFlowData() {

        InputStream in = this.getClass().getClassLoader().getResourceAsStream("ch9329_key_codes.json");

        JSONResourceReader reader = new JSONResourceReader(in);




    }
}