package com.mintminter.simplenewyorktimes.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.mintminter.simplenewyorktimes.R;
import com.mintminter.simplenewyorktimes.util.Common;

/**
 * Created by Irene on 9/23/17.
 */

public class DetailActivity extends AppCompatActivity {

    private RelativeLayout mProgressBar;
    private WebView mWebView;

    private ShareActionProvider mShareAction;
    private Intent mShareIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String title = getIntent().getStringExtra(Common.EXTRA_TITLE);
        String url = getIntent().getStringExtra(Common.EXTRA_URL);
        if(TextUtils.isEmpty(title) || TextUtils.isEmpty(url)){
            finish();
        }
        prepareShareIntent(title, url);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mProgressBar = (RelativeLayout) findViewById(R.id.detail_progressbar);

        mWebView = (WebView) findViewById(R.id.detaio_webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.GONE);
            }

        });
        mWebView.loadUrl(url);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        return true;

    }

    private void prepareShareIntent(String title, String text) {
        mShareIntent = new Intent(Intent.ACTION_SEND);
        mShareIntent.setType("text/plain");
        mShareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,title);
        mShareIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
            case R.id.detail_share:
                startActivity(Intent.createChooser(mShareIntent, "Shearing Option"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
