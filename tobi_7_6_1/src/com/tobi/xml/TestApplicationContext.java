package com.tobi.xml;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.framework.embeddedDb.service.EmbeddedDbSqlRegistry;
import com.framework.sql.interfaces.SqlRegistry;
import com.framework.sql.interfaces.SqlService;
import com.framework.sql.service.OxmSqlService;
import com.tobi.user.dao.UserDaoJdbc;
import com.tobi.user.interfaces.UserDao;
import com.tobi.user.interfaces.UserService;
import com.tobi.user.service.UserServiceImpl;

@Configuration
@EnableTransactionManagement
public class TestApplicationContext {
	
	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource ds = new SimpleDriverDataSource();
		ds.setDriverClass(org.mariadb.jdbc.Driver.class);
		ds.setUrl("jdbc:mariadb://61.252.235.153:18081/seonhoblog?useUnicode=true&amp;characterEncoding=utf8");
		ds.setUsername("test");
		ds.setPassword("1234");
		return ds;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		tm.setDataSource(dataSource());
		return tm;
	}
	
	@Autowired SqlService sqlService;
	
	@Bean
	public UserDao userDao() {
		UserDaoJdbc  dao = new UserDaoJdbc();
		dao.setDataSource(dataSource());
		dao.setSqlService(this.sqlService);
		return dao;
	}
	
	@Bean
	public UserService userService() {
		UserServiceImpl service = new UserServiceImpl();
		service.setUserDao(userDao());
//		service.setMailSender(mailSender());
		return service;
	}
	
//	@Bean
//	public UserService testUserService() {
//		TestUserService testService = new TestUserService();
//		testService.setUserDao(userDao());
//		testService.setMailSender(mailSender());
//		return testService;
//	}
	
	@Bean
	public SqlService sqlService() {
		OxmSqlService sqlService = new OxmSqlService();
		sqlService.setUnmarshaller(unmarshaller());
		sqlService.setSqlRegistry(sqlRegistry());
		return sqlService;
	}
	
	@Bean
	public SqlRegistry sqlRegistry() {
		EmbeddedDbSqlRegistry sqlRegistry = new EmbeddedDbSqlRegistry();
		sqlRegistry.setDataSource(embeddedDatabase());
		return sqlRegistry;
	}
	
	@Bean
	public Unmarshaller unmarshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("com.framework.sql.service.jaxb");
		return (Unmarshaller)marshaller;
	}
	
	@Bean
	public DataSource embeddedDatabase() {
		return new EmbeddedDatabaseBuilder()
				.setName("embeddedDatabase")
				.setType(HSQL)
				.addScript("classpath:/com/framework/embeddedDb/xml/sqlRegistrySchema.sql")
				.build();
	}
	
	
}
