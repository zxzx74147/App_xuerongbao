package com.wazxb.xuerongbao.network;

/**
 * Created by zhengxin on 15/9/15.
 */
public class ZXBRequestManageer {
    private static ZXBRequestManageer mInstance = null;

    private ZXBRequestManageer(){
        initRequest();
    }

    public static ZXBRequestManageer sharedInstance(){
        if(mInstance == null){
            mInstance = new ZXBRequestManageer();
        }
        return mInstance;
    }

    private void initRequest(){

    }

    public boolean dealRequest(ZXBHttpRequest request){
        String path = request.getPath();
        String url = NetworkConfig.HOST+path;
        request.setUrl(url);
        return true;
    }

}
