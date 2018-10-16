package com.tobi.context;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.tobi.config.AppConfig;
import com.tobi.dao.UserDao;
import com.tobi.domain.User;

public class JdbcContextTest {

	/**
	 * JUNIT 라이브러리 추가
	 * */
	@Test
	public void testAdd() throws Exception {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		UserDao userDao = applicationContext.getBean("userDao", UserDao.class);

		userDao.add(getUser());
	}

	@Test
	public void testDeleteAll() throws Exception {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		UserDao userDao = applicationContext.getBean("userDao", UserDao.class);

		userDao.deleteAll();
	}


	private User getUser() {
		User user = new User();
		user.setId("1");
		user.setName("noname");
		user.setPassword("1234");
		return user;
	}

}
