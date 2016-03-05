package com.wazxb.zhuxuebao.network.http;

import com.zxzx74147.devlib.utils.BdLog;

import org.apache.http.message.BasicNameValuePair;

import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 网络请求信息
 * 
 * @author sunqitang
 * 
 */
public class HttpRequest2 {
	private String url = "";

	public static enum HTTP_METHOD {
		GET, POST
	};

	private HTTP_METHOD method;
	/**
	 * HTTP头信息
	 */
	protected Map<String, String> headData = new HashMap<String, String>();
	/**
	 * String参数
	 */
	protected LinkedList<BasicNameValuePair> postData = new LinkedList<BasicNameValuePair>();
	/**
	 * byte参数
	 */
	protected HashMap<String, byte[]> byteData = new HashMap<String, byte[]>();
	private static final int BUFFERSIZE = 1024;
	private static final String end = "\r\n";
	private static final String twoHypens = "--";

	public HTTP_METHOD getMethod() {
		return method;
	}

	public void setMethod(HTTP_METHOD method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		if (url == null) {
			this.url = "";
		} else {
			this.url = url;
		}
	}

	public boolean hasRaw() {
		if (byteData != null && byteData.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 拼装Get信息
	 * 
	 * @param stat
	 *
	 */
	String generateGetString(BdHttpStat stat) {
		StringBuilder buffer = new StringBuilder(30);
		buffer.append(url);
		if (url.indexOf("?") < 0) {
			buffer.append("?");
		} else if (!url.endsWith("?")
				&& !url.endsWith("&")) {
			buffer.append("&");
		}
		for (int i = 0; i < postData.size(); i++) {
			if (i != 0) {
				buffer.append("&");
			}
			buffer.append(postData.get(i).getName());
			buffer.append("=");
			buffer.append(getUrlEncode(postData.get(i)
					.getValue()));
		}

		if (stat != null) {
			stat.upDataSize = buffer.length();
		}

		return buffer.toString();
	}

	/**
	 * 部属Head信息
	 * 
	 * @param connection
	 */
	void wrapHead(HttpURLConnection connection) {
		if (connection != null && headData != null) {
			for (Entry<String, String> pair : headData.entrySet()) {
				connection.addRequestProperty(pair.getKey(), pair.getValue());
			}
		}
	}

	/**
	 * 部属二进制信息,以multipart/form-data格式post网络数据
	 * 
	 * @param connection
	 * @param boundary
	 * @return
	 * @throws Exception
	 */
	void wrapPost2Conn(HttpURLConnection connection, String boundary, BdHttpStat stat) throws Exception {
		preDeplyMutliPost2Conn();
		int length = 0;
		if (connection != null) {
			DataOutputStream ds = new DataOutputStream(connection.getOutputStream());
			;
			try {
				if (postData != null) {
					for (BasicNameValuePair pair : postData) {
						if (pair == null) {
							continue;
						}
						String k = pair.getName();
						String v = pair.getValue();
						ds.writeBytes(twoHypens + boundary + end);
						byte[] vbuffer = v.getBytes("UTF-8");
						ds.writeBytes("Content-Disposition: form-data; name=\"" + k
								+ "\"" + end);
						ds.writeBytes(end);
						ds.write(vbuffer);
						ds.writeBytes(end);
					}
				}

				if (byteData != null) {
					for (Entry<String, byte[]> entry : byteData.entrySet()) {
						String k = entry.getKey();
						byte[] v = entry.getValue();
						if (v == null) {
							continue;
						}
						ds.writeBytes(twoHypens + boundary + end);
						ds.writeBytes("Content-Disposition: form-data; name=\""
								+ k + "\"; filename=\"file\"" + end);
						ds.writeBytes(end);
						ds.write(v);
						ds.writeBytes(end);
					}
				}

				ds.writeBytes(twoHypens + boundary + twoHypens + end);
				ds.flush();
				length = ds.size();
			} finally {
				BdCloseHelper.close(ds);
			}

		}
		if (stat != null) {
			stat.upDataSize = length;
		}
	}

	/**
	 * 部属Post信息
	 * 
	 * @param connection
	 * @return
	 * @throws Exception
	 */
	public void wrapPost2Conn(HttpURLConnection connection, BdHttpStat stat) throws Exception {
		int length = 0;
		if (connection != null) {
			String postData = postData2String().toString();

			DataOutputStream ds = new DataOutputStream(
					connection.getOutputStream());

			BdLog.i("POST:" + url + "?" + postData);

			try {
				ds.writeBytes(postData);
				ds.flush();
			} finally {
				BdCloseHelper.close(ds);
			}

			length = postData.length();
		}

		if (stat != null) {
			stat.upDataSize = length;
		}
	}

	/**
	 * 把Post信息拼装成String
	 * 
	 * @return
	 */
	private StringBuilder postData2String() {
		StringBuilder build = new StringBuilder(BUFFERSIZE);
		if (postData != null) {
			int i = 0;
			for (BasicNameValuePair pair : postData) {
				if (pair == null) {
					continue;
				}
				String k = pair.getName();
				String v = pair.getValue();
				if (i != 0) {
					build.append("&");
				}
				build.append(k + "=");
				build.append(getUrlEncode(v));
				i++;
			}
		}
		return build;
	}

	/**
	 * 提前加工Post数据
	 */
	protected void preDeplyMutliPost2Conn() {
	}

	public void setHeadData(HashMap<String, String> head) {
		headData = head;
	}

	public String getHeadData(String key) {
		if (headData != null) {
			return headData.get(key);
		} else {
			return null;
		}
	}

	public void setPostData(List<Entry<String, Object>> data) {
		if (data != null) {
			for (Entry<String, Object> entry : data) {
				Object ob = entry.getValue();
				if (ob == null) {
					continue;
				}
				if (ob instanceof String) {
					postData.add(new BasicNameValuePair(entry.getKey(), (String) entry.getValue()));
				} else if (ob instanceof byte[]) {
					byteData.put(entry.getKey(), (byte[]) entry.getValue());
				} else {
					throw new UnsupportedOperationException("post type is not String and byte[]");
				}
			}
		}
	}

	public void addPostData(String key, byte[] value) {
		byteData.put(key, value);
	}

	public void addPostData(String key, String value) {
		postData.add(new BasicNameValuePair(key, value));
	}

	public void addHeadData(String string, String host) {
		if (headData != null) {
			headData.put(string, host);

		}
	}

	/**
	 * 获取URLencode编码
	 *
	 * @param s
	 * @return
	 */
	public static String getUrlEncode(String s) {
		if (s == null) {
			return null;
		}
		String result = "";
		try {
			result = URLEncoder.encode(s, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
