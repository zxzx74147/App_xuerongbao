package com.wazxb.xuerongbao.moudles.message;

import android.os.Bundle;

import com.wazxb.xuerongbao.EventBusConfig;
import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.list.ZXBBaseListActivity;
import com.wazxb.xuerongbao.base.list.ZXBViewBinder;
import com.wazxb.xuerongbao.databinding.ActivityMessageListBinding;

import de.greenrobot.event.EventBus;

/**
 * Created by zhengxin on 16/3/4.
 */
public class MessageListActivity extends ZXBBaseListActivity {

    private ActivityMessageListBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = (ActivityMessageListBinding) super.mBinding;
        mBinding.setHandler(this);
        refreshData();

    }

    @Override
    protected ZXBViewBinder getItemBinder() {
        return new MessageBinder();
    }

    @Override
    protected void refreshData() {
        completeLoading();
        if (MessageManager.sharedInstance().getMessageData().msgList != null) {
            setData(MessageManager.sharedInstance().getMessageData().msgList.msg);
        }
    }


    protected int getLayoutID() {
        return R.layout.activity_message_list;
    }

    @Override
    public void onStart() {
        super.onStart();
        //注册EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        //注册EventBus
        refreshData();
    }

    @Override
    public void onStop() {
        super.onStop();
        //取消EventBus
        EventBus.getDefault().unregister(this);
    }

    //事件1接收者：在主线程接收
    public void onEvent(String event) {
        if (EventBusConfig.EVENT_MESSAGE_REFRESH.equals(event)) {
            if (MessageManager.sharedInstance().getMessageData().msgList != null) {
                setData(MessageManager.sharedInstance().getMessageData().msgList.msg);
            }
        }
    }

    @Override
    protected int getEmptyStringID(){
        return R.string.empty_messge;
    }
}