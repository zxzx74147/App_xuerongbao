package com.zxzx74147.devlib.network;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by zhengxin on 15/9/1.
 */
public class HttpManager {

    private static RequestQueue mRequestQueue;
    private HttpManager(){

    }

    public static void init(Application application){
        if(mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(application);
            mRequestQueue.start();
        }
    }

    public static void stop(){
        mRequestQueue.stop();
    }
    public static void start(){
        mRequestQueue.start();
    }

    public static void sendRequest(Request request){
        mRequestQueue.add(request);
    }

    public static void cancel(final int  id){
        mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                Object tag = request.getTag();
                if(tag != null && tag instanceof BaseHttpRequest){
                    BaseHttpRequest oriRequest = (BaseHttpRequest) tag;
                    if(oriRequest.getTag() == id){
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public static void cancel(BaseHttpRequest request){
        mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                if(request.getTag() == request){
                    return true;
                }
                return false;
            }
        });
    }

}
