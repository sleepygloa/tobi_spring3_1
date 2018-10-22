package com.tobi.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tobi.config.AppConfig;
import com.tobi.dao.UserDao;
import com.tobi.domain.Level;
import com.tobi.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class UserServiceTest {
	private List<User> users;

	@Autowired
	private UserService userService;

	@Autowired
	private UserDao userDao;

	@Before
	public void setUp() throws Exception {
		users = Arrays.asList(
				new User("bumjin", "諛뺣쾾吏�", "p1", Level.BASIC, 49, 0),
				new User("joytouch", "媛뺣챸�꽦", "p1", Level.BASIC, 50, 0),
				new User("erwins", "�떊�듅�븳", "p1", Level.SILVER, 60, 29),
				new User("madnite1", "�씠�긽�샇", "p1", Level.SILVER, 60, 30),
				new User("green", "�삤誘쇨퇋", "p1", Level.GOLD, 100, 100)
		);
	}

	@Test
	public void bean() throws Exception {
		assertThat(this.userService, is(notNullValue()));
	}

	@Test
	public void testUpgradeLevels() throws Exception {
		userDao.deleteAll();

		for (User user : users) {
			userDao.add(user);
		}

		userService.upgradeLevels();

		checkLevel(users.get(0), Level.BASIC);
		checkLevel(users.get(1), Level.SILVER);
		checkLevel(users.get(2), Level.SILVER);
		checkLevel(users.get(3), Level.GOLD);
		checkLevel(users.get(4), Level.GOLD);
	}

	@Test
	public void testAdd() throws Exception {
		userDao.deleteAll();

		User userWithLevel = users.get(4);
		User userWithoutLevel = users.get(0);
		userWithoutLevel.setLevel(null);

		userService.add(userWithLevel);
		userService.add(userWithoutLevel);

		User userWithLevelRead = userDao.get(userWithLevel.getId());
		User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

		assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
		assertThat(userWithoutLevelRead.getLevel(), is(Level.BASIC));
	}

	private void checkLevel(User user, Level expectedLevel) {
		User userUpdate = userDao.get(user.getId());
		assertThat(userUpdate.getLevel(), is(expectedLevel));
	}
}