package com.wazxb.xuerongbao.storage.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhengxin on 16/3/13.
 */
public class EvaluateList implements Serializable {
    public int num;   //评论数
    public int hasMore;   //是否有更多，0:没有,1:有
    public int evId;   //翻页Id

    public List<EvaluateItemData> evaluate;
}
