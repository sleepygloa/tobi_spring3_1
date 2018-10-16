package com.tobi.config;

import java.sql.Driver;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.tobi.context.JdbcContext;
import com.tobi.dao.UserDao;

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

	public JdbcContext jdbcContext() throws ClassNotFoundException {
		JdbcContext jdbcContext = new JdbcContext();
		jdbcContext.setDataSource(dataSource());
		return jdbcContext;
	}

	@Bean
	public UserDao userDao() throws ClassNotFoundException {
		UserDao userDao = new UserDao();
		userDao.setJdbcContext(jdbcContext());
		return userDao;
	}
}
