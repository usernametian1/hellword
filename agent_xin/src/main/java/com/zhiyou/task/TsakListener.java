package com.zhiyou.task;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zhiyou.analysis.service.impl.AnlysisDailyTaskService;
import com.zhiyou.core.task.TimerTask;

public class TsakListener implements ServletContextListener {

    private static final Logger log = Logger.getLogger(TsakListener.class);
    
	@Override
	public void contextDestroyed(ServletContextEvent event) {
	    log.info("定时器已销毁");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// 添加日志，可在tomcat日志中查看到
	    log.info("定时器已启动");
		WebApplicationContext webConext = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
		final AnlysisDailyTaskService anlysisDailyTaskService = (AnlysisDailyTaskService) webConext.getBean(AnlysisDailyTaskService.class);
		TimerTask.addEveryDayTask(new Runnable() {
			@Override
			public void run() {
				try {
					anlysisDailyTaskService.agentAnlysisDailyBindNum();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "00:10:00");

		TimerTask.addEveryDayTask(new Runnable() {
			@Override
			public void run() {
				try {
					anlysisDailyTaskService.agentAnlysisDailyIncome();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "00:12:00");
		TimerTask.addEveryDayTask(new Runnable() {
			@Override
			public void run() {
				try {
					anlysisDailyTaskService.agentAnlysisDailyTable();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "00:14:00");
		
		TimerTask.addEveryDayTask(new Runnable() {
			@Override
			public void run() {
				try {
					anlysisDailyTaskService.appAnlysisDaily();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "00:16:00");
	}
}
