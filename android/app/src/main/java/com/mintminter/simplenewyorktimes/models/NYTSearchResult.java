package com.mintminter.simplenewyorktimes.models;

import android.text.TextUtils;

import com.mintminter.simplenewyorktimes.interfaces.Data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Irene on 9/21/17.
 */

public class NYTSearchResult implements Data {
    public String status = "";
    public boolean isSatatusOk = false;
    public String copyright = "";
    public NYTSearchResponse response = new NYTSearchResponse();

    @Override
    public void fromJson(JSONObject json) {
        status = json.optString("status", "");
        isSatatusOk = "OK".equals(status);
        copyright = json.optString("copyright", "");
        response.fromJson(json.optJSONObject("response"));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("status", status);
            json.put("copyright", copyright);
            json.put("response", response.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public void fromJsonString(String jsonString) {
        if(TextUtils.isEmpty(jsonString)){
            return;
        }
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
