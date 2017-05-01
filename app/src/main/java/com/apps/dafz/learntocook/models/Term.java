package com.apps.dafz.learntocook.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Term {
    public String Term;
    public String Explanation;

    public Term(JSONObject obj) {
        try {
            this.Term = obj.getString("Term");
            this.Explanation = obj.getString("Explanation");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
