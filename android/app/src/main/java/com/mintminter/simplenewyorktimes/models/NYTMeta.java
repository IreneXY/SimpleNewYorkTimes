package com.mintminter.simplenewyorktimes.models;

import android.text.TextUtils;

import com.mintminter.simplenewyorktimes.interfaces.Data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Irene on 9/21/17.
 * Example Json:
 * {
 "hits": 183889,
 "offset": 2000,
 "time": 45
 }
 */

public class NYTMeta implements Data {
    public static final int COUNT_PER_PAGE = 10;
    public int hits = 0;
    public int offset = 0;
    public int time = 0;
    public int page = 0;

    @Override
    public void fromJson(JSONObject json) {
        if(json == null){
            return;
        }
        hits = json.optInt("hits", 0);
        offset = json.optInt("offset", 0);
        time = json.optInt("time", 0);
        page = offset / COUNT_PER_PAGE;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("hits", hits);
            json.put("offset", offset);
            json.put("time", time);
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

        }
    }

    @Override
    public String toJsonString() {
        return toJson().toString();
    }
}
