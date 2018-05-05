package com.xiaohailuo.web;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.xiaohailuo.domain.UserMapper;
import com.xiaohailuo.domain.WXToken;
import com.xiaohailuo.domain.WXuserinfo;
import com.xiaohailuo.util.SystemConst;
import com.xiaohailuo.util.WxMpUtil;

@RestController
@RequestMapping(value = "/weixin")
public class LoginController {

	@Autowired
	private UserMapper userMapper;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response) {
		// 接口文档https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842
		// https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb2c12f0f855920ea&redirect_uri=http%3a%2f%2fwww.miniconch.cn%2fxiaohailuo%2fweixin%2flogin&response_type=code&scope=snsapi_userinfo&state=http%3a%2f%2fwww.miniconch.cn%2fxiaohailuoWeb%2findex.html#wechat_redirect
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String code = request.getParameter("code");
			String state = request.getParameter("state");
			// 第二步：通过code换取网页授权access_token
			WXToken wxToken = WxMpUtil.getAccessToken(code);
			// 第三步：刷新access_token（如果需要）
			// 第四步：拉取用户信息(需scope为 snsapi_userinfo)
			WXuserinfo wxuserinfo = WxMpUtil.getUserinfo(wxToken);
			if (null == wxuserinfo.getOpenid() || "".equals(wxuserinfo.getOpenid())) {
				map.put("resultCode", SystemConst.NOT_FOUND);
				map.put("resultMessage", SystemConst.NOT_FOUND_MESSAGE);
			}
			// 更新用户信息
			int result = userMapper.updateWXuserinfo(wxuserinfo);
			// 新增用户
			if (result == 0)
				userMapper.addUser(wxuserinfo);
			map.put("resultCode", SystemConst.SUCCESS);
			map.put("resultMessage", SystemConst.SUCCESS_MESSAGE);
			map.put("uid", wxuserinfo.getOpenid());
			String url = URLDecoder.decode(state, "utf-8") + "?uid=" + wxuserinfo.getOpenid();
			response.sendRedirect(url);

		} catch (Exception e) {
			map.put("resultCode", SystemConst.ERROR);
			map.put("resultMessage", SystemConst.ERROR_MESSAGE);
		}
		return map;
	}

}
