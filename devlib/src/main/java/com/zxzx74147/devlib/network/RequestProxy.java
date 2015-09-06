package com.zxzx74147.devlib.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengxin on 15/9/6.
 */
public class RequestProxy extends JsonRequest<String> {
    private static Map<String ,String> mHeaders;

    static {
        mHeaders = new HashMap<>();
        mHeaders.put("User-agent", "qiushibalke_8.0.2_WIFI_auto_19");
        mHeaders.put("Source","android_8.0.2");
        mHeaders.put("Model","generic/vbox86p/vbox86p:4.2.2/JDQ39E/eng.buildbot.20150216.211440:userdebug/test-keys");
        mHeaders.put("Uuid","IMEI_fde93765dbc7d317c905484d7c4cea83");
        mHeaders.put("Deviceidinfo","{\"DEVICEID\":\"000000000000000\",\"RANDOM\":\"\",\"ANDROID_ID\":\"6f6e94db97b7b7c5\",\"SIMNO\":\"89014103211118510720\",\"IMSI\":\"310260000000000\",\"SERIAL\":\"\",\"MAC\":\"08:00:27:7d:fa:1d\",\"SDK_INT\":17}");
        mHeaders.put("Host","m2.qiushibaike.com");
    }

    public RequestProxy(int method, String url, String requestBody, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);

    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(jsonString,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (Exception je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if(mHeaders == null){
            return super.getHeaders();
        }
        return mHeaders;
    }
}
