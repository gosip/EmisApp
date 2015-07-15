package ge.edu.freeuni.emis.emisapp.loaders.util;

import android.content.Context;
import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonUtils {
    public static JSONObject getJSONObject(String jsonFileName, Context context) throws JSONException {
        jsonFileName = jsonFileName + ".json";
        JSONObject ret = null;
        StringBuilder builder = new StringBuilder();
        try {
            InputStream stream = context.getAssets().open(jsonFileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            String line = reader.readLine();
            while (line != null) {
                builder.append(line);
                line = reader.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        ret = new JSONObject(builder.toString());
        return ret;
    }
}
