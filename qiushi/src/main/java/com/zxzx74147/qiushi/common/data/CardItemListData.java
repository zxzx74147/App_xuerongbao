package com.zxzx74147.qiushi.common.data;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by zhengxin on 15/9/1.
 */
public class CardItemListData implements Serializable {
    public long count;
    public int err;
    public long refresh;
    public long total;
    public long page;
    public LinkedList<CardItemData> items;
}
