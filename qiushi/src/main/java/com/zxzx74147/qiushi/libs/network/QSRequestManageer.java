package com.zxzx74147.qiushi.libs.network;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.zxzx74147.devlib.utils.ZXAssetUtil;
import com.zxzx74147.qiushi.libs.network.data.Param;
import com.zxzx74147.qiushi.libs.network.data.RequestRule;
import com.zxzx74147.qiushi.libs.util.DeviceUtil;
import com.zxzx74147.qiushi.module.statistics.HistoryManager;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zhengxin on 15/9/15.
 */
public class QSRequestManageer {
    private static QSRequestManageer mInstance = null;
    private NetworkConfig mConfig;
    private HashMap<String,RequestRule> mTable = new HashMap<>(100);
    private String mAndroidID;
    private int mRqcnt = 1;

    private QSRequestManageer(){
        mAndroidID = DeviceUtil.getAndroidId();
        mAndroidID = mAndroidID.substring(mAndroidID.length()-8);
        initRequest();
    }

    public static QSRequestManageer sharedInstance(){
        if(mInstance == null){
            mInstance = new QSRequestManageer();
        }
        return mInstance;
    }

    private void initRequest(){
        String config = ZXAssetUtil.getFromAssets("network/config.json");
        mConfig = JSON.parseObject(config, NetworkConfig.class);
        String requsts = ZXAssetUtil.getFromAssets("network/request.json");
        List<RequestRule> rules = JSON.parseArray(requsts,RequestRule.class);
        for(RequestRule rule:rules){
            if(mTable.containsKey(rule.path)){
                Log.e("QSRequestManageer", "duplicate path=" + rule.path);
            }
            mTable.put(rule.path,rule);
        }
    }

    public boolean dealRequest(QSHttpRequest request){
        String path = request.getPath();
        RequestRule requestRule = mTable.get(path);
        if(requestRule == null){
            throw new IllegalArgumentException("error path="+path);
        }

        String url = mConfig.HOST+path;
        request.setUrl(url);

        if(requestRule.params!= null){
            for(Param param:requestRule.params){
                if(request.getParams(param.key)==null){
                    request.addParams(param.key,param.value);
                }
            }
        }

        request.addParams("r", mAndroidID+System.currentTimeMillis());
        request.addParams("rqcnt",mRqcnt++);
        request.addParams("readarticles", HistoryManager.getHistory());

        String method = requestRule.method;
        if("GET".equals(method)){
            request.setMethod(Request.Method.GET);
        }else if("POST".equals(method)){
            request.setMethod(Request.Method.POST);
        }else{
            throw new IllegalArgumentException("unsopport mothod"+method);
        }
        return true;
    }

}
