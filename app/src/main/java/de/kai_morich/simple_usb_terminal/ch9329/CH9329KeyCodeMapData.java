package de.kai_morich.simple_usb_terminal.ch9329;

import android.content.Context;
import android.util.Log;

import java.util.Collections;

import de.kai_morich.simple_usb_terminal.R;
import de.kai_morich.simple_usb_terminal.models.CH9329KeyCodeData;
import de.kai_morich.simple_usb_terminal.utils.JSONResourceReader;

public class CH9329KeyCodeMapData {

    public static String TAG = "TerminalFragment";
    public static CH9329KeyCodeData staticCH9329KeyCodeData = null;

    public static CH9329KeyCodeData getCH9329KeyCodeData() {
        return staticCH9329KeyCodeData;
    }

    public static void setCH9329KeyCodeData(CH9329KeyCodeData ch9329KeyCodeData) {
        CH9329KeyCodeMapData.staticCH9329KeyCodeData = ch9329KeyCodeData;
    }

    public static CH9329KeyCodeData loadCH9329KeyCodeDataFromContext(Context context) {
        // https://stackoverflow.com/questions/6812003/difference-between-oncreate-and-onstart
        // Load our JSON file.
        JSONResourceReader reader = new JSONResourceReader(context.getResources(), R.raw.ch9329_key_codes);
        CH9329KeyCodeData ch9329KeyCodeData = reader.constructUsingGson(CH9329KeyCodeData.class);
        Log.i(TAG, "loadCH9329KeyCodeDataFromContext loading data:");
        Log.i(TAG, Collections.singletonList(ch9329KeyCodeData.getCh9329NormalKeyCodeMap()).toString());
        Log.i(TAG, Collections.singletonList(ch9329KeyCodeData.getCh9329ShiftKeyCodeMap()).toString());
        Log.i(TAG, "loadCH9329KeyCodeDataFromContext done");

        setCH9329KeyCodeData(ch9329KeyCodeData);
        return ch9329KeyCodeData;
    }
}
