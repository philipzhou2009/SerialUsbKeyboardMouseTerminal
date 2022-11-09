package de.kai_morich.simple_usb_terminal;

import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class MiscUtil {

    //    https://stackoverflow.com/questions/5368704/appending-a-byte-to-the-end-of-another-byte
    public static byte[] concatenateByteArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);

    public static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }

    public static String bytesToHexWithSpace(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 3];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 3] = HEX_ARRAY[v >>> 4];
            hexChars[j * 3 + 1] = HEX_ARRAY[v & 0x0F];
            hexChars[j * 3 + 2] = " ".getBytes(StandardCharsets.UTF_8)[0];
        }
        String s = new String(hexChars, StandardCharsets.UTF_8);

        return s;
    }

    public static void LogByteArray(String tag, byte[] bytes) {
        Log.i(tag, Arrays.toString(bytes));
        Log.i(tag, bytesToHexWithSpace(bytes));
    }

    public static void LogByteArray(String tag, String prefix, byte[] bytes) {
        Log.i(tag, prefix + bytesToHexWithSpace(bytes));
    }

    public static void LogByteArray(String tag, String prefix, List<Byte> bytes) {
        int listSize = bytes.size();
        byte[] targetBytes = new byte[listSize];
        for (int i = 0; i < listSize; i++) {
            targetBytes[i] = bytes.get(i);
        }

        Log.i(tag, prefix + bytesToHexWithSpace(targetBytes));
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
