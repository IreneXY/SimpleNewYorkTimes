package com.mintminter.simplenewyorktimes.interfaces;

import org.json.JSONObject;

/**
 * Created by Irene on 9/21/17.
 */

public interface Data {

    void fromJson(JSONObject json);
    JSONObject toJson();
    void fromJsonString(String jsonString);
    String toJsonString();
}
