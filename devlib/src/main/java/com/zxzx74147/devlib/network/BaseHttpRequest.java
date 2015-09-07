package com.zxzx74147.devlib.network;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;

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
    private Class<T> mDstClass;

    public BaseHttpRequest(Class<T> dstClass,HttpResponseListener<T> listener){
        super();
        mResponseListener = listener;
        mDstClass = dstClass;
    }

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
        public void onResponse(final String response) {
            Task<T> task = Task.callInBackground(new Callable<T>() {
                @Override
                public T call() throws Exception {
                    T data = new Gson().fromJson(response,mDstClass);
                    return data;
                }
            }).onSuccess(new Continuation<T, T>() {
                @Override
                public T then(Task<T> task) throws Exception {
                    HttpResponse rsp = new HttpResponse();
                    rsp.result = task.getResult();
                    mResponseListener.onResponse(rsp);
                    return null;
                }
            });


        }
    };

    private Response.ErrorListener mFailListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("http_error",error.getMessage());
        }
    };
}
