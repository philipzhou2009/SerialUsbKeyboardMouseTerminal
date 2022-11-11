package de.kai_morich.simple_usb_terminal.services;

import android.content.res.Resources;

import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import de.kai_morich.simple_usb_terminal.R;
import de.kai_morich.simple_usb_terminal.models.Flow;
import de.kai_morich.simple_usb_terminal.utils.JSONResourceReader;

public class PredefinedFlowDataService {

    private Resources resources;
    private List<Flow> predefinedFlows = Collections.emptyList();
    private int flowsResId = R.raw.flows;

    public PredefinedFlowDataService(Resources resources) {
        this.resources = resources;
    }

    public void loadPredefinedFlowData() {
        InputStream flowInputStream = this.resources.openRawResource(this.flowsResId);
        JSONResourceReader jsonResourceReader = new JSONResourceReader(flowInputStream);

        Type listType = new TypeToken<List<Flow>>(){}.getType();
        List<Flow> list = jsonResourceReader.constructListUsingGson(listType);

        this.predefinedFlows = list;
    }

    public Resources getResources() {
        return resources;
    }

    public List<Flow> getPredefinedFlows() {
        return predefinedFlows;
    }

    public void setFlowsResId(int flowsResId) {
        this.flowsResId = flowsResId;
    }
}
