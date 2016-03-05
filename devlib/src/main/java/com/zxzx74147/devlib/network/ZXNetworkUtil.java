package com.zxzx74147.devlib.network;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengxin on 15/9/6.
 */
public class ZXNetworkUtil {

    public static String getKVParamData(HashMap<String, Object> params) {
        if (params == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer(params.size() * 20);
        for (Map.Entry<String, Object> item : params.entrySet()) {
            if (sb.length() != 0) {
                sb.append("&");
            }
            sb.append(item.getKey());
            sb.append("=");
            sb.append(item.getValue());
        }
        return sb.toString();
    }

    public static HttpEntity fillKVParamData(HashMap<String, Object> params) {
        if (params == null) {
            return null;
        }
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        StringBuffer sb = new StringBuffer(params.size() * 20);
        for (Map.Entry<String, Object> item : params.entrySet()) {
            if (item.getValue() instanceof byte[]) {
                builder.addBinaryBody(item.getKey(), (byte[]) item.getValue());
            } else {
                builder.addTextBody(item.getKey(), item.getValue().toString());
            }
        }
        return builder.build();
    }

    public static String getJsonParamData(HashMap<String, String> params) {
        JSONObject json = new JSONObject(params);
        return json.toString();
    }
}
