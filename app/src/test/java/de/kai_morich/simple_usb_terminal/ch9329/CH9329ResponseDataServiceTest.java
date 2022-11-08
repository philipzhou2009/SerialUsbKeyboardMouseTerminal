package de.kai_morich.simple_usb_terminal.ch9329;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void givenReceivingData_collectReceivingDataIntoTmpStorage_copyItToStorage() {
        boolean actual = subject.collectReceivingDataIntoTmpStorage(new byte[]{0x57, (byte) 0xAB});

        assertTrue(actual);

        int[] receivingData = subject.getReceivingData();

        assertArrayEquals(new int[]{0x57, (byte) 0xAB, 888, 888, 888, 888, 888}, receivingData);
    }

    @Test
    void givenTimeAndResponseData_getResponseResult_returnsResponseResult() {
        subject.collectReceivingData(new byte[]{0x57, (byte) 0xAB});
        subject.collectReceivingData(new byte[]{(byte) 0x00, (byte) 0x82, (byte) 0x01, (byte) 0x00, (byte) 0x85});

        subject.setResponseStartTime(1667892312806L);
        CH9329ResponseStatus actual = subject.getResponseResult();

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