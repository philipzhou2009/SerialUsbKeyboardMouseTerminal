package de.kai_morich.simple_usb_terminal.services;

import android.content.Context;
import android.content.res.Resources;

import java.io.InputStream;
import java.util.List;

import de.kai_morich.simple_usb_terminal.R;
import de.kai_morich.simple_usb_terminal.models.Flow;
import de.kai_morich.simple_usb_terminal.utils.JSONResourceReader;

public class PredefinedFlowDataService {

    private Context context;
    private Resources resources;


    public PredefinedFlowDataService(Resources resources) {
        this.resources = resources;
        InputStream flowInputStream = resources.openRawResource(R.raw.flows);
        loadPredefinedFlowData(flowInputStream);
    }

    public void loadPredefinedFlowData(InputStream flowInputStream) {

        JSONResourceReader jsonResourceReader = new JSONResourceReader(flowInputStream);
        List<Flow> list = jsonResourceReader.constructUsingGson(List.class);



    }
}
