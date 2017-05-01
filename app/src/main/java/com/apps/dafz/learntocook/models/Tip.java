package com.apps.dafz.learntocook.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Tip {

    public String Title;
    public String Text;
    public String Image;

    public Tip (JSONObject obj) {
        try {
            this.Title = obj.getString("Title");
            this.Text = obj.getString("Text");
            this.Image = obj.getString("Image");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
