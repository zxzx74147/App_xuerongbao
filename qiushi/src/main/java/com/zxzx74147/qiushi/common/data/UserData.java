package com.zxzx74147.qiushi.common.data;

import com.zxzx74147.qiushi.libs.util.ImageUtil;

import java.io.Serializable;

/**
 * Created by zhengxin on 15/9/1.
 */
public class UserData implements Serializable {
    public long avatar_updated_at;
    public long last_visited_at;
    public long created_at;
    public long id;
    public String state;
    public String email;
    public String last_device;
    public String role;
    public String login;
    public String icon;

    public String getAvgUrl(){
        return ImageUtil.getAvgUrl(String.valueOf(id),icon);
    }
}
