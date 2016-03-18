package com.wazxb.xuerongbao.moudles.message;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.sdk.android.event.EventBus;
import com.lusfold.androidkeyvaluestore.KVStore;
import com.wazxb.xuerongbao.EventBusConfig;
import com.wazxb.xuerongbao.network.NetworkConfig;
import com.wazxb.xuerongbao.network.ZXBHttpRequest;
import com.wazxb.xuerongbao.storage.StorageManager;
import com.wazxb.xuerongbao.storage.data.MessageData;
import com.wazxb.xuerongbao.util.ActivityStateManager;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.utils.AsyncHelper;
import com.zxzx74147.devlib.utils.ZXJsonUtil;
import com.zxzx74147.devlib.utils.ZXStringUtil;

/**
 * Created by zhengxin on 16/3/13.
 */
public class MessageManager {
    private static final int POLL_INV = 5000;
    private static final int POLL_MAX_INV = 10000;
    private static final String KEY_MESSAGE = "key_message_v2";

    private static MessageManager mInstance = null;

    private long mLastRequest = System.currentTimeMillis();
    private boolean mHasStop = false;
    private int newNum = 0;
    private boolean mHasInit = false;


    private MessageData mData = new MessageData();
    private ZXBHttpRequest<MessageData> mRequest = null;
    private AsyncTask mInitTask = null;
    private static final int MSG_CHECK = 1;


    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_CHECK:
                    mHandler.removeMessages(MSG_CHECK);
                    if (!mHasStop) {
                        mHandler.sendEmptyMessageDelayed(MSG_CHECK, POLL_INV);
                    } else {
                        return false;
                    }
                    if (!mHasInit) {
                        return false;
                    }
                    long now = System.currentTimeMillis();
                    if (mLastRequest > now) {
                        mLastRequest = now;
                    }
                    if (mRequest != null) {
                        if (now - mLastRequest < POLL_MAX_INV) {
                            return false;
                        }
                    }
                    doRequest();
                    break;
            }
            return false;
        }
    });

    private MessageManager() {
        de.greenrobot.event.EventBus.getDefault().register(this);
        doInit();
    }

    public static MessageManager sharedInstance() {
        if (mInstance == null) {
            mInstance = new MessageManager();
        }
        return mInstance;
    }

    public void startPoll() {
        mHasStop = false;
        mHandler.sendEmptyMessageDelayed(MSG_CHECK, POLL_INV);
    }

    public void stopPoll() {
        mHasStop = true;
    }

    private void doInit() {
        if (mInitTask != null) {
            return;
        }
        mInitTask = AsyncHelper.executeAsyncTask(new AsyncHelper.BDTask() {
            @Override
            public Object executeBackGround() {
                String initData = KVStore.getInstance().get(KEY_MESSAGE);
                if (ZXStringUtil.checkString(initData)) {
                    mData = ZXJsonUtil.fromJsonString(initData, MessageData.class);
                }
                return mData;
            }

            @Override
            public void postExecute(Object result) {
                mHasInit = true;
                mInitTask = null;
                mHandler.sendEmptyMessage(MSG_CHECK);
            }
        });
    }

    private void doRequest() {
        mRequest = new ZXBHttpRequest<>(MessageData.class, new HttpResponseListener<MessageData>() {
            @Override
            public void onResponse(HttpResponse<MessageData> response) {
                mRequest = null;
                if (response.hasError()) {
                    return;
                }
                mergeData(response.result);
                mHandler.sendEmptyMessageDelayed(MSG_CHECK, POLL_INV);
            }
        });
        mRequest.isRefresh = true;
        mRequest.addParams("lastMId", mData.lastMId);
        mRequest.setPath(NetworkConfig.ADDRESS_U_MSG);
        mRequest.send();
    }

    private void mergeData(MessageData msgData) {
        if (msgData.lastMId != mData.lastMId) {
            if (msgData.msgList != null) {
                newNum += msgData.msgList.num;
            }
            mData.mergeData(msgData);
            EventBus.getDefault().sendEvent(EventBusConfig.EVENT_MESSAGE_REFRESH);
            StorageManager.sharedInstance().saveKVObjectAsync(KEY_MESSAGE, mData);
        }
    }

    public MessageData getMessageData() {
        return mData;
    }

    public int getNewNum() {
        if(mData == null || mData.msgList == null){
            return 0;
        }
        return mData.msgList.getUnReadNum();
    }

    public void clearNew() {
        newNum = 0;
    }

    public void onEvent(String event) {
        Log.e("event", event);
        if (EventBusConfig.EVENT_FRONT_BACK_CHANGED.equals(event)) {
            boolean isBackGround = ActivityStateManager.sharedInstance().getIsBackGround();
            if (isBackGround) {
                stopPoll();
            } else {
                startPoll();
            }
        }
    }

    public void saveMsg() {
        StorageManager.sharedInstance().saveKVObjectAsync(KEY_MESSAGE, mData);
    }
}
