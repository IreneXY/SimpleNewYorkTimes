package com.mintminter.simplenewyorktimes.models;

import android.text.TextUtils;

import com.mintminter.simplenewyorktimes.interfaces.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Irene on 9/21/17.
 */

public class NYTSearchResponse implements Data {

    public ArrayList<NYTDoc> docs = new ArrayList<>();
    public NYTMeta meta = new NYTMeta();
    @Override
    public void fromJson(JSONObject json) {
        if(json == null){
            return;
        }
        JSONArray docsArray = json.optJSONArray("docs");
        for(int i = 0; i < docsArray.length(); i++){
            JSONObject docJson = docsArray.optJSONObject(i);
            NYTDoc doc = new NYTDoc();
            doc.fromJson(docJson);
            docs.add(doc);
        }
        meta.fromJson(json.optJSONObject("meta"));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("meta", meta.toJson());
            JSONArray docArray = new JSONArray();
            for(NYTDoc doc : docs){
                docArray.put(doc.toJson());
            }
            json.put("docs", docArray);
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
