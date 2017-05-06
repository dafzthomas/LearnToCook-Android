package com.apps.dafz.learntocook.helpers;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class JsonFile {
    private String json = null;
    public JsonFile(Context context, String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            this.json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public JSONArray toArray() {
        try {
            return new JSONArray(this.json);
        } catch(JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public JSONObject toObject() {
        try {
            return new JSONObject(this.json);
        } catch(JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
