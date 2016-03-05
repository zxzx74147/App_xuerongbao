package com.wazxb.zhuxuebao.network.http;

/**
 * 统计信息
 * 
 * @author tony
 * 
 */
public class BdHttpStat {

	// 统计添加项
	// 上传包大小
	public int upDataSize = -1;
	// 下载资源包
	public int downloadSize = -1;
	// 连接时间
	public long connectTime = -1;
	// 返回时间
	public long rspTime = -1;
	// 执行次数。 比如第一次请求即为1
	public int retry = 0;
	// 总耗时
	public long allCostTime = -1;
	// DNS查找时间
	public long dnsTime = -1;
	// 失败原因
	public String exception = "";
	// 返回值
	public int responsedCode = -1;
	// 执行路径
	public int executeStatus = 0;
	
	public class ExecuteStatus{
		public static final int BEGIN = -1;
		public static final int CREATE_CONN_BEFORE = -2;
		public static final int CREATE_CONN_SUCC = -3;
		public static final int CONN_BEFORE = -4;
		public static final int CONN_SUCC = -5;
		public static final int POST_BEFORE = -6;
		public static final int POST_SUCC = -7;
		public static final int GETDATA_BEFORE = -8;
		public static final int GETDATA_SUCC = -9;
	}
	
}
