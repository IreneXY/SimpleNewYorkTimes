package com.mintminter.simplenewyorktimes.models;

import android.text.TextUtils;

import com.mintminter.simplenewyorktimes.interfaces.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Irene on 9/21/17.
 * Json Example: {
 "web_url": "https://www.nytimes.com/aponline/2017/09/21/us/politics/ap-us-trump-leaks-training.html",
 "snippet": "Employees at the Environmental Protection Agency are attending mandatory training sessions this week to reinforce federal laws and rules against leaking government information.",
 "blog": {},
 "source": "AP",
 "multimedia": [],
 "headline": {
 "main": "Federal Employees Are Ordered to Attend Anti-Leaking Classes",
 "print_headline": "Federal Employees Are Ordered to Attend Anti-Leaking Classes"
 },
 "keywords": [],
 "pub_date": "2017-09-21T14:55:10+0000",
 "document_type": "article",
 "new_desk": "None",
 "section_name": "Politics",
 "byline": {
 "original": "By THE ASSOCIATED PRESS"
 },
 "type_of_material": "News",
 "_id": "59c3d2d67c459f246b6299a3",
 "word_count": 127,
 "score": 1,
 "uri": "nyt://article/ebd65ab3-e849-58a5-adcb-1c83055afe9e"
 }
 */

public class NYTDoc implements Data {
    public String web_url = "";
    public String snippet = "";
    public String source = "";
    public ArrayList<NYTMultimedia> multimedias = new ArrayList<>();
    public NYTHeadline headline = new NYTHeadline();
    public ArrayList<NYTKeyword> keywords = new ArrayList<>();
    public String pub_date = "";
    public long pub_date_milliseconds = 0;
    public String document_type = "";
    public String new_desk = "";
    public String section_name = "";
    public NYTByline byline = new NYTByline();
    public String type_of_material = "";
    public String _id = "";
    public int word_count = 0;
    public double score = 0;
    public String uri = "";

    @Override
    public void fromJson(JSONObject json) {
        if(json == null){
            return;
        }
        web_url = json.optString("web_url", "");
        snippet = json.optString("snippet", "");
        source = json.optString("source", "");
        JSONArray multimediaArray = json.optJSONArray("multimedia");
        if(multimediaArray != null){
            for(int i = 0; i < multimediaArray.length(); i++){
                JSONObject j = multimediaArray.optJSONObject(i);
                NYTMultimedia multimedia = new NYTMultimedia();
                multimedia.fromJson(j);
                multimedias.add(multimedia);
            }
        }
        JSONObject headlineJson = json.optJSONObject("headline");
        headline.fromJson(headlineJson);
        JSONArray keywordArray = json.optJSONArray("keywords");
        if(keywordArray != null){
            for(int i = 0; i < keywordArray.length(); i++){
                JSONObject j = keywordArray.optJSONObject(i);
                NYTKeyword keyword = new NYTKeyword();
                keyword.fromJson(j);
                keywords.add(keyword);
            }
        }
        pub_date = json.optString("pub_date", "");
        if(!TextUtils.isEmpty(pub_date)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            try {
                pub_date_milliseconds = sdf.parse(pub_date).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        document_type = json.optString("document_type", "");
        new_desk = json.optString("new_desk", "");
        section_name = json.optString("section_name", "");
        byline.fromJson(json.optJSONObject("byline"));
        type_of_material = json.optString("type_of_material", "");
        _id = json.optString("_id", "");
        word_count = json.optInt("word_count", 0);
        score = json.optDouble("score", 0);
        uri = json.optString("uri", "");
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("web_url",web_url);
            json.put("snippet",snippet);
            json.put("source",source);
            JSONArray multimediaArray = new JSONArray();
            for(NYTMultimedia m : multimedias){
                multimediaArray.put(m.toJson());
            }
            json.put("multimedia", multimediaArray);
            json.put("headline", headline.toJson());
            JSONArray keywordArray = new JSONArray();
            for(NYTKeyword k : keywords){
                keywordArray.put(k.toJson());
            }
            json.put("keywords", keywordArray);
            json.put("pub_date", pub_date);
            json.put("document_type", document_type);
            json.put("new_desk", new_desk);
            json.put("section_name", section_name);
            json.put("byline", byline.toJson());
            json.put("type_of_material", type_of_material);
            json.put("_id", _id);
            json.put("word_count", word_count);
            json.put("score", score);
            json.put("uri", uri);
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
