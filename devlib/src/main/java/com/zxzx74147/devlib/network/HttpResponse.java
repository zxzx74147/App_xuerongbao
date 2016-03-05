package com.zxzx74147.devlib.network;

/**
 * Created by zhengxin on 15/9/6.
 */
public class HttpResponse<T> {
    public int error;
    public String errorString;
    public T result;
    public boolean mIsLoadCache;

    public boolean hasError(){
        return error != 200;
    }
}
