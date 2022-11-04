package de.kai_morich.simple_usb_terminal;

import static de.kai_morich.simple_usb_terminal.CH9329KeyCodeMap.ch9329NormalKeyCodeMap;
import static de.kai_morich.simple_usb_terminal.MiscUtil.LogByteArray;
import static de.kai_morich.simple_usb_terminal.MiscUtil.concatenateByteArrays;

import android.content.Context;
import android.util.Log;

import java.util.Collections;
import java.util.Map;

import de.kai_morich.simple_usb_terminal.models.CH9329KeyCodeData;
import de.kai_morich.simple_usb_terminal.utils.JSONResourceReader;

public class CH9329Util {

    public static String tag = "TerminalFragment";
    public static CH9329KeyCodeData staticCH9329KeyCodeData = null;

    public static CH9329KeyCodeData getCH9329KeyCodeData() {
        return staticCH9329KeyCodeData;
    }

    public static void setCH9329KeyCodeData(CH9329KeyCodeData ch9329KeyCodeData) {
        CH9329Util.staticCH9329KeyCodeData = ch9329KeyCodeData;
    }

    public static void loadCH9329KeyCodeData(Context context) {
        // https://stackoverflow.com/questions/6812003/difference-between-oncreate-and-onstart
        // Load our JSON file.
        JSONResourceReader reader = new JSONResourceReader(context.getResources(), R.raw.ch9329_key_codes);
        CH9329KeyCodeData ch9329KeyCodeData = reader.constructUsingGson(CH9329KeyCodeData.class);
        Log.i("TerminalFragment", Collections.singletonList(ch9329KeyCodeData.getCh9329NormalKeyCodeMap()).toString());
        Log.i("TerminalFragment", Collections.singletonList(ch9329KeyCodeData.getCh9329ShiftKeyCodeMap()).toString());

        staticCH9329KeyCodeData = ch9329KeyCodeData;
    }

    public static byte[] getSendingKeyCode(String str) {
        byte[] ch9329Code = convertStringToCH9329Code(str);
        return addNewLineToCH9329Code(ch9329Code);
    }

    public static byte[] addNewLineToCH9329Code(byte[] originalBytes) {
        return concatenateByteArrays(originalBytes, new byte[]{0x0D, 0x0A});
    }

    public static byte[] convertStringToCH9329Code(String str) {
        byte[] tmp = new byte[0];
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            byte[] keyPressData = getKeyPressData(c);
            byte[] keyReleaseData = getKeyReleaseData();
            byte[] pressReleaseForOneKey = concatenateByteArrays(keyPressData, keyReleaseData);
            tmp = concatenateByteArrays(tmp, pressReleaseForOneKey);
        }

        LogByteArray("convertStringToCH9329Code", tmp);
        return tmp;
    }

    public static byte[] fromCharToHex(char c) {
        final String tag = "fromCharToHex";
        LogUtil.i(tag, ch9329NormalKeyCodeMap.toString());

        byte dataFrameHead01 = 0x57;
        byte dataFrameHead02 = (byte) 0xAB;
        byte addressCode = 0x00;
        byte commandCode = 0x02;
        byte dataLength = 0x08;

        byte dataFirstCode = 0x00;
        byte dataSecondCode = 0x00;
        byte dataThirdCode = ch9329NormalKeyCodeMap.get(c);

        byte[] codesWithoutSum = {dataFrameHead01, dataFrameHead02, addressCode, commandCode, dataLength, dataFirstCode, dataSecondCode, dataThirdCode, 0x0, 0x0, 0x0, 0x0, 0x0};

        // sum code
        byte sum = 0;
        for (byte b : codesWithoutSum) {
            sum += b;
        }
        LogUtil.i(tag, "sum=" + sum);

        byte[] result = addX(13, codesWithoutSum, sum);
        return result;
    }

    public static byte[] buildCH9329KeyPressCode(byte keyCode, boolean withShift) {
        byte dataFrameHead01 = 0x57;
        byte dataFrameHead02 = (byte) 0xAB;
        byte addressCode = 0x00;
        byte commandCode = 0x02;
        byte dataLength = 0x08;

        byte dataFirstCode = withShift ? (byte) 0x02 : 0x00;
        byte dataSecondCode = 0x00;
        byte dataThirdCode = keyCode;

        byte[] codesWithoutSum = {dataFrameHead01, dataFrameHead02, addressCode, commandCode, dataLength, dataFirstCode, dataSecondCode, dataThirdCode, 0x0, 0x0, 0x0, 0x0, 0x0};

        // sum code
        byte sum = 0;
        for (byte b : codesWithoutSum) {
            sum += b;
        }
        LogUtil.i("", "sum=" + sum);

        return addX(13, codesWithoutSum, sum);
    }

    public static byte[] fromCharToHexNew(char c) {

        CH9329KeyCodeData ch9329KeyCodeData = getCH9329KeyCodeData();

        Map<String, String> ch9329ShiftKeyCodeMap = ch9329KeyCodeData.getCh9329ShiftKeyCodeMap();
        Map<String, String> ch9329NormalKeyCodeMap = ch9329KeyCodeData.getCh9329NormalKeyCodeMap();

        String keyCodeStr = ch9329NormalKeyCodeMap.get(String.valueOf(c));
        String keyCodeWithShift = ch9329ShiftKeyCodeMap.get(String.valueOf(c));
//        byte resultKeyCode = 0;
        int keyCodeInt = 0;
        byte[] resultKeyPressCode;
        if (keyCodeStr != null) {
            keyCodeInt = Integer.parseInt(keyCodeStr);
            resultKeyPressCode = buildCH9329KeyPressCode((byte) keyCodeInt, false);

        } else if (keyCodeWithShift != null) {
            keyCodeInt = Integer.parseInt(keyCodeWithShift);
            resultKeyPressCode = buildCH9329KeyPressCode((byte) keyCodeInt, true);
        } else {
            throw new RuntimeException("no proper code found");
        }

        return resultKeyPressCode;
    }

    public static byte[] getKeyPressData(char c) {
        return fromCharToHex(c);
    }

    public static byte[] getKeyReleaseData() {
        return new byte[]{0x57, (byte) 0xAB, 0x00, 0x02, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0C};
    }

    public static byte[] addX(int n, byte arr[], byte x) {
        int i;

        // create a new array of size n+1
        byte newarr[] = new byte[n + 1];

        // insert the elements from
        // the old array into the new array
        // insert all elements till n
        // then insert x at n+1
        for (i = 0; i < n; i++)
            newarr[i] = arr[i];

        newarr[n] = x;

        return newarr;
    }


}
