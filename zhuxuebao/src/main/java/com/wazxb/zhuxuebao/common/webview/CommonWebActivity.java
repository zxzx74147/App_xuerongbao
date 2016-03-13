package com.wazxb.zhuxuebao.common.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.zxzx74147.devlib.widget.TitleBar;

public class CommonWebActivity extends ZXBBaseActivity {
    private static final String URL = "url";
    private static final String TITLE = "title";
    private BBWebView mWebView = null;
    private View mBackButton = null;
    private String mUrl = null;
    private TitleBar mTitle = null;

    public static void startActivity(Context context, String title, String url) {
        Intent intent = new Intent(context, CommonWebActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.putExtra(TITLE, title);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initData(savedInstanceState);

    }

    private void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mUrl = getIntent().getStringExtra(URL);
        } else {
            mUrl = savedInstanceState.getString(URL);
        }

        mWebView.loadUrl(mUrl);
        mTitle.setText(getIntent().getStringExtra(TITLE));
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(URL, mUrl);
    }

    private void initUI() {
        setContentView(R.layout.activity_web);
        mTitle = (TitleBar) findViewById(R.id.title_bar);
        mWebView = (BBWebView) findViewById(R.id.webview);
        mWebView.setWebViewClient(new BBCommonWebViewClient());
        mBackButton = findViewById(R.id.back);
        mBackButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                    return;
                }
                finish();
            }
        });
    }

    public class BBCommonWebViewClient extends WebViewClient {


        @Override
        public void onPageFinished(WebView view, String url) {
            mTitle.setText(view.getTitle());
        }

        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, String url) {


            view.loadUrl(url);
            return true;
        }
    }
}
