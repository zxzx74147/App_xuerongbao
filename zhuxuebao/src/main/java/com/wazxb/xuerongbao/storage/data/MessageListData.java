package com.wazxb.xuerongbao.storage.data;

import java.util.List;

/**
 * Created by zhengxin on 16/3/13.
 */
public class MessageListData {
    public int num;   //消息数
    public List<MessageItemData> msg;  //消息

    public int getUnReadNum() {
        int count = 0;
        if (msg == null) {
            return 0;
        }
        for (MessageItemData data : msg) {
            if (!data.mHasRead) {
                count++;
            }
        }
        return count;
    }


    public void mergeData(MessageListData data) {
        if (data == null) {
            return;
        }
        if (msg == null) {
            msg = data.msg;
            return;
        }
        if (data.msg == null) {
            return;
        }
        msg.addAll(0, data.msg);
        while (msg.size() > 1000) {
            msg.remove(msg.size() - 1);
        }
    }
}
