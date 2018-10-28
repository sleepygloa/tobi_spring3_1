package com.tobi.config;

import java.sql.Driver;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.tobi.dao.UserDao;
import com.tobi.dao.UserDaoJdbc;
import com.tobi.service.DummyMailSender;
import com.tobi.service.UserService;

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

	@Bean
	public UserDao userDao() throws ClassNotFoundException {
		UserDaoJdbc userDao = new UserDaoJdbc();

		userDao.setDataSource(dataSource());
		return userDao;
	}

	@Bean
	public UserService userService(UserDao userDao, DataSource dataSource, PlatformTransactionManager transactionManager, MailSender mailSender) {
		UserService userService = new UserService();

		userService.setUserDao(userDao);
		userService.setDataSource(dataSource);
		userService.setTransactionManager(transactionManager);
		userService.setMailSender(mailSender);
		return userService;
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public DummyMailSender mailSender() {
		DummyMailSender mailSender = new DummyMailSender();
		return mailSender;
	}
}
