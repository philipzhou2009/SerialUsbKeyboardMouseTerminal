package de.kai_morich.simple_usb_terminal.ch9329;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.kai_morich.simple_usb_terminal.enums.CH9329ResponseStatus;

class CH9329ResponseDataServiceTest {

    private final static String TAG = "CH9329ResponseDataServiceTest";
    private CH9329ResponseDataService subject = new CH9329ResponseDataService();

    @BeforeEach
    void setUp() {
    }

    @Test
    void givenTimeAndResponseData_getResponseResult_returnsResponseResult() {
        subject.collectReceivingData(new byte[]{0x57, (byte) 0xAB});
        subject.collectReceivingData(new byte[]{(byte) 0x00, (byte) 0x82, (byte) 0x01, (byte) 0x00, (byte) 0x85});

        CH9329ResponseStatus actual = subject.getCurrentResponseStatus();

        assertEquals(CH9329ResponseStatus.SUCCESS, actual);
    }

    @Test
    void collectReceivingDataIntoTmpStorage0() {
    }

    @Test
    void resetReceivingDataStorage0() {
    }

    @Test
    void getReceivingData0() {
    }
}