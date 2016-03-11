package com.zxzx74147.devlib.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.zxzx74147.devlib.network.BaseHttpRequest;
import com.zxzx74147.devlib.network.HttpManager;
import com.zxzx74147.devlib.utils.CustomToast;
import com.zxzx74147.devlib.utils.ZXUniqueIDGenerator;


/**
 * Created by zhengxin on 15/8/27.
 */
public class ZXBaseActivity extends FragmentActivity {
    private int mUniqueID = ZXUniqueIDGenerator.getUniqueID();

    public ZXBaseActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    public void showToast(String content) {
        CustomToast.newInstance().showToast(content);
    }

    protected void showToast(int contentID) {
        CustomToast.newInstance().showToast(contentID);
    }

    public void sendRequest(BaseHttpRequest request) {
        if (isFinishing()) {
            return;
        }
        request.setTag(mUniqueID);
        request.send();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpManager.cancel(mUniqueID);
    }


}
