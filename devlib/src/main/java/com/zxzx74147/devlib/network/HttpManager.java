package com.zxzx74147.devlib.network;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by zhengxin on 15/9/1.
 */
public class HttpManager {

    private RequestQueue mRequestQueue;
    private HttpManager(){

    }

    public void init(Application application){
        mRequestQueue =  Volley.newRequestQueue(application);
        mRequestQueue.start();
    }

    public void stop(){
        mRequestQueue.stop();
    }
    public void start(){
        mRequestQueue.start();
    }


}
