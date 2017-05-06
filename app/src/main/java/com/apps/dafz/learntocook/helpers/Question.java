package com.apps.dafz.learntocook.helpers;

import org.json.JSONException;
import org.json.JSONObject;

public class Question {

    public Question (JSONObject obj) {
        try {
            this.Title= obj.getString("Title");
            this.Q= obj.getString("Q");
            this.Ans1= obj.getString("Ans1");
            this.Ans2= obj.getString("Ans2");
            this.Ans3= obj.getString("Ans3");
            this.Correct= obj.getString("Correct");
            this.Reason= obj.getString("Reason");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private String Title;

    public String getTitle() { return this.Title; }

    public void setTitle(String Title) { this.Title = Title; }

    private String Q;

    public String getQ() { return this.Q; }

    public void setQ(String Q) { this.Q = Q; }

    private String Ans1;

    public String getAns1() { return this.Ans1; }

    public void setAns1(String Ans1) { this.Ans1 = Ans1; }

    private String Ans2;

    public String getAns2() { return this.Ans2; }

    public void setAns2(String Ans2) { this.Ans2 = Ans2; }

    private String Ans3;

    public String getAns3() { return this.Ans3; }

    public void setAns3(String Ans3) { this.Ans3 = Ans3; }

    private String Correct;

    public String getCorrect() { return this.Correct; }

    public void setCorrect(String Correct) { this.Correct = Correct; }

    private String Reason;

    public String getReason() { return this.Reason; }

    public void setReason(String Reason) { this.Reason = Reason; }
}
