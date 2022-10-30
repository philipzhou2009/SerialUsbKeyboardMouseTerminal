package de.kai_morich.simple_usb_terminal;

import static de.kai_morich.simple_usb_terminal.CH9329KeyCodeMap.ch9329NormalKeyCodeMap;
import static de.kai_morich.simple_usb_terminal.MiscUtil.LogByteArray;
import static de.kai_morich.simple_usb_terminal.MiscUtil.concatenateByteArrays;

public class CH9329Util {


    public static byte[] fromStringToHex(String str) {
        char c = str.charAt(0);
        return new byte[0];
    }

    public static byte[] addNewLineToCH9329Code(byte[] originalBytes) {
        byte[] result = concatenateByteArrays(originalBytes, new byte[]{0x0D, 0x0A});

        return result;
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
