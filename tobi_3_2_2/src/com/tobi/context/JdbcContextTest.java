package com.tobi.context;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.tobi.config.AppConfig;
import com.tobi.domain.User;

public class JdbcContextTest {

	/**
	 * JUNIT 라이브러리 추가
	 * */
	@Test
	public void test() throws Exception {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		JdbcContext jdbcContext = applicationContext.getBean("jdbcContext", JdbcContext.class);

		jdbcContext.add(getUser());
	}

	private User getUser() {
		User user = new User();
		user.setId("1");
		user.setName("noname");
		user.setPassword("1234");
		return user;
	}

}
