package com.tobi.context;

import com.tobi.config.AppConfig;
import com.tobi.domain.User;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JdbcContextTest {

	@Test
	public void testDeleteAll() throws Exception {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		JdbcContext jdbcContext = applicationContext.getBean("jdbcContext", JdbcContext.class);

		System.out.println("==========");
		jdbcContext.deleteAll();

		System.out.println("=delete succuess=");
		jdbcContext.add(getUser());
		System.out.println("=add succuess=");
		System.out.println("==========");
	}


	private User getUser() {
		User user = new User();
		user.setId("1");
		user.setName("noname");
		user.setPassword("1234");
		return user;
	}

}