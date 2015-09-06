package com.zxzx74147.devlib.network;

/**
 * Created by zhengxin on 15/9/6.
 */
public interface HttpResponseListener<T> {
    void  onResponse(HttpResponse<T> response);
}
