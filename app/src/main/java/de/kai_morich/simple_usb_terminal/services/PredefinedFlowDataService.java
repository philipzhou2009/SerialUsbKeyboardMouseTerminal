package de.kai_morich.simple_usb_terminal.services;

import android.content.res.Resources;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import de.kai_morich.simple_usb_terminal.R;
import de.kai_morich.simple_usb_terminal.models.PingFlow;
import de.kai_morich.simple_usb_terminal.utils.JSONResourceReader;

public class PredefinedFlowDataService {

    private final String TAG = "TerminalFragment";
    private final Resources resources;
    private List<PingFlow> predefinedPingFlows = Collections.emptyList();
    private int flowsResId = R.raw.flows;

    public PredefinedFlowDataService(Resources resources) {
        this.resources = resources;
    }

    public void loadPredefinedFlowData() {
        InputStream flowInputStream = this.resources.openRawResource(this.flowsResId);
        JSONResourceReader jsonResourceReader = new JSONResourceReader(flowInputStream);

        Type listType = new TypeToken<List<PingFlow>>(){}.getType();
        List<PingFlow> list = jsonResourceReader.constructListUsingGson(listType);
        Log.i(TAG, "loadPredefinedFlowData, total loaded flows=" + list.size());

        this.predefinedPingFlows = list;
    }

    public Resources getResources() {
        return resources;
    }

    public List<PingFlow> getPredefinedFlows() {
        return predefinedPingFlows;
    }

    public void setFlowsResId(int flowsResId) {
        this.flowsResId = flowsResId;
    }
}
