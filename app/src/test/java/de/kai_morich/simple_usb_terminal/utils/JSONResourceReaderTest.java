package de.kai_morich.simple_usb_terminal.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static de.kai_morich.simple_usb_terminal.TestHelper.getResourceAsStream;

import android.util.Log;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import de.kai_morich.simple_usb_terminal.models.CH9329KeyCodeData;

class JSONResourceReaderTest {

    private final String TAG = "JSONResourceReaderTest";

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

    @Test
    void givenFullCH9329KeyCodeData_convertCharIntoCH9329KeyPressCode_canConvertAll() {
        InputStream in = getResourceAsStream(this, "ch9329_key_codes.json");
        JSONResourceReader reader = new JSONResourceReader(in);
        CH9329KeyCodeData ch9329KeyCodeData = reader.constructUsingGson(CH9329KeyCodeData.class);

        assertNotNull(ch9329KeyCodeData);
        Map<String, String> ch9329NormalKeyCodeMap = ch9329KeyCodeData.getCh9329NormalKeyCodeMap();
        assertEquals(48, ch9329NormalKeyCodeMap.size());
        Log.i(TAG, "loaded ch9329NormalKeyCodeMap=" + new ArrayList<>(ch9329NormalKeyCodeMap.entrySet()));

        assertEquals(47, ch9329KeyCodeData.getCh9329ShiftKeyCodeMap().size());
        assertEquals(2, ch9329KeyCodeData.getCh9329SpecialKeyCodeMap().size());
        assertEquals(3, ch9329KeyCodeData.getCh9329ControlKeyCodeMap().size());
    }
}