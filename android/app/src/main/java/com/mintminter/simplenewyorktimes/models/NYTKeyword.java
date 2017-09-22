package com.mintminter.simplenewyorktimes.models;

import com.mintminter.simplenewyorktimes.interfaces.Data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Irene on 9/21/17.
 * Example Json:
 * {
 "isMajor": "N",
 "rank": 1,
 "name": "subject",
 "value": "Movies"
 }
 */

public class NYTKeyword implements Data{
    public boolean isMajor = false;
    public int rank = 0;
    public String name = "";
    public String value = "";

    @Override
    public void fromJson(JSONObject json) {
        isMajor = json.optString("isMajor", "N").equals("Y");
        rank = json.optInt("rank");
        name = json.optString("name", "");
        value = json.optString("value", "");
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("isMajor", isMajor?"Y":"N");
            json.put("rank", rank);
            json.put("name", name);
            json.put("value", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public void fromJsonString(String jsonString) {
        try {
            JSONObject json = new JSONObject(jsonString);
            fromJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toJsonString() {
        return toJson().toString();
    }
}
