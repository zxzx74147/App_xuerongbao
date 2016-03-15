package com.wazxb.xuerongbao.network.http;

import android.os.Handler;

import com.zxzx74147.devlib.utils.BdLog;

import org.apache.http.HttpStatus;

import java.io.FileNotFoundException;

/**
 * 网络请求
 * 
 * @author sunqitang
 * 
 */
public class BdHttpManager2 {

	private HttpContext2 context;

	public BdHttpManager2(HttpContext2 context) {
		this.context = context;
	}


	/**
	 * GET方式请求
	 * 
	 * @param readTimeout
	 * @param retrycount
	 */
	public void get(int retrycount, int readTimeout, int connTiemout) {

		boolean is_net_error = true;
		long time = System.currentTimeMillis();
		for (int retry = 0; context.getResponse().isCancel == false && is_net_error == true
				&& retry < retrycount; retry++) {
			BdHttpStat stat = new BdHttpStat();
			try {
				stat.retry = retry + 1;

				BdHttpImpl2 httpImpl = new BdHttpImpl2(context);
				httpImpl.getNetData(readTimeout, connTiemout, stat);
				if (context.getResponse().responseCode != HttpStatus.SC_OK) {
					stat.exception = String.valueOf(context.getResponse().responseCode)+ "|retryCount:"+ retry;
					is_net_error = isNeedRetry(context.getResponse().responseCode);
					context.putStat(stat);
					continue;
				}

				break;
			} catch (java.net.SocketException ex) {
				stat.exception = String
						.valueOf(context.getResponse().responseCode)
						+ "|retryCount:"
						+ retry + "|" + ex.getClass() + "|" + ex.getMessage();
				context.getResponse().responseCode = BdNetWorkError.NETWORK_SOCKET_EXCEPTION;
				is_net_error = true;
				context.putStat(stat);
			} catch (java.net.SocketTimeoutException ex) {
				stat.exception = String
						.valueOf(context.getResponse().responseCode)
						+ "|retryCount:"
						+ retry + "|" + ex.getClass() + "|" + ex.getMessage();
				context.getResponse().responseCode = BdNetWorkError.NETWORK_SOCKET_TIMEOUT;
				is_net_error = true;
				context.putStat(stat);
			} catch (Exception ex) {
				stat.exception = String
						.valueOf(context.getResponse().responseCode)
						+ "|retryCount:"
						+ retry + "|" + ex.getClass() + "|" + ex.getMessage();
				context.getResponse().responseCode = BdNetWorkError.NETWORK_UNKNOWN;
				is_net_error = false;
				BdLog.e(getClass().getName(), "getNetData",
						"error = " + ex.getMessage());
				context.putStat(stat);
				break;
			} finally {
				stat.allCostTime = System.currentTimeMillis() - time;
				context.putStat(stat);
			}
		}

	}

	/**
	 * 上传二进制使用
	 * 
	 * @param readTimeout
	 * @param retrycount
	 */
	private void postRawNetData(int retrycount, int readTimeout, int connTiemout) {


		long time = System.currentTimeMillis();
		boolean is_net_error = true;
		for (int retry = 0; context.getResponse().isCancel == false && is_net_error == true
				&& retry < retrycount; retry++) {
			BdHttpStat stat = new BdHttpStat();
			stat.retry = retry + 1;
			try {
				BdHttpImpl2 httpImpl = new BdHttpImpl2(context);
				httpImpl.postBytesNetData(readTimeout, connTiemout, stat);

				
				if (context.getResponse().responseCode != HttpStatus.SC_OK) {
					stat.exception = String.valueOf(context.getResponse().responseCode)+ "|retryCount:"+ retry;
					is_net_error = isNeedRetry(context.getResponse().responseCode);
					context.putStat(stat);
					continue;
				}

				break;
			} catch (java.net.SocketException ex) {
				context.getResponse().responseCode = BdNetWorkError.NETWORK_SOCKET_EXCEPTION;
				is_net_error = true;
				stat.exception = "网络失败";
				BdLog.e(getClass().getName(), "postNetData",
						"SocketException " + ex.getMessage());
				context.putStat(stat);
			} catch (java.net.SocketTimeoutException ex) {
				context.getResponse().responseCode = BdNetWorkError.NETWORK_SOCKET_TIMEOUT;
				is_net_error = true;
				stat.exception = "网络失败";
				BdLog.e(getClass().getName(), "postNetData",
						"SocketTimeoutException " + ex.getMessage());
				context.putStat(stat);
			} catch (UnsupportedOperationException ex) {
				is_net_error = false;
				context.getResponse().responseCode = BdNetWorkError.NETWORK_CANCEL;
				stat.exception =  "网络失败";
				context.putStat(stat);
			} catch (Throwable ex) {
				context.getResponse().responseCode = BdNetWorkError.NETWORK_UNKNOWN;
				is_net_error = false;
				stat.exception =  "网络失败";
				BdLog.e(getClass().getName(), "postNetData", ex.getMessage());
				context.putStat(stat);
			} finally {
				stat.allCostTime = System.currentTimeMillis() - time;
				context.putStat(stat);
			}
		}
	}

	/**
	 * 是否需要重练
	 * @param responseCode
	 * @return
	 */
	private boolean isNeedRetry(int responseCode) {
		if (responseCode == java.net.HttpURLConnection.HTTP_ACCEPTED
				|| responseCode == java.net.HttpURLConnection.HTTP_CREATED
				|| responseCode == java.net.HttpURLConnection.HTTP_RESET
				|| responseCode == java.net.HttpURLConnection.HTTP_NOT_MODIFIED
				|| responseCode == java.net.HttpURLConnection.HTTP_USE_PROXY
				|| responseCode == java.net.HttpURLConnection.HTTP_CLIENT_TIMEOUT) {
			return true;
		} else if (responseCode == java.net.HttpURLConnection.HTTP_BAD_GATEWAY
				|| responseCode == java.net.HttpURLConnection.HTTP_UNAVAILABLE
				|| responseCode == java.net.HttpURLConnection.HTTP_GATEWAY_TIMEOUT) {
			return false;
		}
		return true;
	}


	public void post(int retrycount, int readTimeout, int connTiemout) {
		if (context.getRequest().hasRaw()) {
			postRawNetData(retrycount, readTimeout, connTiemout);
		} else {
			postNetData(retrycount, readTimeout, connTiemout);
		}
	}

	/**
	 * POST方式请求
	 * 
	 * @param readTimeout
	 * @param retrycount
	 */
	private void postNetData(int retrycount, int readTimeout, int connTiemout) {


		long time = System.currentTimeMillis();
		boolean is_net_error = true;
		for (int retry = 0; context.getResponse().isCancel == false && is_net_error == true
				&& retry < retrycount; retry++) {
			BdHttpStat stat = new BdHttpStat();
			stat.retry = retry + 1;
			try {
				BdHttpImpl2 httpImpl = new BdHttpImpl2(context);
				httpImpl.postNetData(readTimeout, connTiemout, stat);
				
				if (context.getResponse().responseCode != HttpStatus.SC_OK) {
					stat.exception = String.valueOf(context.getResponse().responseCode)+ "|retryCount:"+ retry;
					is_net_error = isNeedRetry(context.getResponse().responseCode);
					context.putStat(stat);
					continue;
				}
				
				break;
			} catch (java.net.SocketException ex) {
				context.getResponse().responseCode = BdNetWorkError.NETWORK_SOCKET_EXCEPTION;
				is_net_error = true;
				stat.exception =  "网络失败";
				BdLog.e(getClass().getName(), "postNetData",
						"SocketException " + ex.getMessage());
				context.putStat(stat);
			} catch (java.net.SocketTimeoutException ex) {
				context.getResponse().responseCode = BdNetWorkError.NETWORK_SOCKET_TIMEOUT;
				is_net_error = true;
				stat.exception =  "网络失败";
				BdLog.e(getClass().getName(), "postNetData",
						"SocketTimeoutException " + ex.getMessage());
				context.putStat(stat);
			} catch (UnsupportedOperationException ex) {
				is_net_error = false;
				context.getResponse().responseCode = BdNetWorkError.NETWORK_CANCEL;
				stat.exception =  "网络失败";
				context.putStat(stat);
			} catch (Throwable ex) {
				context.getResponse().responseCode = BdNetWorkError.NETWORK_UNKNOWN;
				is_net_error = false;
				stat.exception = "网络失败";
				BdLog.e(getClass().getName(), "postNetData", ex.getMessage());
				context.putStat(stat);
			} finally {
				stat.allCostTime = System.currentTimeMillis() - time;
				context.putStat(stat);
			}
		}
	}

	/**
	 * 下载文件
	 *
	 * @param path
	 *            保存文件的全路径
	 * @return true：成功； false：失败
	 */
	public boolean downloadFile(String name, final Handler handler, int what, int maxRetryCount, int readTimeout,
								int connTimeout) {

		boolean ret = false;
		BdHttpImpl2 impl2 = new BdHttpImpl2(context);
		for (int i = 0; i < maxRetryCount; i++) {
			try {
				ret = impl2.downloadFile(name, handler, what, readTimeout, connTimeout);
				break;
			} catch (FileNotFoundException ex) {
				context.getResponse().responseCode = BdNetWorkError.FILE_NOT_FOUND;
			} catch (Exception ex) {
				context.getResponse().responseCode = BdNetWorkError.NETWORK_UNKNOWN;
				BdLog.e("NetWork", "downloadFile", "error = " + ex.getMessage());
			}
		}
		return ret;
	}

}