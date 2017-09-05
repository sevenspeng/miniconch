package com.xiaohailuo.webchat.service;

import java.util.Arrays;

import com.xiaohailuo.webchat.service.bean.BaseInfo;
import com.xiaohailuo.webchat.service.bean.Signature;
import com.xiaohailuo.webchat.util.Decript;
import com.xiaohailuo.webchat.util.StringUtils;
import com.xiaohailuo.webchat.util.TimestampUtils;

public class SignatureInWebChat {
//	private String signature = "signature";
//	private long timestamp;
//	private String nonce = "nonce";
//	private String echostr = "echostr";
//	String token = "3uLnIkuFvhex2U30zNiluwGPZIu-ikyWVLLRXwDPRrNrsMbIDGolouJHkNGYtenBRMtf7Yuh8WBw3-JUsdoiYiNBFBI8cliyvsoi1CaRSlx1HjdBuBXqf9EZEeVTL1gEAHZfCGAFME"; // 有效期为7200秒

	/*public static void main(String[] args) {
		//new SignatureInWebChat().signatureIn();
		//new SignatureInWebChat().signatureIn("http://mp.weixin.qq.com?params=value");
		SignatureInWebChat.getInstance().signatureIn("http://mp.weixin.qq.com?params=value");
		BaseInfo baseInfo = BaseInfo.getInstance();
		System.out.println("baseIno:\n" + baseInfo.toString());
		System.out.println("BaseInfo:\n" + BaseInfo.getSignatures());
		
	}*/
	
	private SignatureInWebChat(){}
	private static class SingletonHolder {
		private static final SignatureInWebChat instance = new SignatureInWebChat();
	}
	public static SignatureInWebChat getInstance(){
		return SingletonHolder.instance;
	}
	
	
	//public /*void*/String signatureIn() {
	public String signatureIn(String url) {
		System.out.println("开始生成签名");
		System.out.println("待签名的URL：" + url);
		//System.out.println("开始签名校验");
		/*String signature = getSignature();
		String timestamp = String.valueOf(getTimestamp());
		String nonce = getNonce();
		String echostr = getEchostr();*/
		String signatureOrigin = "0f9de62fce790f9a083d5c99e95740ceb90c27ed";
		
		/*String timestamp = "1414587457";
		String nonce = "Wm3WZYTPz0wzccnW";
		String jsapi_ticket = "sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg";
		String url = "http://mp.weixin.qq.com?params=value";*/
		String timestamp = String.valueOf(TimestampUtils.getTimestampInMilliseconds());
		String nonceStr = StringUtils.getNonceStr();
		String jsapi_ticket = TicketInWebChat.getTicket();
		//String url = "http://mp.weixin.qq.com?params=value";

		/*ArrayList<String> array = new ArrayList<String>();
		array.add(signature);
		array.add(timestamp);
		array.add(nonce);*/

		// 排序
		//String sortString = sort("url=http://mp.weixin.qq.com?params=value", "noncestr=Wm3WZYTPz0wzccnW",  "timestamp=1414587457", "jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg");
		String sortString = sort("url=" + url, "noncestr=" + nonceStr,  "timestamp=" + timestamp, "jsapi_ticket=" + jsapi_ticket);
		System.out.println("sortString:" + sortString);
		
		// 加密
		String signature = Decript.SHA1(sortString);
		System.out.println("signature:" + signature);
		// 校验签名
		/*if (signature != null && signature != "" && signature.equals(signatureOrigin)) {
			System.out.println("签名校验通过。");
			// response.getWriter().println(echostr);
			// //如果检验成功输出echostr，微信服务器接收到此输出，才会确认检验完成。
		} else {
			System.out.println("签名校验失败。");
		}*/
		System.out.println("签名已经生成");
		
		new Signature(AccessTokenInWebChat.APP_ID, Long.parseLong(timestamp), nonceStr, signature, url);
		
		return signature;
	}

	/**
	 * 排序方法
	 * 
	 * @param token
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static String sort(String token, String timestamp, String nonce) {
		String[] strArray = { token, timestamp, nonce };
		Arrays.sort(strArray);

		StringBuilder sbuilder = new StringBuilder();
		for (String str : strArray) {
			sbuilder.append(str);
		}

		return sbuilder.toString();
	}

	/*public static String sort(String jsapi_ticket, String noncestr, String timestamp, String url) {
		String[] strArray = {jsapi_ticket, timestamp, url, noncestr};
		Arrays.sort(strArray);

		StringBuilder sbuilder = new StringBuilder();
		int i = 0;
		for (String str : strArray) {
			++i;
			if (i == strArray.length) {
				sbuilder.append(str);
			} else {
				sbuilder.append(str).append("&");
			}
		}

		return sbuilder.toString();
	}*/
	
	public static String sort(String...args) {
		//String[] strArray = new String[args.length];
		/*for(int i = 0; i < args.length; i++){
			strArray[i] = args[i];
		}*/
		/*for (int i = 0, j = args.length; i < j; i++) {
			strArray[i] = args[i];
		}*/
		int i = 0;
		/*for(String str:args){
			strArray[i] = args[i];
		}*/
		String[] strArray = args;
		Arrays.sort(strArray);

		StringBuilder sbuilder = new StringBuilder();
		i=0;
		for (String str : strArray) {
			++i;
			if (i == strArray.length) {
				sbuilder.append(str);
			} else {
				sbuilder.append(str).append("&");
			}
		}
		System.out.println("sortResult: " + sbuilder.toString());

		return sbuilder.toString();
	}

}
