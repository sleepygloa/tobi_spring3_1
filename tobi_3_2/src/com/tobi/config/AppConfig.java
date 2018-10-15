package com.tobi.config;

import java.sql.Driver;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.tobi.dao.UserDao;
import com.tobi.dao.UserDaoAdd;
import com.tobi.dao.UserDaoDeleteAll;

@Configuration
public class AppConfig {

	String dbClass = "org.mariadb.jdbc.Driver";
	String dbUrl = "jdbc:mariadb://61.252.235.153:18081/seonhoblog?useUnicode=true&amp;characterEncoding=utf8";

	@Bean
	public DataSource dataSource() throws ClassNotFoundException {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass((Class<Driver>) Class.forName(dbClass));
		dataSource.setUrl(dbUrl);
		dataSource.setUsername("root");
		dataSource.setPassword("1234");
		return dataSource;
	}

//	@Bean
//	public UserDao userDao() throws ClassNotFoundException {
//		UserDao userDao = new UserDao();
//		userDao.setDataSource(dataSource());
//		return userDao;
//	}
	
	@Bean
	public UserDaoAdd userDaoAdd() throws ClassNotFoundException {
		UserDaoAdd userDao = new UserDaoAdd();
		userDao.setDataSource(dataSource());
		return userDao;
	}
	
	@Bean
	public UserDaoDeleteAll userDaoDeleteAll() throws ClassNotFoundException {
		UserDaoDeleteAll userDao = new UserDaoDeleteAll();
		userDao.setDataSource(dataSource());
		return userDao;
	}
}
