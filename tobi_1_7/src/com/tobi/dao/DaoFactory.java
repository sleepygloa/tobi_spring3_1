package com.tobi.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
	
	//UserDao 클래스에 구동시 Bean 등록 단계에서 미리 DB Connection 정보를 입력하여
	//UserDao 전역변수로 DB 정보를 가지고있음.
	@Bean
	public UserDao userDao() {
		UserDao userDao = new UserDao();
		userDao.setConnectionMaker(connectionMaker());
		return userDao;
	}
	
	@Bean
	public ConnectionMaker connectionMaker() {
		return new DConnectionMaker();
	}
	
}
