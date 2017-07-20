package com.xiaohailuo.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiaohailuo.webchat.service.SignatureInWebChat;
import com.xiaohailuo.webchat.service.bean.BaseInfo;
import com.xiaohailuo.webchat.service.bean.Signature;

@RestController
@RequestMapping("/webchat")
public class WebChatController {

	@RequestMapping(value = "/get/info/signature/base1", method = RequestMethod.POST)
	public /*Map<String, Object>*/BaseInfo getSignatureInWebChat1(@RequestBody Map<String, String> requestMap) {
		
		String url = requestMap.get("url");
		SignatureInWebChat.getInstance().signatureIn(url);
		BaseInfo baseInfo = BaseInfo.getInstance();
		
		/*Map<String,Object> retMap = new HashMap<String,Object>();
		
		retMap.put("resultCode", 200);
		retMap.put("resultMessage", "接口调用正常返回");
		retMap.put("value", WebChatInfo.getCmap());
		
		return retMap;*/
		return baseInfo;
	}
	
	@RequestMapping(value = "/get/info/signature/base2", method = RequestMethod.POST)
	public Signature getSignatureInWebChat2(@RequestBody Map<String, String> requestMap) {
		
		String url = requestMap.get("url");
		SignatureInWebChat.getInstance().signatureIn(url);
		Signature signature = BaseInfo.getSignatures().get(url);
		
		return signature;
	}
	
	@RequestMapping(value = "/get/info/signature", method = RequestMethod.POST)
	public Map<String, Object> getSignatureInWebChat(@RequestBody Map<String, String> requestMap) {
		
		String url = requestMap.get("url");
		
		SignatureInWebChat.getInstance().signatureIn(url);
		Signature signature = BaseInfo.getSignatures().get(url);
		
		Map<String,Object> retMap = new HashMap<String,Object>();
		
		retMap.put("resultCode", 200);
		retMap.put("resultMessage", "接口调用正常返回");
		retMap.put("value", signature);
		
		return retMap;
	}
}
