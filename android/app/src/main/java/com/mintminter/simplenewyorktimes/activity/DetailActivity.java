package com.mintminter.simplenewyorktimes.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String title = getIntent().getStringExtra(Common.EXTRA_TITLE);
        String url = getIntent().getStringExtra(Common.EXTRA_URL);
        if(TextUtils.isEmpty(title) || TextUtils.isEmpty(url)){
            finish();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitle(title);
        //setSupportActionBar(toolbar);

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
}
