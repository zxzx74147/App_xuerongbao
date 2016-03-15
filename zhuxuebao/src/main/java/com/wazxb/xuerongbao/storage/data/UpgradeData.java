package com.wazxb.xuerongbao.storage.data;

import java.io.Serializable;

/**
 * Created by zhengxin on 16/3/3.
 */
public class UpgradeData implements Serializable{
    public int show;           //是否提示版本更新（1:是，0:否）
    public int force;       //是否强制版本更新（1:是，0:否）
    public String msg;            //版本更新文案
}
