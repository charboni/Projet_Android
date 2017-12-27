package fr.eseo.dis.projet_android;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Nicolas on 27/12/2017.
 */

public class JSONClass {

    public static JSONObject convertingISToJson(InputStream in) throws IOException, JSONException {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);
        JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
        return  jsonObject;
    }

}
