package com.mintminter.simplenewyorktimes.api;

import com.mintminter.simplenewyorktimes.BuildConfig;

/**
 * Created by Irene on 9/20/17.
 */

public class Bootstrap {
    public static final String NYTAPIKEY = BuildConfig.NYTAPIKEY;

    public static final String BASEURL_NYT = "https://api.nytimes.com/svc/search/v2";

    public static final String ENDPOINT_SEARCH = "/articlesearch.json";

    public static final String getApi(String endpoint){
        return BASEURL_NYT + endpoint;
    }

    public static final String getSearchApi(){
        return getApi(ENDPOINT_SEARCH);
    }
}
