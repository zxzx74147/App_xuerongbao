package com.zxzx74147.qiushi.libs.network;

import com.zxzx74147.devlib.network.BaseHttpRequest;
import com.zxzx74147.devlib.network.HttpResponseListener;

/**
 * Created by zhengxin on 15/9/15.
 */
public class QSHttpRequest<T> extends BaseHttpRequest<T> {
    private String mPath;
    public QSHttpRequest(Class<T> dstClass, HttpResponseListener<T> listener) {
        super(dstClass, listener);
    }

    public void setPath(String path){
        mPath = path;
    }

    public String getPath(){
        return mPath;
    }
}
