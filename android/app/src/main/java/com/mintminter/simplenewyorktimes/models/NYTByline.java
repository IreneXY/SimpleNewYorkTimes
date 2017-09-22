package com.mintminter.simplenewyorktimes.models;

import android.text.TextUtils;

import com.mintminter.simplenewyorktimes.interfaces.Data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Irene on 9/21/17.
 * Example Json
 * {
 "original": "By KEN BELSON"
 }
 */

public class NYTByline implements Data {
    public String original = "";
    @Override
    public void fromJson(JSONObject json) {
        if(json == null){
            return;
        }
        original = json.optString("original","");
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("original", original);
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
