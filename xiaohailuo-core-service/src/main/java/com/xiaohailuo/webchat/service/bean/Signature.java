package com.xiaohailuo.webchat.service.bean;

public class Signature {

	private String appId = "";
	private Long timestamp = 1414587457L;
	private String nonceStr = "Wm3WZY_Pz0wz9cnW";
	private String signature = "0f9de62fce790f9a083d5c99e95740ceb90c27ed";
	private String url = "";
	
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public Signature(String appId, Long timestamp, String nonceStr, String signature, String url) {
		super();
		this.appId = appId;
		this.timestamp = timestamp;
		this.nonceStr = nonceStr;
		this.signature = signature;
		this.url = url;
		
		BaseInfo.addSignature(this.url, this);
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "Signature [appId=" + appId + ", timestamp=" + timestamp + ", nonceStr=" + nonceStr + ", signature="
				+ signature + ", url=" + url + "]";
	}
	
}
