package com.wazxb.zhuxuebao.widget;

/**
 * Created by zhengxin on 16/2/27.
 */
public interface ICheckable {
    boolean checkDone();

    void onStateChanged(boolean isReady);
}
