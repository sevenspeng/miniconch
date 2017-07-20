package com.xiaohailuo.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xiaohailuo.domain.DBHeartbeat;

@Component
public class ScheduledTasks {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	private DBHeartbeat dbHeartbeat;

	
//	@Scheduled(fixedRate = 5000) ：上一次开始执行时间点之后5秒再执行
//	@Scheduled(fixedDelay = 5000) ：上一次执行完毕时间点之后5秒再执行
//	@Scheduled(initialDelay=1000, fixedRate=5000) ：第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
//	@Scheduled(cron="*/5 * * * * *") ：通过cron表达式定义规则
	
//	@Scheduled(initialDelay=1000, fixedRate = 5000)
//	public void reportCurrentTime() {
//		System.out.println("当前时间：" + dateFormat.format(new Date()));
//	}
	
	@Scheduled(initialDelay = 1000, fixedRate = 10000)
	public void heartbeat() {
		System.out.println("MySQL Ping执行时间：" + dbHeartbeat.pingMySQL());
	}

	@Scheduled(initialDelay = 1000, fixedRate = 72000 * 1000)
	public void accessTokenForWebChat() {
		System.out.println("更新access_token：" + dbHeartbeat.pingMySQL());
	}

	@Scheduled(initialDelay = 1000, fixedRate = 72000 * 1000)
	public void signatureForWebChat() {
		System.out.println("更新scheduled：" + dbHeartbeat.pingMySQL());
	}
	
	
}