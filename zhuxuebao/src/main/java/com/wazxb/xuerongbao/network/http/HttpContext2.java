package com.wazxb.xuerongbao.network.http;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * 网络上下文
 * @author sunqitang
 *
 */
public class HttpContext2 {
	protected HttpRequest2 request = new HttpRequest2();
	protected HttpResponse2 response = new HttpResponse2();
	private LinkedList<BdHttpStat> statList = new LinkedList<BdHttpStat>();
	
	public HttpRequest2 getRequest() {
		return request;
	}
	public HttpResponse2 getResponse() {
		return response;
	}
	public List<BdHttpStat> getStatList() {
		return Collections.unmodifiableList(statList);
	}
	
	public void putStat(BdHttpStat stat) {
		if (stat != null) {
			statList.add(stat);
		}
	}
	
	
}
