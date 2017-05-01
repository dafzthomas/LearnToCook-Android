package com.apps.dafz.learntocook.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Recipe {

    public String Image;
    public String Title;
    public String Text;
    public String Serves;
    public String PrepTime;
    public String CookingTime;
    public String Ingredients;
    public String Method;

    public Recipe (JSONObject obj) {
        try {
            this.Image = obj.getString("Image");
            this.Title = obj.getString("Title");
            this.Text = obj.getString("Text");
            this.Serves = obj.getString("Serves");
            this.PrepTime = obj.getString("PrepTime");
            this.CookingTime = obj.getString("CookingTime");
            this.Ingredients = obj.getString("Ingredients");
            this.Method = obj.getString("Method");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
