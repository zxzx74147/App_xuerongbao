package com.wazxb.xuerongbao.storage.data;

import java.io.Serializable;

/**
 * Created by zhengxin on 16/3/13.
 */
public class EvaluateItemData implements Serializable {
    public int evId;   //评论ID
    public String name;     //用户名
    public String portrait;     //头像（picUrl）
    public int time;   //评论时间
    public int star;   //评论星星
    public String title;     //评论标题
    public String content;     //评论内容
}
