package com.mintminter.simplenewyorktimes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mintminter.simplenewyorktimes.api.ApiManager;
import com.mintminter.simplenewyorktimes.interfaces.ApiCallback;
import com.mintminter.simplenewyorktimes.models.NYTSearchResult;

public class MainActivity extends AppCompatActivity implements ApiCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new ApiManager().getSearchResult("Google", null, null, null, null, 0, this);
    }

    @Override
    public void setSearchResult(NYTSearchResult searchResult) {
        Log.i("Test", searchResult.toJsonString());
    }

    @Override
    public void setFailure(int statusCode, String res) {

    }
}
