package com.zxzx74147.devlib.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.http.HttpEntity;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhengxin on 15/9/6.
 */
public class UplaodRequestProxy extends Request<String> {

    protected static final String PROTOCOL_CHARSET = "utf-8";
    private LinkedHashMap<String, Object> mParams;
    private HttpEntity mEntity;

    private Response.Listener<String> mListener;


    public UplaodRequestProxy(int method, String url, LinkedHashMap<String, Object> params, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
        mParams = params;
        buildMultipartEntity();
    }

    private void buildMultipartEntity() {

        mEntity = ZXNetworkUtil.fillKVParamData(mParams);
    }

    @Override
    public String getBodyContentType() {
        return mEntity.getContentType().getValue();
    }

    public static byte[] fillKVParamData(HashMap<String, Object> params) {
        if (params == null) {
            return null;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            for (Map.Entry<String, Object> item : params.entrySet()) {
                if (bos.size() != 0) {
                    bos.write('&');
                }
                bos.write(item.getKey().getBytes());
                bos.write('=');
                if (item.getValue() instanceof byte[]) {
                    bos.write((byte[]) item.getValue());
                } else {
                    bos.write(item.getValue().toString().getBytes());
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return fillKVParamData(mParams);
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
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }
}
