package com.tobi.dao;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.tobi.notuse.DConnectionMaker;

@Configuration
public class DaoFactory {
	
	//UserDao 클래스에 구동시 Bean 등록 단계에서 미리 DB Connection 정보를 입력하여
	//UserDao 전역변수로 DB 정보를 가지고있음.
	@Bean
	public UserDao userDao() {
		UserDao userDao = new UserDao();
		
		//Before
		//기존 인터페이스를 오버라이드한, Factory, 개별 ConnectionMaker 를 이용한 DI 주입
//		userDao.setConnectionMaker(connectionMaker());
		
		//new
		//DataSource 를 이용한 DI
		userDao.setDataSource(dataSource());
		return userDao;
	}
	
	@Bean
	public ConnectionMaker connectionMaker() {
		return new DConnectionMaker();
	}
	
	//DataSource 를 이용한 Connection
	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		
		dataSource.setDriverClass(org.mariadb.jdbc.Driver.class);
		dataSource.setUrl("jdbc:mariadb://61.252.235.153:18081/seonhoblog?useUnicode=true&amp;characterEncoding=utf8");
		dataSource.setUsername("test");
		dataSource.setPassword("1234");
		
		return dataSource;
		
	}
	
}
