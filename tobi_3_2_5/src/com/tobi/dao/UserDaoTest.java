package com.tobi.dao;

import com.tobi.config.AppConfig;
import com.tobi.domain.User;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class UserDaoTest {

	private UserDao userDao;

	@Before
	public void setup() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		userDao = applicationContext.getBean("userDao", UserDao.class);
	}

	@Test
	public void testDeleteAllWithPsCreator() throws Exception {
		userDao.deleteAllWithPsCreateor();
	}

	@Test
	public void testDeleteAll() throws Exception {
		userDao.deleteAll();
	}

	@Test
	public void testGetCount() throws Exception {
		userDao.deleteAll();
		userDao.add(getUser());
		assertThat(userDao.getCount(), is(1));
	}

	@Test
	public void testGet() throws Exception {
		userDao.deleteAll();
		userDao.add(getUser());
		assertThat(userDao.get("crazybnn"), is(not(nullValue())));
	}
	public User getUser() {
		User user = new User();
		user.setId("1");
		user.setName("noname");
		user.setPassword("1234");
		return user;
	}

}