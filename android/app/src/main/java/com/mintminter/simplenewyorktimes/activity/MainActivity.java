package com.mintminter.simplenewyorktimes.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.mintminter.simplenewyorktimes.R;
import com.mintminter.simplenewyorktimes.adapter.DocAdapter;
import com.mintminter.simplenewyorktimes.api.ApiManager;
import com.mintminter.simplenewyorktimes.interfaces.ApiCallback;
import com.mintminter.simplenewyorktimes.interfaces.ContinueCallBack;
import com.mintminter.simplenewyorktimes.models.NYTSearchResult;
import com.mintminter.simplenewyorktimes.models.SearchParams;

public class MainActivity extends AppCompatActivity implements ApiCallback, ContinueCallBack{

    private RecyclerView mDocList;
    private SearchParams mSearchParams = new SearchParams();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDocList = (RecyclerView) findViewById(R.id.main_doclist);
        mDocList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mDocList.setAdapter(new DocAdapter(this, this));


        new ApiManager().getSearchResult(mSearchParams, this);
    }

    @Override
    public void setSearchResult(NYTSearchResult searchResult) {
        mSearchParams.page++;
        ((DocAdapter) mDocList.getAdapter()).append(searchResult.response.docs);

    }

    @Override
    public void setFailure(int statusCode, String res) {

    }

    @Override
    public void continueLoading() {
        new ApiManager().getSearchResult(mSearchParams, this);
    }
}
