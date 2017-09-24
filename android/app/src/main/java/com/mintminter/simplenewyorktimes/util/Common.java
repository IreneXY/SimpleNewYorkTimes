package com.mintminter.simplenewyorktimes.util;


import android.util.Log;
import android.util.Patterns;
import android.webkit.URLUtil;

/**
 * Created by Irene on 9/22/17.
 */

public class Common {
    public static final String SIMPLEDATEFORMAT_PUB_DATE = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String SIMPLEDATEFORMAT_REQUEST_DATE = "Y.YYYMMDD";

    public static final String EXTRA_URL = "url";
    public static final String EXTRA_TITLE = "title";

    public static final double LEVER_CONTINUELOADING = 0.5;

    public static boolean isUrl(String s){
       return URLUtil.isHttpUrl(s) || URLUtil.isHttpsUrl(s);
    }
}
