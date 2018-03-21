package com.xiaohailuo;


import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thetransactioncompany.cors.CORSFilter;

@Configuration
public class FilterConfiguration {

	@Bean
	public FilterRegistrationBean filterDemo4Registration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		// 注入过滤器
		registration.setFilter(new CORSFilter());
		// 拦截规则
		registration.addUrlPatterns("/*");
		// 过滤器名称
		registration.setName("CORS");
		
		Map<String, String> initParameters=new HashMap<String, String>();
		initParameters.put("cors.allowOrigin", "*");
		initParameters.put("cors.supportedMethods", "GET, POST, HEAD, PUT, DELETE");
		initParameters.put("cors.supportedHeaders", "Accept, Origin, XRequestedWith, Content-Type, LastModified");
		initParameters.put("cors.exposedHeaders", "SetCookie");
		initParameters.put("cors.supportsCredentials", "true");
		
		registration.setInitParameters(initParameters);
		// 是否自动注册 false 取消Filter的自动注册
		//registration.setEnabled(false);
		
		// 过滤器顺序 
		registration.setOrder(1);
		return registration;
	}

}
