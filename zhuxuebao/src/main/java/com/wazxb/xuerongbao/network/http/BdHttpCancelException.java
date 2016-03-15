package com.wazxb.xuerongbao.network.http;

import java.io.IOException;

public class BdHttpCancelException extends IOException {
	private static final long serialVersionUID = 6712119810502114101L;

	public BdHttpCancelException() {
		super("request cancelled.");
	}

	public BdHttpCancelException(String detailMessage) {
		super(detailMessage);
	}

	public BdHttpCancelException(String detailMessage, Throwable cause) {
		super(detailMessage);
		this.initCause(cause);
	}

}
