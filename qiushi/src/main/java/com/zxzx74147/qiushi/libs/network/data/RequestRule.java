package com.zxzx74147.qiushi.libs.network.data;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by zhengxin on 15/9/15.
 */
public class RequestRule implements Serializable{
    public LinkedList<Param> params;
    public String method;
    public String path;
    public String host;
    public String include;
}
