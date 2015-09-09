package com.notus.fit.network.misfit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.notus.fit.R;

public class MisfitOauthActivity extends ActionBarActivity {
    private static final String TAG = MisfitOauthActivity.class.getSimpleName();
    private String accessCode;

    public MisfitOauthActivity() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_oauth_base);
        Uri uri = (Uri) getIntent().getParcelableExtra(MisfitClient.AUTH_URI);
        WebView webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new ViewClient());
        webview.loadUrl(uri.toString());
    }

    class ViewClient extends WebViewClient {

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            String accessCodeFragment = "code=";
            Log.e(MisfitOauthActivity.TAG, "oauth response from server: " + url);
            int start = url.indexOf(accessCodeFragment);
            if (start > -1) {
                Log.d(MisfitOauthActivity.TAG, "user accepted, url is :" + url);
                MisfitOauthActivity.this.accessCode = url.substring(accessCodeFragment.length() + start, url.length());
                Log.d(MisfitOauthActivity.TAG, "user accepted, code is :" + MisfitOauthActivity.this.accessCode);
                view.clearCache(true);
                Intent i = MisfitOauthActivity.this.getIntent();
                i.putExtra(MisfitClient.ACCESS_CODE, MisfitOauthActivity.this.accessCode);
                MisfitOauthActivity.this.setResult(-1, i);
                MisfitOauthActivity.this.finish();
            }
        }

    }
}