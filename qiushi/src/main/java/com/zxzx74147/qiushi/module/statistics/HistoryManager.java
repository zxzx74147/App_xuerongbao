package com.zxzx74147.qiushi.module.statistics;

import java.util.HashSet;

/**
 * Created by zhengxin on 15/9/16.
 */
public class HistoryManager {
    private static HashSet<Long> mHistory = new HashSet<>();

    public static void addHistory(long history){
        mHistory.add(history);
    }

    public static String getHistory(){
        StringBuffer sb = new StringBuffer(mHistory.size()*10);
        sb.append("[");
        for(Long item:mHistory){
            if(sb.length()>1){
                sb.append(",");
            }
            sb.append(String.valueOf(item));
        }
        sb.append("]");
        mHistory.clear();
        return sb.toString();
    }

}
