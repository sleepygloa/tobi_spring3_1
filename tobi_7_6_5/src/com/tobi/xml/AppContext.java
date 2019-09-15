package com.tobi.xml;

import java.sql.Driver;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;

import com.framework.sql.interfaces.SqlMapConfig;
import com.tobi.annotation.EnableSqlService;
import com.tobi.annotation.EnableTransactionManagement;
import com.tobi.user.interfaces.UserDao;
import com.tobi.user.interfaces.UserService;
import com.tobi.user.service.DamperMailSender;
import com.tobi.user.test.TestUserService;

@Configuration
//@EnableTransactionManagement
@ComponentScan(basePackages="com.tobi.user")
//@Import({SqlServiceContext.class, AppContext.TestAppContext.class, AppContext.ProductionAppContext.class})
@EnableSqlService
@Import(SqlServiceContext.class)
@PropertySource("/com/framework/properties/database.properties")
public class AppContext implements SqlMapConfig {
	
	@Value("${db.driverClass}") Class<? extends Driver> driverClass;
	@Value("${db.url}") String url;
	@Value("${db.username}") String username;
	@Value("${db.password}") String password;
	
	@Override
	public Resource getSqlMapResource() {
		return new ClassPathResource("/com/tobi/user/xml/sqlmap.xml", UserDao.class);
	}
	
//	@Bean
//	public SqlMapConfig sqlMapConfig() {
//		return new UserSqlMapConfig();
//	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	

	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource ds = new SimpleDriverDataSource();
		
		ds.setDriverClass(this.driverClass);
		ds.setUrl(this.url);
		ds.setUsername(this.username);
		ds.setPassword(this.password);
		return ds;
	}
	
//	@Autowired
//	Environment env;
//	
//	@Bean
//	public DataSource dataSource() {
//		SimpleDriverDataSource ds = new SimpleDriverDataSource();
//		
//		try {
//			ds.setDriverClass((Class<? extends java.sql.Driver>)Class.forName(env.getProperty("db.driverClass")));
//		}catch(ClassNotFoundException e) {
//			throw new RuntimeException(e);
//		}
//		ds.setUrl(env.getProperty("db.url"));
//		ds.setUsername(env.getProperty("db.username"));
//		ds.setPassword(env.getProperty("db.password"));
//		return ds;
//	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		tm.setDataSource(dataSource());
		return tm;
	}
	
	@Configuration
	@Profile("production")
	public static class ProductionAppContext {
		@Bean
		public MailSender mailSender() {
			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
			mailSender.setHost("");
			return mailSender;
		}
	}

	
	@Configuration
	@Profile("test")
	public static class TestAppContext {
		@Bean
		public UserService testUserService() {
			return new TestUserService();
		}
		@Bean
		public DamperMailSender mailSender() {
			return new DamperMailSender();
		}
	}



	
	
	
	
}
