package com.mintminter.simplenewyorktimes.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mintminter.simplenewyorktimes.R;
import com.mintminter.simplenewyorktimes.adapter.DocAdapter;
import com.mintminter.simplenewyorktimes.api.ApiManager;
import com.mintminter.simplenewyorktimes.fragment.FilterDialogFragment;
import com.mintminter.simplenewyorktimes.interfaces.ApiCallback;
import com.mintminter.simplenewyorktimes.interfaces.ContinueCallBack;
import com.mintminter.simplenewyorktimes.interfaces.SearchParamsCallback;
import com.mintminter.simplenewyorktimes.models.NYTSearchResult;
import com.mintminter.simplenewyorktimes.models.SearchParams;
import com.mintminter.simplenewyorktimes.util.Common;

public class MainActivity extends AppCompatActivity implements ApiCallback, ContinueCallBack, SearchParamsCallback{

    private RecyclerView mDocList;
    private SearchView mSearchView;
    private RelativeLayout mProgressArea;
    private SearchParams mSearchParams = new SearchParams();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle(Common.getString(this, R.string.title));
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDocList.scrollToPosition(0);
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        mDocList = (RecyclerView) findViewById(R.id.main_doclist);
        mDocList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mDocList.setAdapter(new DocAdapter(this, this));

        mProgressArea = (RelativeLayout) findViewById(R.id.main_progressbar);

        query(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_search:
                mSearchView = (SearchView) item.getActionView();
                mSearchView.setSubmitButtonEnabled(true);
                mSearchView.setOnSearchClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSearchView.setQuery(mSearchParams.q, false);
                    }
                });
                mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        mSearchParams.q = query;
                        query(false);
                        item.collapseActionView();
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String s) {
                        return false;
                    }
                });
                break;
            case R.id.action_filter:
                FragmentManager fm = getSupportFragmentManager();
                FilterDialogFragment filterDialogFragment = FilterDialogFragment.newInstance(mSearchParams);
                filterDialogFragment.setSearchParamsCallback(MainActivity.this);
                filterDialogFragment.show(fm, FilterDialogFragment.TAG);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void query(boolean bAppendResult){
        if(!bAppendResult){
            mSearchParams.page = 0;
            mProgressArea.setVisibility(View.VISIBLE);
        }else{
            mProgressArea.setVisibility(View.GONE);
        }
        new ApiManager().getSearchResult(mSearchParams, bAppendResult, this);
    }

    @Override
    public void setSearchResult(NYTSearchResult searchResult, boolean bAppendResult) {
        mSearchParams.page++;
        ((DocAdapter) mDocList.getAdapter()).bind(searchResult.response.docs, bAppendResult);
        if(!bAppendResult){
            mDocList.scrollToPosition(0);
        }
        mProgressArea.setVisibility(View.GONE);
    }

    @Override
    public void setFailure(int statusCode, String res) {
        Toast.makeText(this, res, Toast.LENGTH_LONG).show();
    }

    @Override
    public void continueLoading() {
        query(true);
    }

    @Override
    public void setSearchParams(SearchParams searchParams) {
        mSearchParams = searchParams;
        query(false);
    }
}
