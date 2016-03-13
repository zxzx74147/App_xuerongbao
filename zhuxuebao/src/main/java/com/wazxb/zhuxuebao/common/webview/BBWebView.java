package com.wazxb.zhuxuebao.common.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class BBWebView extends WebView {

	private WebSettings mSettings;

	public BBWebView(Context context) {
		super(context);
		initSettings();
	}

	public BBWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initSettings();
	}

	public BBWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initSettings();
	}
	
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        invalidate();
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

	@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
	private void initSettings() {
		mSettings = getSettings();
		mSettings.setJavaScriptEnabled(true);
		mSettings.setDomStorageEnabled(true);
		mSettings.setUseWideViewPort(true);
		mSettings.setLoadWithOverviewMode(true);
		mSettings.setAllowFileAccess(true);
		mSettings.setAppCacheEnabled(true);
		mSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
		mSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
		mSettings.setJavaScriptCanOpenWindowsAutomatically(true);
	}

}
