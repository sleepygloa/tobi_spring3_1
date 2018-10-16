package com.tobi.context;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.tobi.config.AppConfig;
import com.tobi.dao.UserDao;

public class JdbcContextTest {

	/**
	 * JUNIT ���̺귯�� �߰�
	 * */

	@Test
	public void testDeleteAll() throws Exception {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		UserDao userDao = applicationContext.getBean("userDao", UserDao.class);

		userDao.deleteAll();
	}

}
