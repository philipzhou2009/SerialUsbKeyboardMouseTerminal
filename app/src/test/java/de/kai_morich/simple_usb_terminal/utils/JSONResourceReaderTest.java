package de.kai_morich.simple_usb_terminal.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import android.util.Log;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Collections;

import de.kai_morich.simple_usb_terminal.models.CH9329KeyCodeData;

class JSONResourceReaderTest {

    @Test
    void givenDataJson_JSONResourceReader_readsKeyCodeMap() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("ch9329_key_codes.json");

        JSONResourceReader reader = new JSONResourceReader(in);

        assertNotNull(reader);

        CH9329KeyCodeData ch9329KeyCodeData = reader.constructUsingGson(CH9329KeyCodeData.class);

        assertNotNull(ch9329KeyCodeData);

        Log.i("", Collections.singletonList(ch9329KeyCodeData.getCh9329NormalKeyCodeMap()).toString());
        Log.i("", Collections.singletonList(ch9329KeyCodeData.getCh9329ShiftKeyCodeMap()).toString());
    }
}