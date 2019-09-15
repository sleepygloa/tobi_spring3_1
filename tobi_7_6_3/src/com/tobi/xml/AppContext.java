package com.tobi.xml;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;

import com.tobi.user.interfaces.UserService;
import com.tobi.user.service.DamperMailSender;
import com.tobi.user.test.TestUserService;

@Configuration
//@EnableTransactionManagement
@ComponentScan(basePackages="com.tobi.user")
//@Import({SqlServiceContext.class, AppContext.TestAppContext.class, AppContext.ProductionAppContext.class})
@Import(SqlServiceContext.class)
public class AppContext {
	
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
//	@Autowired UserDao userDao;
//	
//	@Bean
//	public UserService userService() {
//		UserServiceImpl service = new UserServiceImpl();
//		service.setUserDao(this.userDao);
////		service.setMailSender(mailSender());
//		return service;
//	}
//	
////	@Bean
////	public UserService testUserService() {
////		TestUserService testService = new TestUserService();
////		testService.setUserDao(this.userDao);
//////		testService.setMailSender(mailSender());
////		return testService;
////	}
//	
//	@Bean
//	public MailSender mailSender() {
//		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//		mailSender.setHost("");
//		return mailSender;
//	}
	
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
