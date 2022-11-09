package de.kai_morich.simple_usb_terminal;

import android.util.Log;

import org.junit.jupiter.api.Test;

class TextUtilTest {

    @Test
    void name() {

        String str = "57 AB";
        StringBuilder sb = new StringBuilder();
        TextUtil.toHexString(sb, TextUtil.fromHexString(str));
        TextUtil.toHexString(sb, TextUtil.newline_crlf.getBytes());
        String msg = sb.toString();

        Log.i("xxx", msg);

    }
}