package com.tobi.service;

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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
	
	@Before
	public void setUp() throws Exception {
		users = Arrays.asList(
				new User("5", "arr5", "1234", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0),
				new User("6", "arr6", "1234", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0),
				new User("7", "arr7", "1234", Level.SILVER, MIN_RECOMMEND_FOR_GOLD-1, 29),
				new User("8", "arr8", "1234", Level.SILVER, MIN_RECOMMEND_FOR_GOLD, 30),
				new User("9", "arr9", "1234", Level.GOLD, Integer.MAX_VALUE, 100)
		);
	}
	
	/**
	 * 1. User Delete
	 * 2. User add
	 * 3. User(0) Auth update
	 * */
	@Test
	public void updateLevel() {
		userDao.deleteAll();
		for(User user : users) {
			userDao.add(user);
		}
		
		userService.upgradeLevels();
		
		checkLevel(users.get(0), Level.BASIC);
		
		checkLevelUpgraded(users.get(0), false);
		checkLevelUpgraded(users.get(1), true);
		checkLevelUpgraded(users.get(2), false);
		checkLevelUpgraded(users.get(3), true);
		checkLevelUpgraded(users.get(4), false);
		
	}
	
	private void checkLevelUpgraded(User user, boolean upgraded) {
		
		User userUpdate = userDao.get(user.getId());
		
		if(upgraded) {
			assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
		}
		else {
			assertThat(userUpdate.getLevel(), is(user.getLevel()));
		}
			
	}
	
	/**
	 * check user Auth, after user add.
	 * */
	private void checkLevel(User user, Level expectedLevel) {
		User userUpdate = userDao.get(user.getId());
		assertThat(userUpdate.getLevel(), is(expectedLevel));
	}
	
	/**
	 * 1. update delete
	 * 2. user VO(array) date setting -> 5th(GOLD), 0th(BASIC)
	 * 3. add user VO
	 * */
	@Test
	public void add() throws Exception {
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
	

	
}
