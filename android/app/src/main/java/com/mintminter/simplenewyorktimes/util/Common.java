package com.mintminter.simplenewyorktimes.util;


import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.webkit.URLUtil;

import com.mintminter.simplenewyorktimes.models.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Irene on 9/22/17.
 */

public class Common {
    public static final String SIMPLEDATEFORMAT_PUB_DATE = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String SIMPLEDATEFORMAT_REQUEST_DATE = "yyyyMMdd";

    public static final String EXTRA_URL = "url";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_SEARCHPARAM = "searchparam";

    public static final double LEVER_CONTINUELOADING = 0.5;

    public static boolean isUrl(String s){
       return URLUtil.isHttpUrl(s) || URLUtil.isHttpsUrl(s);
    }

    public static long dataToMilliSeconds(String data){
        SimpleDateFormat sdf = new SimpleDateFormat(SIMPLEDATEFORMAT_REQUEST_DATE);
        long res = 0;
        if(!TextUtils.isEmpty(data)) {
            try {
                res = sdf.parse(data).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public static Time time(long milliSeconds){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(milliSeconds);
        Time time = new Time();
        time.year = c.get(Calendar.YEAR);
        time.month = c.get(Calendar.MONTH);
        time.day = c.get(Calendar.DAY_OF_MONTH);
        return time;
    }

    public static Time time(String data){
        return time(dataToMilliSeconds(data));
    }
}
