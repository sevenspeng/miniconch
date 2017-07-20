package com.xiaohailuo.root;

import java.util.List;

public class Root {
	private int resultCode;

	private String resultMessage;

	private Object value;

	public Root(int resultCode, String resultMessage, Object value) {
		super();
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
		this.value = value;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public int getResultCode() {
		return this.resultCode;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getResultMessage() {
		return this.resultMessage;
	}

	public void setValue(List<Object> value) {
		this.value = value;
	}

	public Object getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return "Root [resultCode=" + resultCode + ", resultMessage=" + resultMessage + ", value=" + value + "]";
	}

}