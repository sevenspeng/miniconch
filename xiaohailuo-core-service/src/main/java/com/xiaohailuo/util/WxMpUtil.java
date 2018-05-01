package com.xiaohailuo.util;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import com.google.gson.Gson;
import com.xiaohailuo.domain.WXToken;
import com.xiaohailuo.domain.WXuserinfo;

public class WxMpUtil {

	final static String appid = "wxb2c12f0f855920ea";
	final static String secret = "216bafcf6de291ca1f99160a6bc6867c";
	final static Gson gson = new Gson();

	public static WXToken getAccessToken(String code) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + secret + "&code="
				+ code + "&grant_type=authorization_code";
		try {
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse response = httpClient.execute(httpGet);
			String resultContent = new BasicResponseHandler().handleResponse(response);
			WXToken wxToken = gson.fromJson(resultContent, WXToken.class);
			return wxToken;

		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public static WXuserinfo getUserinfo(WXToken wxToken) {
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + wxToken.getAccess_token() + "&openid="
				+ wxToken.getOpenid() + "&lang=zh_CN";
		try {
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse response = httpClient.execute(httpGet);
			String resultContent = new BasicResponseHandler().handleResponse(response);
			resultContent = new String(resultContent.getBytes("ISO-8859-1"), "UTF-8");
			WXuserinfo wxuserinfo = gson.fromJson(resultContent, WXuserinfo.class);
			return wxuserinfo;

		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
