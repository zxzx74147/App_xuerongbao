package com.wazxb.xuerongbao.network.http;

public class BdNetWorkError {
	/**
	 * 网络请求，未知异常
	 */
	public static final int NETWORK_UNKNOWN = -10;
	/**
	 * 网络数据获取（图片），超过2M拒绝
	 */
	public static final int NETWORK_DATA_TOO_BIG = -11;
	/**
	 * 网络请求，捕获java.net.SocketException
	 */
	public static final int NETWORK_SOCKET_EXCEPTION = -12;
	/**
	 * 网络请求，捕获java.net.SocketTimeoutException
	 */
	public static final int NETWORK_SOCKET_TIMEOUT = -13;
	/**
	 * 网络请求，被取消
	 */
	public static final int NETWORK_CANCEL = -14;
	/**
	 * 网络请求，内存溢出
	 */
	public static final int NETWORK_OUTOFMEMORY = -15;
	/**
	 * 网络请求，动态的可用内存不足
	 */
	public static final int NETWORK_MEMORY_SMALL = -16;

	/**
	 * 没有下载到可用的内容
	 */
	public static final int NETWORK_NO_DATA_RECEIVED = -17;
	
	/**
	 * 文件找不到
	 */
	public static final int FILE_NOT_FOUND = -100;
}
