package com.xiaohailuo.webchat.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.xiaohailuo.webchat.service.bean.BaseInfo;

public class AccessTokenInWebChat {
	public static final String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
	//测试
	//public static final String APP_ID = "wxa549b28c24cf341e";
	//public static final String SECRET = "78d8a8cd7a4fa700142d06b96bf44a37";
	//xiaohailuo
	/*public static final String APP_ID = "wxa7efceb860cbfa5b";
	public static final String SECRET = "1e3a0ad362a6b80eb390da3bb31084cf";*/
	public static final String APP_ID = "wxc48e52be91c76a40";
	public static final String SECRET = "4694b3d66c5733db3eb50e0f776ac7f3";
	public static String current_AccessToken = "";
	

	public static String /*void*/ getAccessToken() {
		System.out.println("========================获取token开始==========================");

		String url = String.format("%s?grant_type=client_credential&appid=%s&secret=%s", GET_TOKEN_URL, APP_ID, SECRET);
		String accessToken = null;
		String expiresIn = null;

		try {
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET");
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);

			http.connect();

			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);

			String message = new String(jsonBytes, "UTF-8");
			
			JSONObject demoJson = new JSONObject(message);
			System.out.println(demoJson);
			accessToken = demoJson.getString("access_token");
			current_AccessToken = accessToken;
			//expiresIn = demoJson.getString("expires_in");
			expiresIn = String.valueOf(demoJson.getInt("expires_in"));

			System.out.println("accessToken====" + accessToken);
			System.out.println("expiresIn===" + expiresIn);
			System.out.println("========================获取token结束==========================");

			is.close();
			
			//writeDB(accessToken, expiresIn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//保存access_token相关信息
		BaseInfo.getInstance().setAccessToken(accessToken);
		BaseInfo.getInstance().setAppId(APP_ID);
		BaseInfo.getInstance().setSecret(SECRET);

		return accessToken;

	}
	
	/*public static void main(String[] args) {
		AccessTokenInWebChat getWeiXinAccessToken = new AccessTokenInWebChat();
		getWeiXinAccessToken.getAccessToken();
	}*/
	
}