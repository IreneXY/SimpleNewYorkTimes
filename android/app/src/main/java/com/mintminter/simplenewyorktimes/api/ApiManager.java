package com.mintminter.simplenewyorktimes.api;

import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mintminter.simplenewyorktimes.interfaces.ApiCallback;
import com.mintminter.simplenewyorktimes.models.NYTSearchResult;
import com.mintminter.simplenewyorktimes.models.SearchParams;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Irene on 9/20/17.
 */

public class ApiManager {
    public void getSearchResult(SearchParams searchParams, final ApiCallback apiCallback){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("api-key", Bootstrap.NYTAPIKEY);
        if(!TextUtils.isEmpty(searchParams.q)) {
            params.put("q", searchParams.q);
        }
        if(!TextUtils.isEmpty(searchParams.begin_date)) {
            params.put("begin_date", searchParams.begin_date);
        }
        if(!TextUtils.isEmpty(searchParams.end_date)) {
            params.put("end_date", searchParams.end_date);
        }
        if(!TextUtils.isEmpty(searchParams.sort)) {
            params.put("sort", searchParams.sort);
        }
        params.put("page", searchParams.page);
        String fq = searchParams.genFq();
        if(!TextUtils.isEmpty(fq)){
            params.put("fq", fq);
        }
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
