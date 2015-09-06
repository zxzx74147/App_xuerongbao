package com.zxzx74147.devlib.network;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;

/**
 * Created by zhengxin on 15/9/6.
 */
public class BaseHttpRequest<T> {
    protected String mUrl;
    protected HashMap<String,Object> mParams;
    protected int mMothod;
    protected boolean mLoadCache = false;
    protected Request mProxy;
    protected HttpResponseListener<T> mResponseListener;

    public void setUrl(String url){
        mUrl = url;
    }

    public void addParams(String key,Object value){
        if(mParams == null) {
            mParams = new HashMap<>();
        }
        mParams.put(key,value);
    }


    public void send(){
        if(mMothod == Request.Method.GET){
            String path = mUrl;
            if(mUrl.contains("?")){
                path+=ZXNetworkUtil.getKVParamData(mParams);
            }else{
                path+="?"+ZXNetworkUtil.getKVParamData(mParams);
            }
            mProxy = new RequestProxy(mMothod, path, ZXNetworkUtil.getKVParamData(mParams), mSucessListener, mFailListener);
        }else {
            mProxy = new RequestProxy(mMothod, mUrl, ZXNetworkUtil.getKVParamData(mParams), mSucessListener, mFailListener);
        }
        HttpManager.sendRequest(mProxy);
    }

    private Response.Listener<String> mSucessListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.e("http",response);
        }
    };

    private Response.ErrorListener mFailListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("http_error",error.getMessage());
        }
    };
}
