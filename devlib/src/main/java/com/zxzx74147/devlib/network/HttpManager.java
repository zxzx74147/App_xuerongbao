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

}
