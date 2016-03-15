package com.wazxb.xuerongbao.network.http;

import org.apache.http.HttpStatus;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * 网络返回值
 * 
 * @author sunqitang
 * 
 */
public class HttpResponse2 {
	/**
	 * 是否被取消或者停止
	 */
	public volatile boolean isCancel = false;

	public int responseCode;
	public String contentEncoding = "";
	public String contentType = "";
	public String contentLength = "";

	public Map<String, List<String>> heads;

	/**
	 * 返回的二进制数据
	 */
	public byte[] retBytes;

	/**
	 * 文件是否分片成功
	 * 
	 * @return
	 */
	public boolean isFileSegSuccess() {
		if (responseCode != HttpStatus.SC_OK
				&& responseCode != HttpStatus.SC_PARTIAL_CONTENT) {
			return false;
		} else {
			return true;
		}
	}

	protected void getResponseHead(HttpURLConnection mConn) throws Exception {
		if (mConn != null) {
			responseCode = mConn.getResponseCode();
			contentEncoding = mConn.getContentEncoding();
			contentType = mConn.getContentType();
			heads = mConn.getHeaderFields();
		}
	}

	public boolean isNetOK() {
		// 200和3xx代表成功
		if (responseCode == HttpStatus.SC_OK || responseCode / 100 == 3) {
			return true;
		} else {
			return false;
		}
	}

}
