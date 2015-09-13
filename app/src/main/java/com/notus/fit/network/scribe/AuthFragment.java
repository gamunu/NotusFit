package com.notus.fit.network.scribe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.notus.fit.R;
import com.notus.fit.fragments.AddAccountsFragment;
import com.notus.fit.fragments.DefaultFragment;
import com.notus.fit.network.fitbit.FitbitApi;

import org.scribe.builder.ServiceBuilder;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AuthFragment extends DefaultFragment {
    private static final String LOG_TAG = AuthFragment.class.getSimpleName();
    @Bind(R.id.wvAuthorise)
    WebView wvAuthorise;

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind((Object) this, view);
        this.wvAuthorise.getSettings().setJavaScriptEnabled(true);
        this.wvAuthorise.addJavascriptInterface(new MyJavaScriptInterface(), "HtmlViewer");
        this.wvAuthorise.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                AuthFragment.this.wvAuthorise.loadUrl("javascript:window.HtmlViewer.showHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
            }
        });
        AddAccountsFragment.service = new ServiceBuilder().provider(FitbitApi.class).apiKey(FitbitApi.API_KEY).apiSecret(FitbitApi.API_SECRET).build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AddAccountsFragment.requestToken = AddAccountsFragment.service.getRequestToken();
                    final String authURL = AddAccountsFragment.service.getAuthorizationUrl(AddAccountsFragment.requestToken);
                    AuthFragment.this.wvAuthorise.post(new Runnable() {
                        @Override
                        public void run() {
                            AuthFragment.this.wvAuthorise.loadUrl(authURL);
                        }
                    });
                } catch (Exception e) {
                    if (AuthFragment.this.getActivity() != null) {
                        Toast.makeText(AuthFragment.this.getActivity(), "Oops! Something went wrong.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }).start();
    }

    public int getLayoutResource() {
        return R.layout.fragment_auth;
    }

    class MyJavaScriptInterface {
        boolean firstRun;

        public MyJavaScriptInterface() {
            this.firstRun = true;
        }

        @JavascriptInterface
        public void showHTML(String html) {
            if (this.firstRun) {
                this.firstRun = false;
                return;
            }
            try {
                String divStr = "gap20\">";
                int first = html.indexOf(divStr);
                int second = html.indexOf("</div>", first);
                if (first != -1) {
                    String pin = html.substring(divStr.length() + first, second);
                    Intent intent = new Intent();
                    intent.putExtra("PIN", pin);
                    AuthFragment.this.getActivity().setResult(-1, intent);
                    AuthFragment.this.getActivity().finish();
                }
            } catch (Exception e) {
                Log.d(AuthFragment.LOG_TAG, e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
