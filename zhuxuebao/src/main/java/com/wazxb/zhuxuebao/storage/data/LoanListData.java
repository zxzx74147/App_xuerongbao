package com.wazxb.zhuxuebao.storage.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhengxin on 16/3/11.
 */
public class LoanListData implements Serializable {
    public int num;   //
    public List<LoanItemData> loan;
}
