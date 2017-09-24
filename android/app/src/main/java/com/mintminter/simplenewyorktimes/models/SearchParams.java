package com.mintminter.simplenewyorktimes.models;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Irene on 9/23/17.
 */

@Parcel
public class SearchParams {
    public String q = "";
    public String begin_date = "";
    public String end_date = "";
    public String sort = "newest";
    public ArrayList<String> news_desk = new ArrayList<>();
    public int page = 0;

    public String genFq(){
        if(news_desk == null || news_desk.size() == 0){
            return "";
        }else{
            StringBuilder content = new StringBuilder();
            for(int i = 0; i < news_desk.size()-1; i++){
                content.append("\"");
                content.append(news_desk.get(i));
                content.append("\" ");
            }
            content.append("\"");
            content.append(news_desk.get(news_desk.size()-1));
            content.append("\"");
            return String.format("news_desk:(%1$s)", content.toString());
        }
    }
}
