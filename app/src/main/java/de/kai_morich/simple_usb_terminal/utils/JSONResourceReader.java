package de.kai_morich.simple_usb_terminal.utils;

import android.content.res.Resources;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//https://stackoverflow.com/questions/6349759/using-json-file-in-android-app-resources
public class JSONResourceReader {


    // === [ Private Data Members ] ============================================

    // Our JSON, in string form.
    private String jsonString;
//    private static final String LOGTAG = JSONResourceReader.class.getSimpleName();
    private static final String LOGTAG = "TerminalFragment";

    // === [ Public API ] ======================================================

    /**
     * Read from a resources file and create a {@link JSONResourceReader} object that will allow the creation of other
     * objects from this resource.
     *
     * @param resources An application {@link Resources} object.
     * @param id        The id for the resource to load, typically held in the raw/ folder.
     */
    public JSONResourceReader(Resources resources, int id) {
        extracted(resources.openRawResource(id));
    }

    public JSONResourceReader(InputStream resourceReader) {
        extracted(resourceReader);
    }

    private void extracted(InputStream resourceReader) {
        Writer writer = new StringWriter();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceReader, "UTF-8"));
            String line = reader.readLine();
            while (line != null) {
                writer.write(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            Log.e(LOGTAG, "Unhandled exception while using JSONResourceReader", e);
        } finally {
            try {
                resourceReader.close();
            } catch (Exception e) {
                Log.e(LOGTAG, "Unhandled exception while using JSONResourceReader", e);
            }
        }

        jsonString = writer.toString();
    }


    /**
     * Build an object from the specified JSON resource using Gson.
     *
     * @param type The type of the object to build.
     * @return An object of type T, with member fields populated using Gson.
     */
    public <T> T constructUsingGson(Class<T> type) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonString, type);
    }

    public <T> List<T> constructListUsingGson(Type type) {
        ArrayList<T> list = new GsonBuilder().create().fromJson(jsonString, type);

        return list;
    }

}
