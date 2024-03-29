package com.zxzx74147.devlib.network;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.LinkedHashMap;
import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;

/**
 * Created by zhengxin on 15/9/6.
 */
public class BaseHttpRequest<T> {
    protected String mUrl;
    protected LinkedHashMap<String, Object> mParams;
    protected int mMothod;
    protected boolean mLoadCache = false;
    protected Request mProxy;
    protected HttpResponseListener<T> mResponseListener;
    private Class<T> mDstClass;
    private int mTag = 0;
    private boolean isCancel = false;
    private ErrorData mError;

    public BaseHttpRequest(Class<T> dstClass, HttpResponseListener<T> listener) {
        super();
        mResponseListener = listener;
        mDstClass = dstClass;
    }

    public int getTag() {
        return mTag;
    }

    public void setTag(int tag) {
        mTag = tag;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public void addParams(String key, Object value) {
        if (mParams == null) {
            mParams = new LinkedHashMap<>();
        }
        mParams.put(key, value);
    }

    public Object getParams(String key) {
        return mParams.get(key);
    }

    public void setMethod(int mothod) {
        mMothod = mothod;
    }


    public void send() {
        if (mMothod == Request.Method.GET) {
            String path = mUrl;
            if (mUrl.contains("?")) {
                path += ZXNetworkUtil.getKVParamData(mParams);
            } else {
                path += "?" + ZXNetworkUtil.getKVParamData(mParams);
            }
            mProxy = new RequestProxy(mMothod, path, ZXNetworkUtil.getKVParamData(mParams), mSucessListener, mFailListener);
        } else {
            mProxy = new RequestProxy(mMothod, mUrl, ZXNetworkUtil.getKVParamData(mParams), mSucessListener, mFailListener);
//            mProxy = new UplaodRequestProxy(mMothod, mUrl, mParams, mSucessListener, mFailListener);
        }
        mProxy.setTag(this);
        HttpManager.sendRequest(mProxy);
    }

    private Response.Listener<String> mSucessListener = new Response.Listener<String>() {
        @Override
        public void onResponse(final String response) {

            final Task<T> task = Task.callInBackground(new Callable<T>() {
                @Override
                public T call() throws Exception {
                    String url = mUrl + "?" + new String(mProxy.getBody());
                    Log.d("NETWORK URL = ", url);
                    Log.d("NETWORK RSP = ", response);
                    T data = null;
                    try {
                        mError = JSON.toJavaObject(JSON.parseObject(response).getJSONObject("error"), ErrorData.class);
                        data = JSON.parseObject(response, mDstClass);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return data;
                }
            }).
                    onSuccess(new Continuation<T, T>() {
                        @Override
                        public T then(Task<T> task) throws Exception {
                            if (isCancel) {
                                return null;
                            }

                            HttpResponse rsp = new HttpResponse();
                            if (mError != null && mError.errno != 0) {
                                rsp.error = mError.errno;
                                rsp.errorString = mError.usermsg;
                            }
                            rsp.result = task.getResult();
                            mResponseListener.onResponse(rsp);
                            return null;
                        }
                    }, Task.UI_THREAD_EXECUTOR);


        }
    };

    private Response.ErrorListener mFailListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if (error != null) {
                if (error.getMessage() != null) {
                    Log.e("http_error", error.getMessage());
                    if (mResponseListener != null) {
                        HttpResponse rsp = new HttpResponse();
                        rsp.error = -1;
                        rsp.errorString = error.getMessage();
                        mResponseListener.onResponse(rsp);
                    }
                }
            }
        }
    };

    public void cancel() {
        isCancel = true;
        HttpManager.cancel(this);
    }
}
