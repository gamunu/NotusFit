package com.jawbone.upplatformsdk.oauth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.notus.fit.R;
import com.notus.fit.network.misfit.MisfitClient;

public class OauthWebViewActivity extends Activity {
    private static final String TAG;

    static {
        TAG = OauthWebViewActivity.class.getSimpleName();
    }

    private String accessCode;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oauth_webview);
        Uri uri = (Uri) getIntent().getParcelableExtra(MisfitClient.AUTH_URI);
        WebView webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new C07261());
        webview.loadUrl(uri.toString());
    }

    /* renamed from: com.jawbone.upplatformsdk.oauth.OauthWebViewActivity.1 */
    class C07261 extends WebViewClient {
        C07261() {
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            String accessCodeFragment = "&code=";
            Log.e(OauthWebViewActivity.TAG, "oauth response from server: " + url);
            int start = url.indexOf(accessCodeFragment);
            if (start > -1) {
                Log.d(OauthWebViewActivity.TAG, "user accepted, url is :" + url);
                OauthWebViewActivity.this.accessCode = url.substring(accessCodeFragment.length() + start, url.length());
                Log.d(OauthWebViewActivity.TAG, "user accepted, code is :" + OauthWebViewActivity.this.accessCode);
                view.clearCache(true);
                Intent i = OauthWebViewActivity.this.getIntent();
                i.putExtra("code", OauthWebViewActivity.this.accessCode);
                OauthWebViewActivity.this.setResult(-1, i);
                OauthWebViewActivity.this.finish();
            }
        }
    }
}
