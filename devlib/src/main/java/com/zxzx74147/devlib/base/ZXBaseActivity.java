package com.zxzx74147.devlib.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


/**
 * Created by zhengxin on 15/8/27.
 */
public class ZXBaseActivity extends FragmentActivity {

    public ZXBaseActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    protected void showToast(String content){

    }

    protected void showToast(int contentID){

    }




}
