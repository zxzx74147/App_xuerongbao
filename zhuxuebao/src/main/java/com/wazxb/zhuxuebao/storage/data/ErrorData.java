package com.wazxb.zhuxuebao.storage.data;

import java.io.Serializable;

/**
 * Created by zhengxin on 16/2/27.
 */
public class ErrorData implements Serializable {
    public int errno;           //错误号
    public String errmsg;             //错误信息
    public String usermsg;        //给用户提示信息
}
