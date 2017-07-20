package com.xiaohailuo.webchat.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class WebChatInfo {
	private static final ConcurrentMap<String, Object> cMap = new ConcurrentHashMap<String, Object>();
	
	private static final String appId = "appId";
	private static final String timestamp = "timestamp";
	private static final String accessToken="accessToken";
	private static final String nonceStr = "nonceStr";
	private static final String signature = "signature";
	
	public static void updateWebChatInfo(Map infoMap) {
		cMap.clear();
		cMap.putAll(infoMap);
	}

	public static ConcurrentMap<String, Object> getCmap() {
		if(cMap.isEmpty()||cMap.size()==0){
			init();
		}
		return cMap;
	}
	
	private static void init(){
		;
	}
}
