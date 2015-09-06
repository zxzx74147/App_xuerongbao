package com.zxzx74147.devlib.network;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengxin on 15/9/6.
 */
public class ZXNetworkUtil {

    public static String getKVParamData(HashMap<String ,Object> params){
        if(params == null){
            return "";
        }
        StringBuffer sb = new StringBuffer(params.size()*20);
        for(Map.Entry<String,Object> item:params.entrySet()){
            if(sb.length() != 0){
                sb.append("&");
            }
            sb.append(item.getKey());
            sb.append("=");
            sb.append(item.getValue());
        }
        return sb.toString();
    }

    public static String getJsonParamData(HashMap<String ,String> params){
        JSONObject json = new JSONObject(params);
        return json.toString();
    }
}
