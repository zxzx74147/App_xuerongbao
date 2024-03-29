package com.wazxb.xuerongbao.network;

import com.wazxb.xuerongbao.moudles.account.AccountManager;
import com.zxzx74147.devlib.network.BaseHttpRequest;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.utils.ZXStringUtil;

/**
 * Created by zhengxin on 15/9/15.
 */
public class ZXBHttpRequest<T> extends BaseHttpRequest<T> {
    private String mPath;

    public boolean isRefresh = false;

    public ZXBHttpRequest(Class<T> dstClass, HttpResponseListener<T> listener) {
        super(dstClass, listener);
        if (ZXStringUtil.checkString(AccountManager.sharedInstance().getUid())) {
            addParams("uId", AccountManager.sharedInstance().getUid());
        }
//        mMothod = Request.Method.POST;
    }

    public void setPath(String path) {
        mPath = path;
        setUrl(NetworkConfig.HOST + mPath);
    }

    public String getPath() {
        return mPath;
    }


}
