package com.mintminter.simplenewyorktimes.models;

import android.text.TextUtils;

import com.mintminter.simplenewyorktimes.interfaces.Data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Irene on 9/21/17.
 * Example Json:
 * {
 "type": "image",
 "subtype": "xlarge",
 "url": "images/2017/09/24/fashion/weddings/24SKINCARE-BRIDE-COMBO1/24SKINCARE-BRIDE-COMBO1-articleLarge.jpg",
 "height": 338,
 "width": 600,
 "rank": 0,
 "legacy": {
 "xlargewidth": 600,
 "xlarge": "images/2017/09/24/fashion/weddings/24SKINCARE-BRIDE-COMBO1/24SKINCARE-BRIDE-COMBO1-articleLarge.jpg",
 "xlargeheight": 338
 }
 }
 */

public class NYTMultimedia implements Data {
    public String type = "";
    public String subtype = "";
    public String url = "";
    public int height = 0;
    public int width = 0;
    public int rank = 0;
    public NYTLegacy legacy;

    @Override
    public void fromJson(JSONObject json) {
        if(json == null){
            return;
        }
        type = json.optString("type", "");
        subtype = json.optString("subtype", "");
        url = json.optString("url", "");
        height = json.optInt("height", 0);
        width = json.optInt("width", 0);
        rank = json.optInt("rank", 0);
        legacy = new NYTLegacy(subtype);
        legacy.fromJson(json.optJSONObject("legacy"));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("type", type);
            json.put("subtype", subtype);
            json.put("url",url);
            json.put("height", 0);
            json.put("width", 0);
            json.put("rank", 0);
            json.put("legacy", legacy.toJson());
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
