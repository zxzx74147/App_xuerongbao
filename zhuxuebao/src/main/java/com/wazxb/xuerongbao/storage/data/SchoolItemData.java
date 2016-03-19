package com.wazxb.xuerongbao.storage.data;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import java.io.Serializable;

/**
 * Created by zhengxin on 16/3/19.
 */
public class SchoolItemData implements Serializable {
    public String name;

    public void onClick(View v) {
        Activity activity = (Activity) v.getContext();
        Intent intent = new Intent();
        intent.putExtra("data",name);
        activity.setResult(Activity.RESULT_OK,intent);
        activity.finish();
    }
}
