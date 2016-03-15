package com.wazxb.xuerongbao.network.http;

import com.zxzx74147.devlib.utils.BdLog;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * HttpResponsedMessage
 *
 * @author zhaolin02
 */
public class HttpResponsedMessage {
    private int mStatusCode = -1;
    private Map<String, List<String>> mHeader = null;
    private String contentEncoding = "";
    private String contentType = "";
    private String contentLength = "";
    public String mRet = null;


    public String getContentEncoding() {
        return contentEncoding;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentLength() {
        return contentLength;
    }

    public void setContentLength(String contentLength) {
        this.contentLength = contentLength;
    }

    /**
     * 添加http response的协议头
     */
    public synchronized void setHeader(Map<String, List<String>> heads) {
        mHeader = heads;
    }

    /**
     * 获取协议头
     *
     * @param key
     * @return
     */
    public synchronized List<String> getHeader(String key) {
        if (mHeader != null) {
            return Collections.unmodifiableList(mHeader.get(key));
        } else {
            return null;
        }
    }

    /**
     * 是否联网成功
     *
     * @return
     */
    public boolean isSuccess() {
        // 200和3xx代表成功
        if (mStatusCode == 200 || mStatusCode / 100 == 3) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasError() {
        return !isSuccess();
    }

    static private final int BUFFERSIZE = 1024;

    /**
     * 对其进行GZIP解压缩
     *
     * @param data
     * @return
     * @throws Exception
     */
    protected final byte[] decodeByGZIP(byte[] data) throws Exception {
        byte[] src = data;
        if (getContentEncoding() != null &&
                getContentEncoding().toLowerCase().contains("gzip")) {
            ByteArrayInputStream tmpInput = new ByteArrayInputStream(src);
            ByteArrayOutputStream tmpOutput = new ByteArrayOutputStream(BUFFERSIZE);
            BdGzipHelper.decompress(tmpInput, tmpOutput);
            return tmpOutput.toByteArray();
        } else {
            return src;
        }
    }

    /**
     * 获取status code
     *
     * @return
     */
    public int getStatusCode() {
        return mStatusCode;
    }

    /**
     * 设置status code
     *
     * @param statusCode
     * @param error
     */
    public void setStatusCode(int statusCode, String error) {
        this.mStatusCode = statusCode;
        if (isSuccess() == false) {
        }
    }

    public final void decodeInBackGround(byte[] data) {

        try {
            data = decodeByGZIP(data);
            mRet = new String(data, getCharset());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 逻辑层的最好实现该方法。 来处理业务逻辑
     *
     * @param cmd
     * @param retJson 有可能为空，业务需要判空处理
     */
    public void decodeLogicInBackGround(int cmd, JSONObject retJson) throws Exception {

    }

    /**
     * 解析服务器错误码
     */
    private JSONObject parseServerCode(String data) {
        JSONObject json = null;
        try {
            if (data != null) {
                json = new JSONObject(data);

            }
        } catch (Exception ex) {
            BdLog.e("NetWork", "parseServerCode",
                    "error = " + ex.getMessage());

        }
        return json;
    }

    /**
     * 读取服务器返回的数据编码格式
     *
     * @return
     * @throws Exception
     */
    private String getCharset() throws Exception {
        String type = getContentType();
        String charset = "utf-8";
        if (type != null) {
            int index = type.indexOf("charset");
            if (index != -1) {
                int end = type.indexOf(' ', index);
                if (end == -1) {
                    charset = type.substring(index + 8);
                } else {
                    charset = type.substring(index + 8, end);
                }
            }
        }
        return charset;
    }


}
