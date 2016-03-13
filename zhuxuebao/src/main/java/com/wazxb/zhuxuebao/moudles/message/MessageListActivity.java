package com.wazxb.zhuxuebao.moudles.message;

import android.os.Bundle;

import com.wazxb.zhuxuebao.EventBusConfig;
import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.list.ZXBBaseListActivity;
import com.wazxb.zhuxuebao.base.list.ZXBViewBinder;
import com.wazxb.zhuxuebao.databinding.ActivityMessageListBinding;
import com.wazxb.zhuxuebao.network.ZXBHttpRequest;
import com.wazxb.zhuxuebao.storage.data.MessageData;

import de.greenrobot.event.EventBus;

/**
 * Created by zhengxin on 16/3/4.
 */
public class MessageListActivity extends ZXBBaseListActivity {

    private ActivityMessageListBinding mBinding;
    private ZXBHttpRequest<MessageData> mRequest = null;

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
        if (mRequest != null) {
            return;
        }
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
}