package de.kai_morich.simple_usb_terminal;

import static de.kai_morich.simple_usb_terminal.CH9329KeyCodeMap.ch9329NormalKeyCodeMap;

public class CH9329Util {


    public static byte[] fromStringToHex(String str) {
        char c = str.charAt(0);
        return new byte[0];
    }

    public static byte[] fromCharToHex(char c) {
        final String tag = "fromCharToHex";
        LogUtil.i(tag, ch9329NormalKeyCodeMap.toString());

        byte[] frameHead = {0x57, (byte) 0xAB};
        byte dataFrameHead01 = 0x57;
        byte dataFrameHead02 = (byte) 0xAB;
        byte addressCode = 0x00;
        byte commandCode = 0x02;
        byte dataLength = 0x08;

        byte dataFirstCode = 0x00;
        byte dataSecondCode = 0x00;
        byte dataThirdCode = ch9329NormalKeyCodeMap.get(c);

        byte[] codeswithoutSum = {dataFrameHead01, dataFrameHead02, addressCode, commandCode,
                dataLength, dataFirstCode, dataSecondCode, dataThirdCode, 0x0, 0x0, 0x0, 0x0};

        int sum = 0;
        for (int i = 0; i < codeswithoutSum.length; i++) {
            sum += codeswithoutSum[i];
        }

        LogUtil.i(tag, "sum=" + sum);
        LogUtil.i(tag, "sum as byte =" + (byte) sum);

//        byte sum = dataFrameHead01 + dataFrameHead02 + addressCode + commandCode +


        return frameHead;

    }
}
