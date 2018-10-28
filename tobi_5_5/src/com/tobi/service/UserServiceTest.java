package com.tobi.service;

import java.sql.SQLException;
import java.util.Arrays;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import com.tobi.config.AppConfig;
import com.tobi.dao.UserDao;
import com.tobi.domain.Level;
import com.tobi.domain.User;
import com.tobi.service.TestUserService.TestUserServiceException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import static com.tobi.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static com.tobi.service.UserService.MIN_RECOMMEND_FOR_GOLD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserServiceTest {
	
	private List<User> users;

	@Autowired
	private UserService userService;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	PlatformTransactionManager transactionManager;
	
	@Before
	public void setUp() throws Exception {
		users = Arrays.asList(
				new User("5", "arr5", "1234", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0, "1@test.com"),
				new User("6", "arr6", "1234", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0, "1@test.com"),
				new User("7", "arr7", "1234", Level.SILVER, MIN_RECOMMEND_FOR_GOLD-1, 29, "1@test.com"),
				new User("8", "arr8", "1234", Level.SILVER, MIN_RECOMMEND_FOR_GOLD, 30, "1@test.com"),
				new User("9", "arr9", "1234", Level.GOLD, Integer.MAX_VALUE, 100, "1@test.com")
		);
	}
	
	@Test
	public void upgradeAllOrNothing() throws Exception {
		UserService testUserService = new TestUserService(users.get(3).getId());
		testUserService.setUserDao(this.userDao);
		testUserService.setTransactionManager(transactionManager);
		userDao.deleteAll();

		for (User user : users) {
			userDao.add(user);
		}
		
		try {
			userService.upgradeLevels();
		} catch (TestUserServiceException e) {
		}
		
		checkLevelUpgraded(users.get(0), false);
		checkLevelUpgraded(users.get(1), true);
		checkLevelUpgraded(users.get(2), false);
		checkLevelUpgraded(users.get(3), true);
		checkLevelUpgraded(users.get(4), false);
	}

	private void checkLevelUpgraded(User user, boolean upgraded) {
		User userUpdate = userDao.get(user.getId());
		
		if (upgraded) {
			assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
		} else {
			assertThat(userUpdate.getLevel(), is(user.getLevel()));
		}
	}

	static class TestUserService extends UserService {
		private String id;

		private TestUserService(String id) {
			this.id = id;
		}

		protected void upgradeLevel(User user) {
			if (user.getId().equals(this.id)) {
				throw new TestUserServiceException();
			}
			super.upgradeLevel(user);
		}

	}

	static class TestUserServiceException extends RuntimeException {
	}

	
}
