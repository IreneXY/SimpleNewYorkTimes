package com.mintminter.simplenewyorktimes.models;

import android.text.TextUtils;

import com.mintminter.simplenewyorktimes.interfaces.Data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Irene on 9/21/17.
 * Example json:
 * {
 "main": "MoneyGram Deal May Test Trump’s View on Chinese Investment",
 "kicker": "Deal Professor",
 "print_headline": "MoneyGram Deal May Test Trump’s Views About Chinese Investment"
 }
 */

public class NYTHeadline implements Data {
    public String main = "";
    public String print_headline = "";
    public String kicker = "";

    @Override
    public void fromJson(JSONObject json) {
        if(json == null){
            return;
        }
        main = json.optString("main", "");
        print_headline = json.optString("print_headline", "");
        kicker = json.optString("kicker", "");
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("main", main);
            json.put("print_headline", print_headline);
            json.put("kicker", kicker);
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
