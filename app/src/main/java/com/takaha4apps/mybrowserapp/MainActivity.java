package com.takaha4apps.mybrowserapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private WebView myWebView;
    private EditText urlText;

    private static final String INITIAL_WEBSITE = "http://dotinstall.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //取得
        myWebView = (WebView) findViewById(R.id.myWebView);
        urlText = (EditText) findViewById(R.id.urlText);

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                //ActionBarに今覗いてるHPのタイトルを入れる
                getSupportActionBar().setSubtitle(view.getTitle());
                //edittextにurlリンクを入れる
                urlText.setText(url);
            }
        });
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl(INITIAL_WEBSITE);

    }

    @Override
    public void onBackPressed() {
        //Backボタンを押したときに強制終了しないようにする
        if (myWebView.canGoBack()) {
            myWebView.goBack();
            return;
        }
        super.onBackPressed();
    }

    //メモリ管理
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myWebView != null) {
            myWebView.stopLoading();
            myWebView.setWebViewClient(null);
            myWebView.destroy();
        }
        myWebView = null;
    }

    public void showWebsite(View view) {
        String url = urlText.getText().toString().trim();
        //URL妥当性チェック
        if (!Patterns.WEB_URL.matcher(url).matches()) {
            urlText.setError("Invalid URL");
        } else {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            myWebView.loadUrl(url);
        }
    }

    public void clearUrl(View view) {
        urlText.setText("");
    }
}
