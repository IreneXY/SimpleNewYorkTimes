package com.mintminter.simplenewyorktimes.api;

import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mintminter.simplenewyorktimes.interfaces.ApiCallback;
import com.mintminter.simplenewyorktimes.models.NYTSearchResult;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Irene on 9/20/17.
 */

public class ApiManager {
    public void getSearchResult(String q, String beginDate, String endDate, String sort, String newsDesk, int page, final ApiCallback apiCallback){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("api-key", Bootstrap.NYTAPIKEY);
        if(!TextUtils.isEmpty(q)) {
            params.put("q", q);
        }
        if(!TextUtils.isEmpty(beginDate)) {
            params.put("begin_date", beginDate);
        }
        if(!TextUtils.isEmpty(endDate)) {
            params.put("end_date", endDate);
        }
        if(!TextUtils.isEmpty(sort)) {
            params.put("sort", sort);
        }
        params.put("page", page);
        client.get(Bootstrap.getSearchApi(), params, new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String res) {
                        // called when response HTTP status is "200 OK"
                        Log.i("@getSearchResult res ", res);
                        NYTSearchResult searchResult = new NYTSearchResult();
                        searchResult.fromJsonString(res);
                        apiCallback.setSearchResult(searchResult);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        apiCallback.setFailure(statusCode, res);
                    }
                }
        );
    }
    
}
