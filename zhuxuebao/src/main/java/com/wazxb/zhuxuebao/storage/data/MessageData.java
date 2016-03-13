package com.wazxb.zhuxuebao.storage.data;

/**
 * Created by zhengxin on 16/3/13.
 */
public class MessageData {
    public MessageListData msgList;
    public int lastMId;

    public void mergeData(MessageData data) {
        if (data == null) {
            return;
        }
        lastMId = Math.max(lastMId, data.lastMId);
        if(msgList == null){
            msgList = data.msgList;
            return;
        }
        msgList.mergeData(data.msgList);

    }
}
