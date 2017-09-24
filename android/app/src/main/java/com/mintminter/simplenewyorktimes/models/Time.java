package com.mintminter.simplenewyorktimes.models;

import java.util.Calendar;

/**
 * Created by Irene on 9/24/17.
 */

public class Time {
    public int year;
    public int month;
    public int day;

    public Time(){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(0);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
    }

    public Time(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(year);
        sb.append(month);
        sb.append(day);
        return sb.toString();
    }
}
