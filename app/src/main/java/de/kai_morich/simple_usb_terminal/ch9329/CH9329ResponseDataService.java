package de.kai_morich.simple_usb_terminal.ch9329;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.kai_morich.simple_usb_terminal.enums.CH9329ResponseStatus;

public class CH9329ResponseDataService {

    public static final String TAG = "TerminalFragment";
    public static List<Byte> successResponseCodes = Arrays.asList((byte) 0x57, (byte) 0xAB, (byte) 0x00, (byte) 0x82, (byte) 0x01, (byte) 0x00, (byte) 0x85);

    private final List<byte[]> responseDataStorage = new ArrayList<>();
    private CH9329ResponseStatus latestResponseStatus;

    public CH9329ResponseDataService() {
        resetResponseData();
    }

    public void collectReceivingData(byte[] receivingData) {
        responseDataStorage.add(receivingData);
    }

    public CH9329ResponseStatus getLatestResponseStatus() {
        CH9329ResponseStatus responseStatus = getCurrentResponseStatus();
        if (responseStatus == CH9329ResponseStatus.PENDING || responseStatus == latestResponseStatus) {
            return CH9329ResponseStatus.PENDING;
        }

        latestResponseStatus = responseStatus;
        return latestResponseStatus;
    }

    public CH9329ResponseStatus getCurrentResponseStatus() {
        List<Byte> totalResponseData = new ArrayList<>();
        for (byte[] entry : this.responseDataStorage) {
            for (byte bCode : entry) {
                totalResponseData.add(bCode);
            }
        }
//        MiscUtil.LogByteList(TAG, "total response data=", totalResponseData);

        int totalSize = totalResponseData.size();
        if (totalSize % 7 != 0) {
            return CH9329ResponseStatus.PENDING;
        }

        for (int i = 0; i < totalSize; i = i + 7) {
            List<Byte> subList = totalResponseData.subList(i, i + Math.min(totalSize - i, 7));
//            MiscUtil.LogByteList(TAG, "sub response list=", subList);
            if (!subList.equals(successResponseCodes)) {
                return CH9329ResponseStatus.ERROR;
            }
        }

        return CH9329ResponseStatus.SUCCESS;
    }

    public void resetResponseData() {
        responseDataStorage.clear();
        this.latestResponseStatus = null;
    }
}
