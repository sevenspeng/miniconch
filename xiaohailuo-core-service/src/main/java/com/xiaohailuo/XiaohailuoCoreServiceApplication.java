package com.xiaohailuo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
/*import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;*/
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class XiaohailuoCoreServiceApplication /*extends SpringBootServletInitializer*/ {
	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(XiaohailuoCoreServiceApplication.class);
	}*/
	
	/*@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultiPartConfigFactory factory = new MultiPartConfigFactory();
		factory.setMaxFileSize("128KB");
		factory.setMaxRequestSize("128KB");
		return factory.createMultipartConfig();
	}*/
	
	public static void main(String[] args) {
		SpringApplication.run(XiaohailuoCoreServiceApplication.class, args);
	}
}
