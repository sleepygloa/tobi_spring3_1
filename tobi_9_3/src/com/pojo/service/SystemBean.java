package com.pojo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.pojo.interfaces.ApplicationContext;

public class SystemBean {

	@Autowired ApplicationContext context;
	
	public void specialJobWithContext() {
		this.context.getBean(Hello.class);
	}
	
	@Autowired ResourceLoader resourceLoader;
	
	public void loadDataFile() {
		Resource resource = this.resourceLoader.getResource("WEB-INF/info.dat");
	}
}
