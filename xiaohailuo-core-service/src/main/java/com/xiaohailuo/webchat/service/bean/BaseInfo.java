package com.xiaohailuo.webchat.service.bean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;

public class BaseInfo {
	private BaseInfo(){};
	private static class SingletonHolder {
		private static BaseInfo instance = new BaseInfo();
	}
	public static BaseInfo getInstance(){
		return SingletonHolder.instance;
	}
	
	private String appId = "wxc48e52be91c76a40";
	private String secret = "4694b3d66c5733db3eb50e0f776ac7f3";
	private String accessToken = "";
	private Long expiresIn = 7200L;
	private String jsapiTicket = "";
	
	private static final Map<String, Signature> signatures = new ConcurrentHashMap<String, Signature>();
	//private ConcurrentMap<String, Object> signatureMap = new ConcurrentHashMap<String, Object>();
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	public String getJsapiTicket() {
		return jsapiTicket;
	}
	
	public void setJsapiTicket(String jsapiTicket) {
		this.jsapiTicket = jsapiTicket;
	}
	
	public static Map<String, Signature> getSignatures() {
		return signatures;
	}
	
	static void addSignature(String url, Signature signature){
		signatures.put(url, signature);
	}

	/*@Override
	public String toString() {
		return "BaseInfo [appId=" + appId + ", secret=" + secret + ", accessToken=" + accessToken + ", expiresIn="
				+ expiresIn + ", jsapiTicket=" + jsapiTicket + ", getAppId()=" + getAppId() + ", getSecret()="
				+ getSecret() + ", getAccessToken()=" + getAccessToken() + ", getExpiresIn()=" + getExpiresIn()
				+ ", getJsapiTicket()=" + getJsapiTicket() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}*/

}
