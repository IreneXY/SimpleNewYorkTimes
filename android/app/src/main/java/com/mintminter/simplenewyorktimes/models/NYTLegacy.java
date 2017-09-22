package com.mintminter.simplenewyorktimes.models;

import android.text.TextUtils;

import com.mintminter.simplenewyorktimes.interfaces.Data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Irene on 9/21/17.
 * Example Json:
 * {
 "xlargewidth": 600,
 "xlarge": "images/2017/09/24/fashion/weddings/24SKINCARE-BRIDE-COMBO1/24SKINCARE-BRIDE-COMBO1-articleLarge.jpg",
 "xlargeheight": 338
 }
 */

public class NYTLegacy implements Data {
    public String type = "";
    public int width = 0;
    public String value = "";
    public int height = 0;

    private NYTLegacy(){

    }

    public NYTLegacy(String subType){
        type = subType;
    }

    @Override
    public void fromJson(JSONObject json) {
        if(json == null){
            return;
        }
        width = json.optInt(type+"width", 0);
        value = json.optString(type, "");
        height = json.optInt(type+"height", 0);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put(type+"width", width);
            json.put(type, value);
            json.put(type+"height", height);
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
