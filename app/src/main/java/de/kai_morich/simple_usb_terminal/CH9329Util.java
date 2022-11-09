package de.kai_morich.simple_usb_terminal;

import static de.kai_morich.simple_usb_terminal.CH9329KeyCodeMap.ch9329NormalKeyCodeMap;
import static de.kai_morich.simple_usb_terminal.MiscUtil.LogByteArray;
import static de.kai_morich.simple_usb_terminal.MiscUtil.addX;
import static de.kai_morich.simple_usb_terminal.MiscUtil.concatenateByteArrays;
import static de.kai_morich.simple_usb_terminal.TextUtil.fromHexString;
import static de.kai_morich.simple_usb_terminal.ch9329.CH9329KeyCodeMapData.getCH9329KeyCodeData;

import android.util.Log;

import java.util.Arrays;
import java.util.Map;

import de.kai_morich.simple_usb_terminal.models.CH9329KeyCodeData;

public class CH9329Util {

    public static final String TAG = "TerminalFragment";

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

    public static byte[] getKeyPressData(char c) {
        return convertCharIntoCH9329KeyPressCode(c, getCH9329KeyCodeData());
    }

    public static byte[] getKeyReleaseData() {
        return new byte[]{0x57, (byte) 0xAB, 0x00, 0x02, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0C};
    }

    public static byte[] convertCharIntoCH9329KeyPressCode(char c, CH9329KeyCodeData ch9329KeyCodeData) {
        Log.i(TAG, "convertCharIntoCH9329KeyPressCode, c=" + c + "|||");

        Map<String, String> ch9329NormalKeyCodeMap = ch9329KeyCodeData.getCh9329NormalKeyCodeMap();
        Map<String, String> ch9329ShiftKeyCodeMap = ch9329KeyCodeData.getCh9329ShiftKeyCodeMap();

        String keyCodeStr = ch9329NormalKeyCodeMap.get(String.valueOf(c));
        String keyCodeStrWithShift = ch9329ShiftKeyCodeMap.get(String.valueOf(c));

        byte[] resultKeyPressCode;
        if (keyCodeStr != null) {
            byte[] bytes = fromHexString(keyCodeStr);
            resultKeyPressCode = buildCH9329KeyPressCode((byte) bytes[0], false);
        } else if (keyCodeStrWithShift != null) {
            byte[] bytes = fromHexString(keyCodeStrWithShift);
            LogByteArray(TAG, "this key needs shift: ", bytes);
            resultKeyPressCode = buildCH9329KeyPressCode((byte) bytes[0], true);
        } else {
            throw new RuntimeException("no proper code found for -->" + c + "<--");
        }

        return resultKeyPressCode;
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
        Log.i("", "sum=" + sum);

        return addX(13, codesWithoutSum, sum);
    }

    @Deprecated
    public static byte[] fromCharToHex(char c) {
        final String tag = "fromCharToHex";
        Log.i(tag, ch9329NormalKeyCodeMap.toString());

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
//        Log.i(tag, "sum=" + sum);

        return addX(13, codesWithoutSum, sum);
    }
}
