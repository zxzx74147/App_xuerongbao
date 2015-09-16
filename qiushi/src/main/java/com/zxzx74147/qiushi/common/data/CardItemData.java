package com.zxzx74147.qiushi.common.data;

import java.io.Serializable;

/**
 * Created by zhengxin on 15/9/1.
 */
public class CardItemData implements Serializable{
    public VoteData votes;
    public long created_at;
    public long published_at;
    public String content;
    public String state;
    public String tag;
    public long comments_count;
    public UserData user;
    public String image;
    public boolean allow_comment;
    public long share_count;
    public String type;
    public long id;
    public ImageSizeData image_size;

}
