package com.xiaohailuo.webchat.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.xiaohailuo.webchat.service.bean.BaseInfo;

public class TicketInWebChat {
	/*public static void main(String[] args) {
		TicketInWebChat.getTicket();
	}*/

	public static String getTicket() {
		String ticket = null;
		//String access_token = "3uLnIkuFvhex2U30zNiluwGPZIu-ikyWVLLRXwDPRrNrsMbIDGolouJHkNGYtenBRMtf7Yuh8WBw3-JUsdoiYiNBFBI8cliyvsoi1CaRSlx1HjdBuBXqf9EZEeVTL1gEAHZfCGAFME"; // 有效期为7200秒
		String access_token = AccessTokenInWebChat.getAccessToken();
		// https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";
		System.out.println("getticketUrl:" + url);

		// ticket:sM4AOVdWfPE4DxkXGEs8VGn-qh0Uv1cqlJvSAgSvGoq0P2mCCAXPPlUFx9ZqG95MZBFet5djrPMYOR6AREeLqg

		try {
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			JSONObject demoJson = new JSONObject(message);
			System.out.println(demoJson);
			ticket = demoJson.getString("ticket");
			// System.out.println(ticket);
			System.out.println(String.format("%s:%s", "ticket", ticket));
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		//保存jsapi_token相关信息
		BaseInfo.getInstance().setJsapiTicket(ticket);
		
		return ticket;
	}
}
