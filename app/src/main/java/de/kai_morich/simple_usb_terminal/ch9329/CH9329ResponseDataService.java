package de.kai_morich.simple_usb_terminal.ch9329;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.kai_morich.simple_usb_terminal.enums.CH9329ResponseStatus;

public class CH9329ResponseDataService {

    public static final String TAG = "TerminalFragment";
    private final static int DATA_FILLING = 888;

    private final int[] tmpStorageForReceivingData = new int[7];
    private final Date[] responseStartStopTime = new Date[2];
    private long responseStartTime;
    private final List<byte[]> responseDataStorage = new ArrayList<>();

    public CH9329ResponseDataService() {
        resetResponseData();
    }

    public void collectReceivingData(byte[] receivingData) {
        responseDataStorage.add(receivingData);
    }

    public CH9329ResponseStatus getResponseResult() {
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        long timeDiff = timeInMillis - this.responseStartTime;
        Log.i(TAG, "currentTime: " + timeInMillis + ", startTime: " + this.responseStartTime + ", timeDiff: " + timeDiff);
//        if (timeDiff < 500) {
//            return CH9329ResponseStatus.PENDING;
//        }

        List<Byte> totalList = new ArrayList<>();
        for (byte[] entry : this.responseDataStorage) {
            for (byte bCode : entry) {
                totalList.add(bCode);
            }
        }
        Log.i(TAG, "total response data=" + totalList);


        List<Byte> successResponseCodes = Arrays.asList((byte) 0x57, (byte) 0xAB, (byte) 0x00, (byte) 0x82, (byte) 0x01, (byte) 0x00, (byte) 0x85);

        if (totalList.size() < 7) {
            return CH9329ResponseStatus.PENDING;
        }

        for (int i = 0; i < totalList.size(); i = i + 7) {
            List<Byte> subList = totalList.subList(i, 7);
            if (!subList.equals(successResponseCodes)) {
                return timeDiff > 500 ? CH9329ResponseStatus.ERROR : CH9329ResponseStatus.PENDING;
            }
        }

        return CH9329ResponseStatus.SUCCESS;
    }

    public boolean collectReceivingDataIntoTmpStorage(byte[] receivingData) {
        try {
            if (receivingData[0] == 0x57) {
                resetResponseData();
            }

            int index = 0;
            for (int i = 0; i < tmpStorageForReceivingData.length; i++) {
                if (tmpStorageForReceivingData[i] == DATA_FILLING) {
                    index = i;
                    break;
                }
            }

            for (int i = 0; i < receivingData.length; i++) {
                if (index + i > tmpStorageForReceivingData.length) {
                    throw new RuntimeException("please check receiving ch9329 data which cannot store in target storage");
                }
                tmpStorageForReceivingData[index + i] = receivingData[i];
            }

            return true;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

    public void resetResponseData() {
        Date currentTime = Calendar.getInstance().getTime();
        responseStartStopTime[0] = currentTime;
        responseStartStopTime[1] = null;

        this.setResponseStartTime(Calendar.getInstance().getTimeInMillis());
        responseDataStorage.clear();
    }

    public int[] getReceivingData() {
        return tmpStorageForReceivingData;
    }

    public void setResponseStartTime(long responseStartTime) {
        this.responseStartTime = responseStartTime;
    }
}
