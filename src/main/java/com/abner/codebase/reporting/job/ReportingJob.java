package com.abner.codebase.reporting.job;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.abner.codebase.reporting.config.ReportConfig;
import com.abner.codebase.reporting.domain.Human;
import com.abner.codebase.reporting.service.BootstrapService;
import com.abner.codebase.reporting.service.ReportingService;
import com.abner.codebase.reporting.service.impl.DefaultBootstrapService;

public class ReportingJob {
	
	private ApplicationContext context;
	private BootstrapService bootstrapService;
	private ReportingService reportingService;
	
	public void start(String[] args){
		init(args);
		bootstrapService.boot();
		String reportName = ReportConfig.REPORT_NAME.getValue();
		reportingService.generateReport( Human.class.getSimpleName());
	}
	
	private void init(String[] args){
		context = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
		bootstrapService = context.getBean(DefaultBootstrapService.class);
		reportingService = context.getBean(ReportingService.class);
	}
	
	public ApplicationContext getContext(){
		return context;
	}
}
